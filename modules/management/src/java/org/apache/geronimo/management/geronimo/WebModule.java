/**
 *
 * Copyright 2004 The Apache Software Foundation
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
package org.apache.geronimo.management.geronimo;

import java.net.URL;

/**
 * Geronimo extension to the standard JSR-77 WebModule type.
 *
 * @version $Rev$ $Date$
 */
public interface WebModule extends org.apache.geronimo.management.WebModule {
    /**
     * Gets the web context for this web app.
     */
    public String getContextPath();

    /**
     * Gets the ObjectName of the WebContainer that's hosting this
     * WebModule.
     */
    public WebContainer getContainer();

    /**
     * Gets a URL that can be used to access this web application.  Prefers
     * HTTP if available, then HTTPS, then AJP, depending on what the hosting
     * web container makes available.
     */
    public URL getURLFor();

    /**
     * Gets a name for this WAR file that can be used to identify the WAR to a
     * user.  This is not guaranteed to be anything in particular.
     */
    public String getWARName();

    /**
     * Gets the location of the web module "source" (typically an exploded
     * directory).  This is where e.g. a web server would look for static
     * content or a user would copy updated JSPs to.
     */
    public URL getWARDirectory();
}
