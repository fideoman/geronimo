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
package org.apache.geronimo.tomcat;

import java.io.File;
import java.net.URI;
import java.security.PermissionCollection;
import java.util.*;
import javax.management.ObjectName;

import junit.framework.TestCase;
import org.apache.catalina.authenticator.Constants;
import org.apache.catalina.deploy.LoginConfig;

import org.apache.geronimo.connector.outbound.connectiontracking.ConnectionTrackingCoordinator;
import org.apache.geronimo.gbean.GBeanData;
import org.apache.geronimo.j2ee.j2eeobjectnames.J2eeContext;
import org.apache.geronimo.j2ee.j2eeobjectnames.J2eeContextImpl;
import org.apache.geronimo.j2ee.j2eeobjectnames.NameFactory;
import org.apache.geronimo.kernel.Kernel;
import org.apache.geronimo.kernel.management.State;
import org.apache.geronimo.security.SecurityServiceImpl;
import org.apache.geronimo.security.deploy.Principal;
import org.apache.geronimo.security.deploy.Security;
import org.apache.geronimo.security.jaas.GeronimoLoginConfiguration;
import org.apache.geronimo.security.jaas.JaasLoginService;
import org.apache.geronimo.security.jaas.LoginModuleGBean;
import org.apache.geronimo.security.realm.GenericSecurityRealm;
import org.apache.geronimo.system.serverinfo.ServerInfo;
import org.apache.geronimo.tomcat.connector.HTTPConnector;
import org.apache.geronimo.transaction.context.TransactionContextManager;
import org.apache.geronimo.transaction.manager.TransactionManagerImpl;
import org.apache.geronimo.transaction.OnlineUserTransaction;


/**
 * @version $Rev: 111239 $ $Date: 2004-12-08 02:29:11 -0700 (Wed, 08 Dec 2004) $
 */
public class AbstractWebModuleTest extends TestCase {

    protected static final String securityRealmName = "demo-properties-realm";
    protected static final String POLICY_CONTEXT_ID = "securetest";


    protected Kernel kernel;
    private GBeanData container;
    private ObjectName containerName;
    private ObjectName connectorName;
    private GBeanData connector;
    private ObjectName webModuleName;
    private ObjectName tmName;
    private ObjectName ctcName;
    private GBeanData tm;
    private GBeanData ctc;
    private ObjectName tcmName;
    private GBeanData tcm;
    private ClassLoader cl;
    private J2eeContext moduleContext = new J2eeContextImpl("tomcat.test", "test", "null", "tomcatTest", null, null);
    private GBeanData securityServiceGBean;
    protected ObjectName securityServiceName;
    private ObjectName loginServiceName;
    private GBeanData loginServiceGBean;
    private GBeanData loginConfigurationGBean;
    protected ObjectName loginConfigurationName;
    protected GBeanData propertiesLMGBean;
    protected ObjectName propertiesLMName;
    protected ObjectName propertiesRealmName;
    private GBeanData propertiesRealmGBean;
    private ObjectName serverInfoName;
    private GBeanData serverInfoGBean;

    public void testDummy() throws Exception {
    }

    protected void setUpInsecureAppContext() throws Exception {

        GBeanData app = new GBeanData(webModuleName, TomcatWebAppContext.GBEAN_INFO);
        app.setAttribute("webAppRoot", new File("target/var/catalina/webapps/war1/").toURI());
        app.setAttribute("webClassPath", new URI[]{});
        app.setAttribute("configurationBaseUrl", new File("target/var/catalina/webapps/war1/WEB-INF/web.xml").toURL());
        app.setAttribute("componentContext", Collections.EMPTY_MAP);
        app.setReferencePattern("Container", containerName);
        OnlineUserTransaction userTransaction = new OnlineUserTransaction();
        app.setAttribute("userTransaction", userTransaction);
        app.setReferencePattern("TransactionContextManager", tcmName);
        app.setReferencePattern("TrackedConnectionAssociator", ctcName);
        app.setAttribute("path", "/test");

        start(app);
    }

