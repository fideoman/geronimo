/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Geronimo" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache Geronimo", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * This source code implements specifications defined by the Java
 * Community Process. In order to remain compliant with the specification
 * DO NOT add / change / or delete method signatures!
 *
 * ====================================================================
 */

package javax.security.jacc;

import java.security.SecurityPermission;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @version $Revision: 1.1 $ $Date: 2003/08/30 01:55:12 $
 */
public final class PolicyContext {

    private static ThreadLocal contextId = new ThreadLocal();
    private static ThreadLocal handlerData = new ThreadLocal();
    private static Hashtable handlers = new Hashtable();

    public static void setContextID(String contextID) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkPermission(new SecurityPermission("setPolicy"));

        contextId.set(contextID);
    }

    public static String getContextID() {
        return (String)contextId.get();
    }

    public static void setHandlerData(Object data) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkPermission(new SecurityPermission("setPolicy"));

        handlerData.set(data);
    }

    public static void registerHandler(String key, PolicyContextHandler handler, boolean replace) throws PolicyContextException {
        if (key == null) throw new IllegalArgumentException("Key must not be null");
        if (handler == null) throw new IllegalArgumentException("Handler must not be null");
        if (!replace && handlers.containsKey(key)) throw new IllegalArgumentException ("A handler has already been registered under '" + key + "' and replace is false.");

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkPermission(new SecurityPermission("setPolicy"));

        handlers.put(key, handler);
    }

    public static Set getHandlerKeys() {
        return handlers.keySet();
    }

    public static Object getContext(String key) throws PolicyContextException {
        if (key == null) throw new IllegalArgumentException("Key must not be null");

        PolicyContextHandler handler = (PolicyContextHandler)handlers.get(key);

        if (handler == null) throw new IllegalArgumentException("No handler can be found for the key '" + key + "'");
        if (!handler.supports(key)) throw new IllegalArgumentException ("Registered handler no longer supports the key '" + key + "'");

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkPermission(new SecurityPermission("setPolicy"));

        return handler.getContext(key, handlerData.get());
    }
}
