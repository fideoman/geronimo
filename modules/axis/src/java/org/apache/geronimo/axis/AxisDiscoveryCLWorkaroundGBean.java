/**
 *
 * Copyright 2005 The Apache Software Foundation
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
package org.apache.geronimo.axis;

import org.apache.axis.AxisProperties;
import org.apache.geronimo.gbean.GBeanInfo;
import org.apache.geronimo.gbean.GBeanInfoBuilder;

/**
 * @version $Rev:  $ $Date:  $
 */
public class AxisDiscoveryCLWorkaroundGBean {

    public AxisDiscoveryCLWorkaroundGBean(ClassLoader classLoader) {
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(classLoader);
        try {
            //this sets the classloaders used in discovery. One is the current TCCL.
            AxisProperties.getNameDiscoverer();
        } finally {
            currentThread.setContextClassLoader(oldClassLoader);
        }
    }

    public static final GBeanInfo GBEAN_INFO;

    static {
        GBeanInfoBuilder infoBuilder = new GBeanInfoBuilder(AxisDiscoveryCLWorkaroundGBean.class);
        infoBuilder.addAttribute("classLoader", ClassLoader.class, false);
        infoBuilder.setConstructor(new String[] {"classLoader"});
        GBEAN_INFO = infoBuilder.getBeanInfo();
    }

    public static GBeanInfo getGBeanInfo() {
        return GBEAN_INFO;
    }
}