    protected ObjectName setUpJAASSecureAppContext(Set securityConstraints, Set securityRoles) throws Exception {
        GBeanData app = new GBeanData(webModuleName, TomcatWebAppContext.GBEAN_INFO);
        app.setAttribute("webAppRoot", new File("target/var/catalina/webapps/war3/").toURI());
        app.setAttribute("webClassPath", new URI[]{});
        app.setAttribute("configurationBaseUrl", new File("target/var/catalina/webapps/war3/WEB-INF/web.xml").toURL());
        app.setAttribute("path", "/securetest");
        app.setAttribute("policyContextID", POLICY_CONTEXT_ID);

        LoginConfig loginConfig = new LoginConfig();
        loginConfig.setAuthMethod(Constants.FORM_METHOD);
        loginConfig.setRealmName("Test JAAS Realm");
        loginConfig.setLoginPage("/auth/logon.html?param=test");
        loginConfig.setErrorPage("/auth/logonError.html?param=test");
        app.setAttribute("loginConfig", loginConfig);
        app.setAttribute("loginConfig", loginConfig);

        app.setAttribute("securityConstraints", securityConstraints);
        app.setAttribute("securityRoles", securityRoles);

        TomcatJAASRealm realm = new TomcatJAASRealm("demo-properties-realm");
        realm.setUserClassNames("org.apache.geronimo.security.realm.providers.GeronimoUserPrincipal");
        realm.setRoleClassNames("org.apache.geronimo.security.realm.providers.GeronimoGroupPrincipal");
        app.setAttribute("tomcatRealm", realm);

        OnlineUserTransaction userTransaction = new OnlineUserTransaction();
        app.setAttribute("userTransaction", userTransaction);
        app.setReferencePattern("TransactionContextManager", tcmName);
        app.setReferencePattern("TrackedConnectionAssociator", ctcName);

        app.setAttribute("componentContext", Collections.EMPTY_MAP);
        app.setReferencePattern("Container", containerName);
        app.setAttribute("kernel", null);

        start(app);

        return webModuleName;
    }

    protected ObjectName setUpSecureAppContext(Security securityConfig,
                                               Set securityConstraints,
                                               PermissionCollection uncheckedPermissions,
                                               PermissionCollection excludedPermissions,
                                               Map rolePermissions,
                                               Set securityRoles)
            throws Exception {

        GBeanData app = new GBeanData(webModuleName, TomcatWebAppContext.GBEAN_INFO);
        app.setAttribute("webAppRoot", new File("target/var/catalina/webapps/war3/").toURI());
        app.setAttribute("webClassPath", new URI[]{});
        app.setAttribute("configurationBaseUrl", new File("target/var/catalina/webapps/war3/WEB-INF/web.xml").toURL());
        app.setAttribute("path", "/securetest");
        app.setAttribute("policyContextID", POLICY_CONTEXT_ID);
 
        LoginConfig loginConfig = new LoginConfig();
        loginConfig.setAuthMethod(Constants.FORM_METHOD);
        loginConfig.setRealmName("Test JACC Realm");
        loginConfig.setLoginPage("/auth/logon.html?param=test");
        loginConfig.setErrorPage("/auth/logonError.html?param=test");
        app.setAttribute("loginConfig", loginConfig);

        app.setAttribute("securityConstraints", securityConstraints);
        app.setAttribute("securityRoles", securityRoles);

        TomcatGeronimoRealm realm = new TomcatGeronimoRealm(POLICY_CONTEXT_ID,
                                                            securityConfig,
                                                            "demo-properties-realm",
                                                            securityRoles,
                                                            uncheckedPermissions,
                                                            excludedPermissions,
                                                            rolePermissions);
        realm.setUserClassNames("org.apache.geronimo.security.realm.providers.GeronimoUserPrincipal");
        realm.setRoleClassNames("org.apache.geronimo.security.realm.providers.GeronimoGroupPrincipal");
        app.setAttribute("tomcatRealm", realm);

        OnlineUserTransaction userTransaction = new OnlineUserTransaction();
        app.setAttribute("userTransaction", userTransaction);
        app.setReferencePattern("TransactionContextManager", tcmName);
        app.setReferencePattern("TrackedConnectionAssociator", ctcName);

        app.setAttribute("componentContext", Collections.EMPTY_MAP);
        app.setReferencePattern("Container", containerName);
        start(app);

        return webModuleName;
    }

    protected void setUpSecurity() throws Exception {

        loginConfigurationName = new ObjectName("geronimo.security:type=LoginConfiguration");
        loginConfigurationGBean = new GBeanData(loginConfigurationName, GeronimoLoginConfiguration.getGBeanInfo());
        Set configurations = new HashSet();
        configurations.add(new ObjectName("geronimo.server:j2eeType=SecurityRealm,*"));
        configurations.add(new ObjectName("geronimo.server:j2eeType=ConfigurationEntry,*"));
        loginConfigurationGBean.setReferencePatterns("Configurations", configurations);

        securityServiceName = new ObjectName("geronimo.server:j2eeType=SecurityService");
        securityServiceGBean = new GBeanData(securityServiceName, SecurityServiceImpl.GBEAN_INFO);
        securityServiceGBean.setAttribute("policyConfigurationFactory", "org.apache.geronimo.security.jacc.GeronimoPolicyConfigurationFactory");

        loginServiceName = JaasLoginService.OBJECT_NAME;
        loginServiceGBean = new GBeanData(loginServiceName, JaasLoginService.GBEAN_INFO);
        loginServiceGBean.setReferencePattern("Realms", new ObjectName("geronimo.server:j2eeType=SecurityRealm,*"));
        loginServiceGBean.setAttribute("algorithm", "HmacSHA1");
        loginServiceGBean.setAttribute("password", "secret");

        propertiesLMName = new ObjectName("geronimo.security:type=LoginModule,name=demo-properties-login");
        propertiesLMGBean = new GBeanData(propertiesLMName, LoginModuleGBean.GBEAN_INFO);
        propertiesLMGBean.setAttribute("loginModuleClass", "org.apache.geronimo.security.realm.providers.PropertiesFileLoginModule");
        propertiesLMGBean.setAttribute("serverSide", Boolean.TRUE);
        Properties options = new Properties();
        options.setProperty("usersURI", "src/test-resources/data/users.properties");
        options.setProperty("groupsURI", "src/test-resources/data/groups.properties");
        propertiesLMGBean.setAttribute("options", options);
        propertiesLMGBean.setAttribute("loginDomainName", "demo-properties-realm");

        propertiesRealmName = new ObjectName("geronimo.server:j2eeType=SecurityRealm,name=demo-properties-realm");
        propertiesRealmGBean = new GBeanData(propertiesRealmName, GenericSecurityRealm.GBEAN_INFO);
        propertiesRealmGBean.setReferencePattern("ServerInfo", serverInfoName);
        propertiesRealmGBean.setAttribute("realmName", "demo-properties-realm");
        Properties config = new Properties();
        config.setProperty("LoginModule.1.REQUIRED", propertiesLMName.getCanonicalName());
        propertiesRealmGBean.setAttribute("loginModuleConfiguration", config);
        Principal.PrincipalEditor principalEditor = new Principal.PrincipalEditor();
        principalEditor.setAsText("metro=org.apache.geronimo.security.realm.providers.GeronimoUserPrincipal");
        propertiesRealmGBean.setAttribute("defaultPrincipal", principalEditor.getValue());

        start(loginConfigurationGBean);
        start(securityServiceGBean);
        start(loginServiceGBean);
        start(propertiesLMGBean);
        start(propertiesRealmGBean);

    }

