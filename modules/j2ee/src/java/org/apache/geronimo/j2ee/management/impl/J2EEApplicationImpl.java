/**
 *
 * Copyright 2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.geronimo.j2ee.management.impl;

import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.geronimo.gbean.GBean;
import org.apache.geronimo.gbean.GBeanContext;
import org.apache.geronimo.gbean.GBeanInfo;
import org.apache.geronimo.gbean.GBeanInfoFactory;
import org.apache.geronimo.gbean.WaitingException;
import org.apache.geronimo.gbean.jmx.GBeanMBeanContext;
import org.apache.geronimo.j2ee.management.J2EEServer;

/**
 * @version $Revision: 1.1 $ $Date: 2004/05/19 20:53:59 $
 */
public class J2EEApplicationImpl implements GBean {
    private String deploymentDescriptor;
    private String baseName;
    private GBeanContext context;
    private J2EEServer server;

    public J2EEApplicationImpl(J2EEServer server, String deploymentDescriptor) {
        this.server = server;
        this.deploymentDescriptor = deploymentDescriptor;
    }

    public void setGBeanContext(GBeanContext context) {
        this.context = context;
        if (context != null) {
            ObjectName objectName = context.getObjectName();
            verifyObjectName(objectName);

            // build the base name used to query the server for child modules
            Hashtable keyPropertyList = objectName.getKeyPropertyList();
            String name = (String) keyPropertyList.get("name");
            String j2eeServerName = (String) keyPropertyList.get("J2EEServer");
            baseName = objectName.getDomain() + ":J2EEServer=" + j2eeServerName + ",J2EEApplication=" + name + ",";
        } else {
            baseName = null;
        }
    }

    /**
     * ObjectName must match this pattern:
     * <p/>
     * domain:j2eeType=J2EEApplication,name=MyName,J2EEServer=MyServer
     */
    private void verifyObjectName(ObjectName objectName) {
        if (objectName.isPattern()) {
            throw new InvalidObjectNameException("ObjectName can not be a pattern", objectName);
        }
        Hashtable keyPropertyList = objectName.getKeyPropertyList();
        if (!"J2EEApplication".equals(keyPropertyList.get("j2eeType"))) {
            throw new InvalidObjectNameException("J2EEApplication object name j2eeType property must be 'J2EEApplication'", objectName);
        }
        if (!keyPropertyList.containsKey("name")) {
            throw new InvalidObjectNameException("J2EEApplication object must contain a J2EEServer property", objectName);
        }
        if (!keyPropertyList.containsKey("J2EEServer")) {
            throw new InvalidObjectNameException("J2EEApplication object name must contain a J2EEServer property", objectName);
        }
        if (keyPropertyList.size() != 3) {
            throw new InvalidObjectNameException("J2EEApplication object name can only have j2eeType, name, and J2EEServer properties", objectName);
        }
    }

    public void doStart() throws WaitingException, Exception {
    }

    public void doStop() throws WaitingException, Exception {
    }

    public void doFail() {
    }

    public String[] getmodules() throws MalformedObjectNameException {
        return Util.getObjectNames(((GBeanMBeanContext) context).getServer(),
                baseName,
                new String[]{"AppClientModule", "EJBModule", "WebModule", "ResourceAdapterModule"});
    }

    public String getdeploymentDescriptor() {
        return deploymentDescriptor;
    }

    public String getserver() {
        return server.getobjectName();
    }

    public static final GBeanInfo GBEAN_INFO;

    static {
        GBeanInfoFactory infoFactory = new GBeanInfoFactory(J2EEApplicationImpl.class);
        infoFactory.addReference("j2eeServer", J2EEServer.class);
        infoFactory.addAttribute("deploymentDescriptor", true);
        infoFactory.addAttribute("modules", false);
        infoFactory.setConstructor(new String[]{"j2eeServer", "deploymentDescriptor"},
                new Class[]{J2EEServer.class, String.class});
        GBEAN_INFO = infoFactory.getBeanInfo();
    }

    public static GBeanInfo getGBeanInfo() {
        return GBEAN_INFO;
    }
}
