/*
 * Copyright 2003,2004 The Apache Software Foundation.
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
package org.apache.geronimo.axis.testUtils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.management.ObjectName;
import javax.naming.Reference;

import org.apache.geronimo.axis.AxisGeronimoUtils;
import org.apache.geronimo.axis.WSConfigBuilder;
import org.apache.geronimo.axis.WSPlan;
import org.apache.geronimo.deployment.DeploymentException;
import org.apache.geronimo.gbean.GBeanData;
import org.apache.geronimo.gbean.jmx.GBeanMBean;
import org.apache.geronimo.j2ee.deployment.EARConfigBuilder;
import org.apache.geronimo.j2ee.deployment.ResourceReferenceBuilder;
import org.apache.geronimo.kernel.Kernel;
import org.apache.geronimo.kernel.config.ConfigurationManager;
import org.apache.geronimo.kernel.config.ConfigurationStore;
import org.apache.geronimo.transaction.OnlineUserTransaction;

/**
 * @version $Rev: $ $Date: $
 */
public class TestingUtils {
    
    protected static J2EEManager j2eeManager  = new J2EEManager();
    

    public static void startJ2EEContinerAndAxisServlet(Kernel kernel) throws Exception{
//        //This does the work need to be done by plan
        j2eeManager.startJ2EEContainer(kernel);
        //start the Axis Serverlet which would be started by the service plan
        org.apache.geronimo.jetty.JettyWebAppContext c = null;
        GBeanMBean app = new GBeanMBean("org.apache.geronimo.jetty.JettyWebAppContext");
        URL url =
                Thread.currentThread().getContextClassLoader().getResource("deployables/axis/");
        System.out.print(url);
        app.setAttribute("uri", URI.create(url.toString()));
        app.setAttribute("contextPath", "/axis");
        app.setAttribute("componentContext", null);
        OnlineUserTransaction userTransaction = new OnlineUserTransaction();
        app.setAttribute("userTransaction", userTransaction);
        app.setAttribute("webClassPath", new URI[0]);
        app.setAttribute("contextPriorityClassLoader", Boolean.FALSE);
        app.setReferencePatterns("JettyContainer", Collections.singleton(AxisGeronimoConstants.WEB_CONTAINER_NAME));
        app.setAttribute("configurationBaseUrl", Thread.currentThread().getContextClassLoader().getResource("deployables/"));
        app.setReferencePattern("TransactionContextManager", AxisGeronimoConstants.TRANSACTION_CONTEXT_MANAGER_NAME);
        app.setReferencePattern("TrackedConnectionAssociator", AxisGeronimoConstants.TRACKED_CONNECTION_ASSOCIATOR_NAME);
        AxisGeronimoUtils.startGBean(AxisGeronimoConstants.APPLICATION_NAME, app, kernel);

    }
    
    public static void stopJ2EEContinerAndAxisServlet(Kernel kernel) throws Exception{
        j2eeManager.stopJ2EEContainer(kernel);
    }
    
    public static ResourceReferenceBuilder RESOURCE_REFERANCE_BUILDER = new ResourceReferenceBuilder() {

        public Reference createResourceRef(String containerId, Class iface) {
            return null;
        }

        public Reference createAdminObjectRef(String containerId, Class iface) {
            return null;
        }

        public ObjectName locateResourceName(ObjectName query) {
            return AxisGeronimoConstants.RESOURCE_ADAPTER_NAME;
        }

        public GBeanData locateActivationSpecInfo(ObjectName resourceAdapterModuleName, String messageListenerInterface) {
            return AxisGeronimoConstants.ACTIVATION_SPEC_INFO;
        }

        public GBeanData locateResourceAdapterGBeanData(ObjectName resourceAdapterModuleName) throws DeploymentException {
            return null;
        }

        public GBeanData locateAdminObjectInfo(ObjectName resourceAdapterModuleName, String adminObjectInterfaceName) throws DeploymentException {
            return null;
        }

        public GBeanData locateConnectionFactoryInfo(ObjectName resourceAdapterModuleName, String connectionFactoryInterfaceName) throws DeploymentException {
            return null;
        }
    };
    
    
    public static void buildConfiguration(File jarfile,
            ConfigurationStore store,
            EARConfigBuilder earConfigBuilder,
            Kernel kernel,
            ObjectName wsConfgBuilderName)throws Exception{
        URI ejbURI = null;
        ObjectName wsconf = new ObjectName("geronimo.test:name=" + jarfile.getName());
        ObjectName ejbconf = new ObjectName("geronimo.test:name=" + jarfile.getName() + "EJB");
        URI wsURI = new URI("new");

        WSPlan plan = null;
        
        Enumeration entires = new JarFile(jarfile).entries();
        while (entires.hasMoreElements()) {
            ZipEntry zipe = (ZipEntry) entires.nextElement();
            String name = zipe.getName();
            if (name.endsWith("/ejb-jar.xml")) {
                
                JarFile module = new JarFile(jarfile);
                File unpackedDir = store.createNewConfigurationDir();
                //Install the EJB
                Object ejbplan = earConfigBuilder.getDeploymentPlan(null, module);
                earConfigBuilder.buildConfiguration(ejbplan, module, unpackedDir);
                ejbURI = store.install(unpackedDir);

                //load the EJB Configuration TODO, Do we need this?        
                GBeanMBean ejbGBean = AxisGeronimoUtils.loadConfig(unpackedDir);
                
                plan = WSPlan.createPlan(wsURI,wsconf,ejbGBean.getObjectNameObject(),jarfile);
                System.out.println("entry found " + name + " the web service is based on a ejb.");
                //log.info("the web service is based on a ejb.");
                break;
            }
        }
        
        if(plan == null){
            plan = WSPlan.createPlan(wsURI,wsconf,jarfile);
        }
        File wsinstallDir = store.createNewConfigurationDir();
        
        kernel.invoke(wsConfgBuilderName,"buildConfiguration",
            new Object[]{plan, null, wsinstallDir},
            new String[]{Object.class.getName(),
                JarFile.class.getName(),
                File.class.getName()});
        //wsconfBuilder.buildConfiguration(plan, null, wsinstallDir);
        
        if(ejbURI != null){
            GBeanMBean config = store.getConfiguration(ejbURI);
            ConfigurationManager configurationManager = kernel.getConfigurationManager();
            ObjectName configName = configurationManager.load(config, null);
            kernel.loadGBean(configName,config);
            kernel.startRecursiveGBean(configName);
        }
        URI wsInstalledURI = store.install(wsinstallDir);
        GBeanMBean config = store.getConfiguration(wsInstalledURI);
        ConfigurationManager configurationManager = kernel.getConfigurationManager();
        ObjectName configName = configurationManager.load(config, null);
        kernel.startRecursiveGBean(configName);
    
    }

}
