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
package org.apache.geronimo.system.plugin;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.util.Map;
import javax.security.auth.login.FailedLoginException;
import org.apache.geronimo.kernel.repository.Artifact;

/**
 * Knows how to import and export configurations
 *
 * @version $Rev: 46019 $ $Date: 2004-09-14 05:56:06 -0400 (Tue, 14 Sep 2004) $
 */
public interface PluginInstaller {
    /**
     * Lists the plugins available for download in a particular Geronimo repository.
     *
     * @param mavenRepository The base URL to the maven repository.  This must
     *                        contain the file geronimo-plugins.xml
     * @param username Optional username, if the maven repo uses HTTP Basic authentication.
     *                 Set this to null if no authentication is required.
     * @param password Optional password, if the maven repo uses HTTP Basic authentication.
     *                 Set this to null if no authentication is required.
     */
    public PluginList listPlugins(URL mavenRepository, String username, String password) throws IOException, FailedLoginException;

    /**
     * Lists the plugins installed in the local Geronimo server, by name and
     * ID.
     *
     * @return A Map with key type String (plugin name) and value type Artifact
     *         (config ID of the plugin).
     */
    public Map getInstalledPlugins();

    /**
     * Gets a CofigurationMetadata for a configuration installed in the local
     * server.  Should load a saved one if available, or else create a new
     * default one to the best of its abilities.
     *
     * @param moduleId Identifies the configuration.  This must match a
     *                 configuration currently installed in the local server.
     *                 The configId must be fully resolved (isResolved() == true)
     */
    public PluginMetadata getPluginMetadata(Artifact moduleId);

    /**
     * Saves a ConfigurationMetadata for a particular plugin, if the server is
     * able to record it.  This can be used if you later re-export the plugin,
     * or just want to review the information for a particular installed
     * plugin.
     *
     * @param metadata The data to save.  The contained configId (which must
     *                 be fully resolved) identifies the configuration to save
     *                 this for.
     */
    public void updatePluginMetadata(PluginMetadata metadata);

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
     * @param pluginsToInstall The list of configurations to install
     */
    public DownloadResults install(PluginList pluginsToInstall, String username, String password);

    /**
     * Installs a configuration from a remote repository into the local Geronimo server,
     * including all its dependencies.  The method blocks until the operation completes,
     * but the caller will be notified of progress frequently along the way (using the
     * supplied DownloadPoller).  Therefore the caller is meant to create the poller and
     * then call this method in a background thread.  Note that this method does not
     * throw exceptions on failure, but instead sets the failure property of the
     * DownloadPoller.
     *
     * @param pluginsToInstall The list of configurations to install
     * @param username         Optional username, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     * @param password         Optional password, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     * @param poller           Will be notified with status updates as the download proceeds
     */
    public void install(PluginList pluginsToInstall, String username, String password, DownloadPoller poller);

    /**
     * Installs a configuration from a remote repository into the local Geronimo server,
     * including all its dependencies.  The method returns immediately, providing a key
     * that can be used to poll the status of the download operation.  Note that the
     * installation does not throw exceptions on failure, but instead sets the failure
     * property of the DownloadResults that the caller can poll for.
     *
     * @param pluginsToInstall The list of configurations to install
     * @param username         Optional username, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     * @param password         Optional password, if the maven repo uses HTTP Basic authentication.
     *                         Set this to null if no authentication is required.
     *
     * @return A key that can be passed to checkOnInstall
     */
    public Object startInstall(PluginList pluginsToInstall, String username, String password);

    /**
     * Installs a configuration downloaded from a remote repository into the local Geronimo
     * server, including all its dependencies.  The method returns immediately, providing a
     * key that can be used to poll the status of the download operation.  Note that the
     * installation does not throw exceptions on failure, but instead sets the failure
     * property of the DownloadResults that the caller can poll for.
     *
     * @param carFile   A CAR file downloaded from a remote repository.  This is a packaged
     *                  configuration with included configuration information, but it may
     *                  still have external dependencies that need to be downloaded
     *                  separately.  The metadata in the CAR file includes a repository URL
     *                  for these downloads, and the username and password arguments are
     *                  used in conjunction with that.
     * @param username  Optional username, if the maven repo uses HTTP Basic authentication.
     *                  Set this to null if no authentication is required.
     * @param password  Optional password, if the maven repo uses HTTP Basic authentication.
     *                  Set this to null if no authentication is required.
     *
     * @return A key that can be passed to checkOnInstall
     */
    public Object startInstall(File carFile, String username, String password);

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
