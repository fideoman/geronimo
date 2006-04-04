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
package org.apache.geronimo.kernel.config.xstream;

import java.net.URI;

import com.thoughtworks.xstream.XStream;
import org.apache.geronimo.kernel.config.ConfigurationData;
import org.apache.geronimo.kernel.config.ConfigurationModuleType;
import org.apache.geronimo.kernel.repository.Artifact;
import org.apache.geronimo.kernel.repository.Dependency;
import org.apache.geronimo.kernel.repository.Version;
import org.apache.geronimo.kernel.repository.ImportType;
import org.apache.geronimo.gbean.AbstractName;
import org.apache.geronimo.gbean.GBeanData;
import org.apache.geronimo.gbean.GBeanInfo;
import org.apache.geronimo.gbean.AbstractNameQuery;

/**
 * @version $Rev$ $Date$
 */
public final class XStreamUtil {
    private XStreamUtil() {
    }

    public static XStream createXStream() {
        XStream xstream = new XStream();
        xstream.alias("configurationData", ConfigurationData.class);

        // AbstractName
        xstream.alias("abstractName", AbstractName.class);
        xstream.addImmutableType(AbstractName.class);
        xstream.registerConverter(new AbstractNameConverter());

        // AbstractNameQuery
        xstream.alias("abstractNameQuery", AbstractNameQuery.class);
        xstream.addImmutableType(AbstractNameQuery.class);
        xstream.registerConverter(new AbstractNameQueryConverter());

        // Artifact
        xstream.alias("artifact", Artifact.class);
        xstream.addImmutableType(Artifact.class);

        // ConfigurationModuleTypeConverter
        xstream.alias("moduleType", ConfigurationModuleType.class);
        xstream.addImmutableType(ConfigurationModuleType.class);
        xstream.registerConverter(new ConfigurationModuleTypeConverter());

        // Dependency
        xstream.alias("dependency", Dependency.class);
        xstream.addImmutableType(Dependency.class);

        // GBeanData
        xstream.alias("gbean", GBeanData.class);
        xstream.registerConverter(new GBeanDataConverter(xstream.getClassMapper()));

        // GBeanInfo
        xstream.alias("gbean-info", GBeanInfo.class);

        // w3c Dom
        xstream.registerConverter(new DomConverter());

        // ImportType
        xstream.addImmutableType(ImportType.class);
        xstream.registerConverter(new ImportTypeConverter());

        // Version
        xstream.alias("version", Version.class);
        xstream.addImmutableType(Version.class);
        xstream.registerConverter(new VersionConverter());

        // URI
        xstream.alias("uri", URI.class);
        xstream.addImmutableType(URI.class);
        xstream.registerConverter(new URIConverter());

        // XStreamGBeanState
        xstream.registerConverter(new XStreamGBeanStateConverter());

        return xstream;
    }

}
