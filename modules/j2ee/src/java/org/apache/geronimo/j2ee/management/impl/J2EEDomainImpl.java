/**
 *
 * Copyright 2003-2004 The Apache Software Foundation
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
import org.apache.geronimo.gbean.jmx.GBeanMBeanContext;

/**
 * @version $Revision: 1.1 $ $Date: 2004/05/19 20:53:59 $
 */
public class J2EEDomainImpl implements GBean {
    private GBeanContext context;
    private String baseName;

    public void setGBeanContext(GBeanContext context) {
        this.context = context;
        if (context != null) {
            ObjectName objectName = context.getObjectName();
            verifyObjectName(objectName);
            baseName = objectName.getDomain() + ":";
        } else {
            baseName = null;
        }
    }

    /**
     * ObjectName must match this pattern:
     * <p/>
     * domain:j2eeType=J2EEServer,name=MyName
     */
    private void verifyObjectName(ObjectName objectName) {
        if (objectName.isPattern()) {
            throw new InvalidObjectNameException("ObjectName can not be a pattern", objectName);
        }
        Hashtable keyPropertyList = objectName.getKeyPropertyList();
        if (!"J2EEDomain".equals(keyPropertyList.get("j2eeType"))) {
            throw new InvalidObjectNameException("J2EEDomain object name j2eeType property must be 'J2EEDomain'", objectName);
        }
        String name = (String) keyPropertyList.get("name");
        if (!objectName.getDomain().equals(name)) {
            throw new InvalidObjectNameException("Domain part of J2EEDomain object name must match name propert", objectName);
        }
    }


    public String[] getservers() throws MalformedObjectNameException {
        return Util.getObjectNames(((GBeanMBeanContext) context).getServer(),
                baseName,
                new String[]{"J2EEServer"});
    }

    public void doStart() {
    }

    public void doStop() {
    }

    public void doFail() {
    }

    public static final GBeanInfo GBEAN_INFO;

    static {
        GBeanInfoFactory infoFactory = new GBeanInfoFactory(J2EEDomainImpl.class);
        infoFactory.addAttribute("servers", false);
        GBEAN_INFO = infoFactory.getBeanInfo();
    }

    public static GBeanInfo getGBeanInfo() {
        return GBEAN_INFO;
    }
}
