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
package org.apache.geronimo.system.configuration;

import java.io.IOException;
import java.net.URL;
import javax.security.auth.login.FailedLoginException;

/**
 * Knows how to import and export configurations
 *
 * @version $Rev: 46019 $ $Date: 2004-09-14 05:56:06 -0400 (Tue, 14 Sep 2004) $
 */
public interface ConfigurationInstaller {
    /**
     * Lists the configurations available for download in a particular Geronimo repository.
     * @param mavenRepository The base URL to the maven repository
     * @param username Optional username, if the maven repo uses HTTP Basic authentication.
     *                 Set this to null if no authentication is required.
     * @param password Optional password, if the maven repo uses HTTP Basic authentication.
     *                 Set this to null if no authentication is required.
     */
    public ConfigurationList listConfigurations(URL mavenRepository, String username, String password) throws IOException, FailedLoginException;

    /**
     * Installs a configuration from a remote repository into the local Geronimo server,
     * including all its dependencies.  The caller will get the results when the
     * operation completes.  Note that this method does not throw exceptions on failure,
     * but instead sets the failure property of the DownloadResults.
     *
     * @param username         Optional username, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     * @param password         Optional password, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     * @param configsToInstall The list of configurations to install
     */
    public DownloadResults install(ConfigurationList configsToInstall, String username, String password);

    /**
     * Installs a configuration from a remote repository into the local Geronimo server,
     * including all its dependencies.  The method blocks until the operation completes,
     * but the caller will be notified of progress frequently along the way (using the
     * supplied DownloadPoller).  Therefore the caller is meant to create the poller and
     * then call this method in a background thread.  Note that this method does not
     * throw exceptions on failure, but instead sets the failure property of the
     * DownloadPoller.
     *
     * @param configsToInstall The list of configurations to install
     * @param username         Optional username, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     * @param password         Optional password, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     * @param poller           Will be notified with status updates as the download proceeds
     */
    public void install(ConfigurationList configsToInstall, String username, String password, DownloadPoller poller);

    /**
     * Installs a configuration from a remote repository into the local Geronimo server,
     * including all its dependencies.  The method return immediately, providing a key
     * that can be used to poll the status of the download operation.  Note that the
     * installation does not throw exceptions on failure, but instead sets the failure
     * property of the DownloadResults that the caller can poll for.
     *
     * @param configsToInstall The list of configurations to install
     * @param username         Optional username, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     * @param password         Optional password, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     *
     * @return A key that can be passed to checkOnInstall
     */
    public Object startInstall(ConfigurationList configsToInstall, String username, String password);

    /**
     * Gets the current progress of a download operation.  Note that once the
     * DownloadResults is returned for this operation shows isFinished = true,
     * the operation will be forgotten, so the caller should be careful not to
     * call this again after the download has finished.
     *
     * @param key Identifies the operation to check on
     */
    public DownloadResults checkOnInstall(Object key);
}