    protected void tearDownSecurity() throws Exception {
        stop(propertiesRealmName);
        stop(propertiesLMName);
        stop(loginServiceName);
        stop(securityServiceName);
        stop(loginConfigurationName);
    }

    private void start(GBeanData gbeanData) throws Exception {
        kernel.loadGBean(gbeanData, cl);
        kernel.startGBean(gbeanData.getName());
        if (((Integer) kernel.getAttribute(gbeanData.getName(), "state")).intValue() != State.RUNNING_INDEX) {
            fail("gbean not started: " + gbeanData.getName());
        }
    }

    protected void stop(ObjectName name) throws Exception {
        kernel.stopGBean(name);
        kernel.unloadGBean(name);
    }

    protected void setUp() throws Exception {
        cl = this.getClass().getClassLoader();
        containerName = NameFactory.getWebComponentName(null, null, null, null, "tomcatContainer", "WebResource", moduleContext);
        connectorName = NameFactory.getWebComponentName(null, null, null, null, "tomcatConnector", "WebResource", moduleContext);
        webModuleName = NameFactory.getWebComponentName(null, null, null, null, NameFactory.WEB_MODULE, "WebResource", moduleContext);

        tmName = NameFactory.getComponentName(null, null, null, null, "TransactionManager", NameFactory.JTA_RESOURCE, moduleContext);
        tcmName = NameFactory.getComponentName(null, null, null, null, "TransactionContextManager", NameFactory.JTA_RESOURCE, moduleContext);

        ctcName = new ObjectName("geronimo.test:role=ConnectionTrackingCoordinator");

        kernel = new Kernel("test.kernel");
        kernel.boot();

        serverInfoName = new ObjectName("geronimo.system:role=ServerInfo");
        serverInfoGBean = new GBeanData(serverInfoName, ServerInfo.GBEAN_INFO);
        serverInfoGBean.setAttribute("baseDirectory", ".");

        start(serverInfoGBean);

        // Need to override the constructor for unit tests
        container = new GBeanData(containerName, TomcatContainer.GBEAN_INFO);
        container.setAttribute("catalinaHome", "target/var/catalina");
        container.setAttribute("endorsedDirs", "target/endorsed");
        container.setReferencePattern("ServerInfo", serverInfoName);

        connector = new GBeanData(connectorName, HTTPConnector.GBEAN_INFO);
        connector.setAttribute("port", new Integer(8080));
        connector.setReferencePattern("TomcatContainer", containerName);

        start(container);
        start(connector);

        tm = new GBeanData(tmName, TransactionManagerImpl.GBEAN_INFO);
        Set patterns = new HashSet();
        patterns.add(ObjectName.getInstance("geronimo.server:j2eeType=JCAManagedConnectionFactory,*"));
        tm.setAttribute("defaultTransactionTimeoutSeconds", new Integer(10));
        tm.setReferencePatterns("ResourceManagers", patterns);
        start(tm);
        tcm = new GBeanData(tcmName, TransactionContextManager.GBEAN_INFO);
        tcm.setReferencePattern("TransactionManager", tmName);
        start(tcm);
        ctc = new GBeanData(ctcName, ConnectionTrackingCoordinator.GBEAN_INFO);
        start(ctc);
    }

    protected void tearDown() throws Exception {
        stop(ctcName);
        stop(tmName);
        stop(containerName);
        stop(serverInfoName);
        kernel.shutdown();
    }
}
