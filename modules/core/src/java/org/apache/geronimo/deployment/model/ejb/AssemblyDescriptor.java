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
 * ====================================================================
 */
package org.apache.geronimo.deployment.model.ejb;

import org.apache.geronimo.deployment.model.j2ee.MessageDestination;
import org.apache.geronimo.deployment.model.j2ee.SecurityRole;

/**
 *  JavaBean for the ejb-jar.xml tag assembly-descriptor
 *
 * @version $Revision: 1.2 $ $Date: 2003/09/07 23:20:06 $
 */
public class AssemblyDescriptor {
    private SecurityRole[] securityRole = new SecurityRole[0];
    private MethodPermission[] methodPermission = new MethodPermission[0];
    private ContainerTransaction[] containerTransaction = new ContainerTransaction[0];
    private MessageDestination[] messageDestination = new MessageDestination[0];
    private ExcludeList excludeList;

    public ContainerTransaction[] getContainerTransaction() {
        return containerTransaction;
    }

    public ContainerTransaction getContainerTransaction(int i) {
        return containerTransaction[i];
    }

    public void setContainerTransaction(ContainerTransaction[] containerTransaction) {
        this.containerTransaction = containerTransaction;
    }

    public void setContainerTransaction(int i, ContainerTransaction containerTransaction) {
        this.containerTransaction[i] = containerTransaction;
    }

    public ExcludeList getExcludeList() {
        return excludeList;
    }

    public void setExcludeList(ExcludeList excludeList) {
        this.excludeList = excludeList;
    }

    public MessageDestination[] getMessageDestination() {
        return messageDestination;
    }

    public MessageDestination getMessageDestination(int i) {
        return messageDestination[i];
    }

    public void setMessageDestination(MessageDestination[] messageDestination) {
        this.messageDestination = messageDestination;
    }

    public void setMessageDestination(int i, MessageDestination messageDestination) {
        this.messageDestination[i] = messageDestination;
    }

    public MethodPermission[] getMethodPermission() {
        return methodPermission;
    }

    public MethodPermission getMethodPermission(int i) {
        return methodPermission[i];
    }

    public void setMethodPermission(MethodPermission[] methodPermission) {
        this.methodPermission = methodPermission;
    }

    public void setMethodPermission(int i, MethodPermission methodPermission) {
        this.methodPermission[i] = methodPermission;
    }

    public SecurityRole[] getSecurityRole() {
        return securityRole;
    }

    public SecurityRole getSecurityRole(int i) {
        return securityRole[i];
    }

    public void setSecurityRole(SecurityRole[] securityRole) {
        this.securityRole = securityRole;
    }

    public void setSecurityRole(int i, SecurityRole securityRole) {
        this.securityRole[i] = securityRole;
    }
}
