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
package org.apache.geronimo.deployment.service;

import org.apache.geronimo.common.DeploymentException;
import org.apache.geronimo.deployment.DeploymentContext;
import org.apache.geronimo.gbean.AbstractName;
import org.apache.geronimo.gbean.ReferencePatterns;
import org.apache.xmlbeans.XmlObject;
import org.osgi.framework.Bundle;

/**
 * @version $Rev: 809078 $ $Date: 2009-08-29 08:51:12 +0800 (Sat, 29 Aug 2009) $
 */
public interface XmlReferenceBuilder {

    String getNamespace();

    ReferencePatterns getReferences(XmlObject xmlObject, DeploymentContext context, AbstractName moduleAbstractName, Bundle bundle) throws DeploymentException;

}
