/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.geronimo.axis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;

import javax.management.ObjectName;

import org.apache.geronimo.axis.testUtils.TestingUtils;
import org.apache.geronimo.gbean.GBeanData;
import org.apache.geronimo.kernel.Kernel;

/**
 * <p> This test case start the AxisGbean and test retiving the index.html of the
 * Axis Service</p>
 * @version $Rev$ $Date$
 */
public class AxisGBeanTest extends AbstractTestCase {
    private ObjectName name;
    private Kernel kernel;

    /**
     * @param testName
     */
    public AxisGBeanTest(String testName) {
        super(testName);
    }

    public void xtestStartAxisService() throws Exception {
        ClassLoader cl = getClass().getClassLoader();
        ClassLoader myCl = new URLClassLoader(new URL[0], cl);
        GBeanData gbean = new GBeanData(name,AxisGbean.getGBeanInfo());
        kernel.loadGBean(gbean,myCl);
        kernel.startGBean(name);
        System.out.println(kernel.getAttribute(name, "state"));
        HttpURLConnection connection = (HttpURLConnection) AxisGeronimoUtils.getURL("/axis/index.html").openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        connection.getResponseCode();
        String line = reader.readLine();
        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
        connection.disconnect();
        kernel.stopGBean(name);
        kernel.unloadGBean(name);
    }
    
    
//    public void testEJBWebServiceGBean() throws Exception{
//        ObjectName ejbname = new ObjectName("test:name=ejbwsGBean");
//        GBeanMBean ejbBean = new GBeanMBean(EJBWSGBean.getGBeanInfo());
//        kernel.loadGBean(ejbname,ejbBean);
//        kernel.startGBean(ejbname);
//
//        kernel.stopGBean(ejbname);
//        kernel.unloadGBean(ejbname);
//    
//    }

    protected void setUp() throws Exception {
        
        name = new ObjectName("test:name=AxisGBean");
        kernel = new Kernel("test.kernel");
        kernel.boot();
        TestingUtils.startJ2EEContinerAndAxisServlet(kernel);
    }

    protected void tearDown() throws Exception {
        TestingUtils.stopJ2EEContinerAndAxisServlet(kernel);
        kernel.shutdown();
    }
}
