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


package org.apache.geronimo.web25.deployment.security;

import org.apache.geronimo.xbeans.geronimo.jaspi.JaspiAuthModuleType;
import org.apache.geronimo.xbeans.geronimo.jaspi.JaspiConfigProviderType;
import org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthConfigType;
import org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType;

/**
 * Wrapper interface for jetty and tomcat Authentication elemens.  Need jaxb :-)
 * @version $Rev$ $Date$
 */
public interface AuthenticationWrapper {

    /**
     * Gets the "configProvider" element
     */
    JaspiConfigProviderType getConfigProvider();

    /**
     * True if has "configProvider" element
     */
    boolean isSetConfigProvider();

    /**
     * Gets the "serverAuthConfig" element
     */
    JaspiServerAuthConfigType getServerAuthConfig();

    /**
     * True if has "serverAuthConfig" element
     */
    boolean isSetServerAuthConfig();

    /**
     * Gets the "serverAuthContext" element
     */
    JaspiServerAuthContextType getServerAuthContext();

    /**
     * True if has "serverAuthContext" element
     */
    boolean isSetServerAuthContext();

    /**
     * Gets the "serverAuthModule" element
     */
    JaspiAuthModuleType getServerAuthModule();

    /**
     * True if has "serverAuthModule" element
     */
    boolean isSetServerAuthModule();

}
