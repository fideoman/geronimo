/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.geronimo.aries.builder;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.jar.JarFile;

import org.apache.aries.application.management.AriesApplication;
import org.apache.aries.application.management.AriesApplicationManager;
import org.apache.aries.application.utils.filesystem.FileSystem;
import org.apache.geronimo.common.DeploymentException;
import org.apache.geronimo.deployment.ConfigurationBuilder;
import org.apache.geronimo.deployment.DeploymentContext;
import org.apache.geronimo.deployment.ModuleIDBuilder;
import org.apache.geronimo.gbean.GBeanLifecycle;
import org.apache.geronimo.gbean.annotation.GBean;
import org.apache.geronimo.gbean.annotation.ParamReference;
import org.apache.geronimo.gbean.annotation.ParamSpecial;
import org.apache.geronimo.gbean.annotation.SpecialAttributeType;
import org.apache.geronimo.kernel.GBeanNotFoundException;
import org.apache.geronimo.kernel.Kernel;
import org.apache.geronimo.kernel.config.ConfigurationStore;
import org.apache.geronimo.kernel.repository.Artifact;
import org.apache.geronimo.kernel.repository.ArtifactResolver;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version $Rev:385232 $ $Date$
 */
@GBean(j2eeType = "ConfigBuilder")
public class ApplicationConfigBuilder implements ConfigurationBuilder, GBeanLifecycle {
    
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfigBuilder.class);
    
    private BundleContext bundleContext;
    private ApplicationInstaller installer;

    public ApplicationConfigBuilder(@ParamReference(name="Installer") ApplicationInstaller installer,
                                 @ParamSpecial(type = SpecialAttributeType.kernel) Kernel kernel,
                                 @ParamSpecial(type = SpecialAttributeType.bundleContext) BundleContext bundleContext) 
        throws GBeanNotFoundException {
        this.installer = installer;
        this.bundleContext = bundleContext;
    }
    
    public void doStart() throws Exception {
    }

    public void doStop() {
    }

    public void doFail() {
        doStop();
    }
        
    private AriesApplicationManager getAriesApplicationManager() {
        ServiceReference ref = 
            bundleContext.getServiceReference(AriesApplicationManager.class.getName());
        if (ref != null) {
            return (AriesApplicationManager) bundleContext.getService(ref);
        } else {
            return null;
        }
    }
           
    public Object getDeploymentPlan(File planFile, 
                                    JarFile jarFile, 
                                    ModuleIDBuilder idBuilder) 
        throws DeploymentException {
        if (jarFile == null) {
            return null;
        }
        
        if (jarFile.getName().endsWith(".eba")) {
            return new Object();
        }
        
        return null;
    }
    
    public Artifact getConfigurationID(Object plan, 
                                       JarFile jarFile, 
                                       ModuleIDBuilder idBuilder)
        throws IOException, DeploymentException {
        
        Artifact name = new Artifact("eba", "application", "0.0.0", "jar");
        
        return name;
    }
    
    public DeploymentContext buildConfiguration(boolean inPlaceDeployment, 
                                                Artifact configId, 
                                                Object plan,
                                                JarFile jarFile, 
                                                Collection<ConfigurationStore> configurationStores, 
                                                ArtifactResolver artifactResolver, 
                                                ConfigurationStore targetConfigurationStore) 
        throws IOException, DeploymentException {
                
        AriesApplicationManager appManager = getAriesApplicationManager();
        
        AriesApplication app = null;
        try {
            app = appManager.createApplication(FileSystem.getFSRoot(new File(jarFile.getName())));
        } catch (Exception e) {
            throw new DeploymentException("Error creating Aries Application", e);
        }
        
        try {
            app = appManager.resolve(app);
        } catch (Exception e) {
            throw new DeploymentException("Error resolving Aries Application", e);
        }
                
        DeploymentContext context = installer.startInstall(app, targetConfigurationStore);
        
        return context;
    }
    
}
