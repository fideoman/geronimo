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
package org.apache.geronimo.kernel.config;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * @version $Rev$ $Date$
 */
public interface ConfigurationMarshaler {
    void writeConfigurationData(ConfigurationData configurationData, OutputStream out) throws IOException;

    ConfigurationData readConfigurationData(InputStream in) throws IOException, ClassNotFoundException;

    GBeanState newGBeanState(Collection gbeans);
}
