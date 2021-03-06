/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.apache.geronimo.jetty8.security;

import java.util.HashMap;
import java.util.Map;
import java.security.AccessControlContext;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.RegistrationListener;
import javax.security.auth.message.config.ServerAuthConfig;

import org.apache.geronimo.gbean.annotation.GBean;
import org.apache.geronimo.gbean.annotation.ParamAttribute;
import org.apache.geronimo.gbean.annotation.ParamReference;
import org.apache.geronimo.jetty8.handler.JaccSecurityHandler;
import org.apache.geronimo.jetty8.security.auth.GeronimoJaspiAuthenticator;
import org.apache.geronimo.jetty8.security.auth.JAASLoginService;
import org.apache.geronimo.security.jacc.RunAsSource;
import org.apache.geronimo.security.ContextManager;
import org.apache.geronimo.security.jaas.ConfigurationFactory;
import org.apache.geronimo.security.jaspi.impl.GeronimoLoginService;
import org.apache.geronimo.security.jaspi.impl.JaspicCallbackHandler;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.jaspi.JaspiAuthenticator;
import org.eclipse.jetty.security.jaspi.ServletCallbackHandler;

/**
 * Fetches a ServerAuthConfig out of the AuthConfigFactory
 * 
 * @version $Rev$ $Date$
 */

@GBean
public class AuthConfigProviderHandlerFactory implements SecurityHandlerFactory {

    private static final String POLICY_CONTEXT_ID_KEY = "javax.security.jacc.PolicyContext";

    private final Map authConfigProperties = new HashMap<Object, Object>();
    private final Subject serviceSubject = null;
    private final boolean allowLazyAuthentication;
    private final ConfigurationFactory configurationFactory;
    private final String messageLayer;
    private final String appContext;


    public AuthConfigProviderHandlerFactory(@ParamAttribute(name = "messageLayer")String messageLayer,
                                            @ParamAttribute(name = "appContext")String appContext,
                                            @ParamAttribute(name = "allowLazyAuthentication") boolean allowLazyAuthentication,
                                            @ParamReference(name = "ConfigurationFactory") ConfigurationFactory configurationFactory
    ) throws AuthException {
        String appContext1 = appContext;
        this.allowLazyAuthentication = allowLazyAuthentication;
        this.configurationFactory = configurationFactory;
        this.messageLayer = messageLayer;
        this.appContext = appContext;
    }

    public SecurityHandler buildSecurityHandler(String policyContextID, Subject defaultSubject, RunAsSource runAsSource, boolean checkRolePermissions) throws AuthException {
        if (defaultSubject == null) {
            defaultSubject = ContextManager.EMPTY;
        }
        AccessControlContext defaultAcc = ContextManager.registerSubjectShort(defaultSubject, null);
        JettyIdentityService identityService = new JettyIdentityService(defaultAcc, defaultSubject, runAsSource);
        GeronimoLoginService loginService = new GeronimoLoginService(configurationFactory, identityService);
        authConfigProperties.put(POLICY_CONTEXT_ID_KEY, policyContextID);
        AuthConfigFactory authConfigFactory = AuthConfigFactory.getFactory();
        RegistrationListener listener = new RegistrationListener() {

            public void notify(String layer, String appContext) {
            }
        };
        AuthConfigProvider authConfigProvider = authConfigFactory.getConfigProvider(messageLayer, appContext, listener);
        CallbackHandler callbackHandler = new JaspicCallbackHandler(loginService);
        ServerAuthConfig serverAuthConfig = authConfigProvider.getServerAuthConfig(messageLayer, appContext, callbackHandler);
        //TODO appContext is supposed to be server-name<space>context-root
        Authenticator authenticator = new GeronimoJaspiAuthenticator(serverAuthConfig, authConfigProperties, callbackHandler, serviceSubject, allowLazyAuthentication, identityService);
        //login service functionality is already inside the servletCallbackHandler
        return new JaccSecurityHandler(policyContextID, authenticator, new JAASLoginService(null, loginService), identityService, defaultAcc);
    }

}