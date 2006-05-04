/**
 *
 * Copyright 2004-2005 The Apache Software Foundation
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

package org.apache.geronimo.naming.deployment;

import org.apache.geronimo.common.DeploymentException;
import org.apache.geronimo.common.UnresolvedReferenceException;
import org.apache.geronimo.gbean.AbstractNameQuery;
import org.apache.geronimo.j2ee.deployment.EARContext;
import org.apache.geronimo.j2ee.deployment.Module;
import org.apache.geronimo.j2ee.deployment.RefContext;
import org.apache.geronimo.j2ee.deployment.ServiceReferenceBuilder;
import org.apache.geronimo.j2ee.j2eeobjectnames.NameFactory;
import org.apache.geronimo.kernel.ClassLoading;
import org.apache.geronimo.kernel.config.Configuration;
import org.apache.geronimo.kernel.repository.Artifact;
import org.apache.geronimo.naming.java.ComponentContextBuilder;
import org.apache.geronimo.xbeans.geronimo.naming.GerEjbLocalRefType;
import org.apache.geronimo.xbeans.geronimo.naming.GerEjbRefType;
import org.apache.geronimo.xbeans.geronimo.naming.GerGbeanLocatorType;
import org.apache.geronimo.xbeans.geronimo.naming.GerMessageDestinationType;
import org.apache.geronimo.xbeans.geronimo.naming.GerPatternType;
import org.apache.geronimo.xbeans.geronimo.naming.GerResourceEnvRefType;
import org.apache.geronimo.xbeans.geronimo.naming.GerResourceRefType;
import org.apache.geronimo.xbeans.geronimo.naming.GerServiceRefType;
import org.apache.geronimo.xbeans.j2ee.EjbLocalRefType;
import org.apache.geronimo.xbeans.j2ee.EjbRefType;
import org.apache.geronimo.xbeans.j2ee.EnvEntryType;
import org.apache.geronimo.xbeans.j2ee.MessageDestinationRefType;
import org.apache.geronimo.xbeans.j2ee.MessageDestinationType;
import org.apache.geronimo.xbeans.j2ee.ParamValueType;
import org.apache.geronimo.xbeans.j2ee.PortComponentRefType;
import org.apache.geronimo.xbeans.j2ee.ResourceEnvRefType;
import org.apache.geronimo.xbeans.j2ee.ResourceRefType;
import org.apache.geronimo.xbeans.j2ee.ServiceRefHandlerType;
import org.apache.geronimo.xbeans.j2ee.ServiceRefType;
import org.apache.geronimo.xbeans.j2ee.XsdQNameType;
import org.apache.geronimo.xbeans.j2ee.XsdStringType;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.transaction.UserTransaction;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version $Rev:385232 $ $Date$
 */
public class ENCConfigBuilder {

    private static final String JAXR_CONNECTION_FACTORY_CLASS = "javax.xml.registry.ConnectionFactory";

    public static void registerMessageDestinations(RefContext refContext, String moduleName, MessageDestinationType[] specDestinations, GerMessageDestinationType[] destinations) throws DeploymentException {
        Map nameMap = new HashMap();
        for (int i = 0; i < destinations.length; i++) {
            GerMessageDestinationType destination = destinations[i];
            String name = destination.getMessageDestinationName().trim();
            nameMap.put(name, destination);
            boolean found = false;
            for (int j = 0; j < specDestinations.length; j++) {
                MessageDestinationType specDestination = specDestinations[j];
                if (specDestination.getMessageDestinationName().getStringValue().trim().equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new DeploymentException("No spec DD message-destination for " + name);
            }
        }
        refContext.registerMessageDestionations(moduleName, nameMap);
    }


    public static AbstractNameQuery getGBeanQuery(String j2eeType, GerGbeanLocatorType gerGbeanLocator) {
        AbstractNameQuery abstractNameQuery;
        if (gerGbeanLocator.isSetGbeanLink()) {
            //exact match
            String linkName = gerGbeanLocator.getGbeanLink().trim();
            abstractNameQuery = buildAbstractNameQuery(null, null, linkName, j2eeType, null);

        } else {
            GerPatternType patternType = gerGbeanLocator.getPattern();
            //construct name from components
            abstractNameQuery = buildAbstractNameQuery(patternType, j2eeType, null);
        }
        //TODO check that the query is satisfied.
        return abstractNameQuery;
    }


    static void addEnvEntries(EnvEntryType[] envEntries, ComponentContextBuilder builder, ClassLoader classLoader) throws DeploymentException {
        for (int i = 0; i < envEntries.length; i++) {
            EnvEntryType envEntry = envEntries[i];
            String name = getStringValue(envEntry.getEnvEntryName());
            String type = getStringValue(envEntry.getEnvEntryType());
            String text = getStringValue(envEntry.getEnvEntryValue());
            try {
                builder.addEnvEntry(name, type, text, classLoader);
            } catch (NumberFormatException e) {
                throw new DeploymentException("Invalid env-entry value for name: " + name, e);
            } catch (NamingException e) {
                throw new DeploymentException("Invalid env-entry definition for name: " + name, e);
            }
        }

    }

    static void addResourceRefs(Configuration earContext, RefContext refContext, URI moduleURI, ResourceRefType[] resourceRefs, Map refMap, ClassLoader cl, ComponentContextBuilder builder) throws DeploymentException {
        //TODO configid remove URI parameter?
        if (refMap == null) {
            refMap = Collections.EMPTY_MAP;
        }
        Map newMap = new HashMap();
        for (Iterator it = refMap.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            newMap.put(name.trim(), refMap.get(name));
        }
        refMap = newMap;

        for (int i = 0; i < resourceRefs.length; i++) {
            ResourceRefType resourceRef = resourceRefs[i];
            String name = getStringValue(resourceRef.getResRefName());
            String type = getStringValue(resourceRef.getResType());
            GerResourceRefType gerResourceRef = (GerResourceRefType) refMap.get(name);
            Class iface;
            try {
                iface = cl.loadClass(type);
            } catch (ClassNotFoundException e) {
                throw new DeploymentException("could not load class " + type, e);
            }
            Reference ref;
            if (iface == URL.class) {
                if (gerResourceRef == null || !gerResourceRef.isSetUrl()) {
                    throw new DeploymentException("No url supplied to resolve: " + name);
                }
                try {
                    //TODO expose jsr-77 objects for these guys
                    builder.bind(name, new URL(gerResourceRef.getUrl()));
                } catch (MalformedURLException e) {
                    throw new DeploymentException("Could not convert " + gerResourceRef.getUrl() + " to URL", e);
                }
            } else {
                //determine jsr-77 type from interface
                String j2eeType;


                if ("javax.mail.Session".equals(type)) {
                    j2eeType = NameFactory.JAVA_MAIL_RESOURCE;
                } else if (JAXR_CONNECTION_FACTORY_CLASS.equals(type)) {
                    j2eeType = NameFactory.JAXR_CONNECTION_FACTORY;
                } else {
                    j2eeType = NameFactory.JCA_MANAGED_CONNECTION_FACTORY;
                }
                try {
                    AbstractNameQuery containerId = getResourceContainerId(name, j2eeType, null, gerResourceRef);

                    ref = refContext.getConnectionFactoryRef(containerId, iface, earContext);
                    builder.bind(name, ref);
                } catch (UnresolvedReferenceException e) {

                    StringBuffer errorMessage = new StringBuffer("Unable to resolve resource reference '");
                    errorMessage.append(name);
                    errorMessage.append("' (");
                    if (e.isMultiple()) {
                        errorMessage.append("Found multiple matching resources.  Try being more specific in a resource-ref mapping in your Geronimo deployment plan.");
                    } else if (gerResourceRef == null){
                        errorMessage.append("Could not auto-map to resource.  Try adding a resource-ref mapping to your Geronimo deployment plan.");
                    } else if (gerResourceRef.isSetResourceLink()){
                        errorMessage.append("Could not find resource '");
                        errorMessage.append(gerResourceRef.getResourceLink());
                        errorMessage.append("'.  Perhaps it has not yet been configured, or your application does not have a dependency declared for that resource module?");
                    } else {
                        errorMessage.append("Could not find the resource specified in your Geronimo deployment plan:");
                        errorMessage.append(gerResourceRef.getPattern());
                    }
                    errorMessage.append(")");

                    throw new DeploymentException(errorMessage.toString());
                }
            }
        }

    }

    private static AbstractNameQuery getResourceContainerId(String name, String type, URI moduleURI, GerResourceRefType gerResourceRef) {
        AbstractNameQuery containerId;
        String module = moduleURI == null ? null : moduleURI.toString();
        if (gerResourceRef == null) {
            containerId = buildAbstractNameQuery(null, module, name, type, NameFactory.RESOURCE_ADAPTER_MODULE);
        } else if (gerResourceRef.isSetResourceLink()) {
            containerId = buildAbstractNameQuery(null, module, gerResourceRef.getResourceLink().trim(), type, NameFactory.RESOURCE_ADAPTER_MODULE);
        } else {
            //construct name from components
            GerPatternType patternType = gerResourceRef.getPattern();
            containerId = buildAbstractNameQuery(patternType, type, NameFactory.RESOURCE_ADAPTER_MODULE);
        }
        return containerId;
    }

    static void addResourceEnvRefs(Configuration earContext, RefContext refContext, ResourceEnvRefType[] resourceEnvRefArray, Map refMap, ClassLoader cl, ComponentContextBuilder builder) throws DeploymentException {
        if (refMap == null) {
            refMap = Collections.EMPTY_MAP;
        }
        Map newMap = new HashMap();
        for (Iterator it = refMap.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            newMap.put(name.trim(), refMap.get(name));
        }
        refMap = newMap;

        for (int i = 0; i < resourceEnvRefArray.length; i++) {
            ResourceEnvRefType resourceEnvRef = resourceEnvRefArray[i];
            String name = getStringValue(resourceEnvRef.getResourceEnvRefName());
            String type = getStringValue(resourceEnvRef.getResourceEnvRefType());
            Class iface;
            try {
                iface = cl.loadClass(type);
            } catch (ClassNotFoundException e) {
                throw new DeploymentException("could not load class " + type, e);
            }
            GerResourceEnvRefType gerResourceEnvRef = (GerResourceEnvRefType) refMap.get(name);
            try {
                AbstractNameQuery containerId = getAdminObjectContainerId(name, gerResourceEnvRef);
                Reference ref = refContext.getAdminObjectRef(containerId, iface, earContext);

                builder.bind(name, ref);
            } catch (UnresolvedReferenceException e) {
                throw new DeploymentException("Unable to resolve resource env reference '" + name + "' (" + (e.isMultiple() ? "found multiple matching resources" : "no matching resources found") + ")");
            }
        }
    }

    private static AbstractNameQuery getAdminObjectContainerId(String name, GerResourceEnvRefType gerResourceEnvRef) {
        AbstractNameQuery containerId;
        if (gerResourceEnvRef == null) {
            containerId = buildAbstractNameQuery(null, null, name, NameFactory.JCA_ADMIN_OBJECT, NameFactory.RESOURCE_ADAPTER_MODULE);
        } else if (gerResourceEnvRef.isSetMessageDestinationLink()) {
            containerId = buildAbstractNameQuery(null, null, gerResourceEnvRef.getMessageDestinationLink().trim(), NameFactory.JCA_ADMIN_OBJECT, NameFactory.RESOURCE_ADAPTER_MODULE);
        } else if (gerResourceEnvRef.isSetAdminObjectLink()) {
            String moduleURI = null;
            if (gerResourceEnvRef.isSetAdminObjectModule()) {
                moduleURI = gerResourceEnvRef.getAdminObjectModule().trim();
            }
            containerId = buildAbstractNameQuery(null, moduleURI, gerResourceEnvRef.getAdminObjectLink().trim(), NameFactory.JCA_ADMIN_OBJECT, NameFactory.RESOURCE_ADAPTER_MODULE);
        } else {
            //construct name from components
            GerPatternType patternType = gerResourceEnvRef.getPattern();
            containerId = buildAbstractNameQuery(patternType, NameFactory.JCA_ADMIN_OBJECT, NameFactory.RESOURCE_ADAPTER_MODULE);
        }
        return containerId;
    }

    static void addMessageDestinationRefs(Configuration earContext, RefContext refContext, MessageDestinationRefType[] messageDestinationRefs, ClassLoader cl, ComponentContextBuilder builder) throws DeploymentException {
        for (int i = 0; i < messageDestinationRefs.length; i++) {
            MessageDestinationRefType messageDestinationRef = messageDestinationRefs[i];
            String name = getStringValue(messageDestinationRef.getMessageDestinationRefName());
            String linkName = getStringValue(messageDestinationRef.getMessageDestinationLink());
            String type = getStringValue(messageDestinationRef.getMessageDestinationType());
            Class iface;
            try {
                iface = cl.loadClass(type);
            } catch (ClassNotFoundException e) {
                throw new DeploymentException("could not load class " + type, e);
            }
            String moduleURI = null;
            GerMessageDestinationType destination = (GerMessageDestinationType) refContext.getMessageDestination(linkName);
            if (destination != null) {
                if (destination.isSetAdminObjectLink()) {
                    if (destination.isSetAdminObjectModule()) {
                        moduleURI = destination.getAdminObjectModule().trim();
                    }
                    linkName = destination.getAdminObjectLink().trim();
                }
            } else {
                //well, we know for sure an admin object is not going to be defined in a modules that can have a message-destination
                int pos = linkName.indexOf('#');
                if (pos > -1) {
                    //AMM -- the following line causes blowups; e.g. to look in DayTrader EJB module for a RA -- why is that?!?
                    //moduleURI = linkName.substring(0, pos);
                    linkName = linkName.substring(pos + 1);
                }
            }

            //try to resolve ref based only matching resource-ref-name
            //throws exception if it can't locate ref.
            AbstractNameQuery containerId = buildAbstractNameQuery(null, moduleURI, linkName, NameFactory.JCA_ADMIN_OBJECT, NameFactory.RESOURCE_ADAPTER_MODULE);
            Reference ref = refContext.getAdminObjectRef(containerId, iface, earContext);
            builder.bind(name, ref);

        }

    }

    static void addEJBRefs(Configuration earContext, Configuration ejbContext, RefContext refContext, URI moduleURI, EjbRefType[] ejbRefs, Map ejbRefMap, ClassLoader cl, ComponentContextBuilder builder) throws DeploymentException {
        Map newMap = new HashMap();
        for (Iterator it = ejbRefMap.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            newMap.put(name.trim(), ejbRefMap.get(name));
        }
        ejbRefMap = newMap;
        for (int i = 0; i < ejbRefs.length; i++) {
            EjbRefType ejbRef = ejbRefs[i];

            String ejbRefName = getStringValue(ejbRef.getEjbRefName());
            GerEjbRefType remoteRef = (GerEjbRefType) ejbRefMap.get(ejbRefName);

            Reference ejbReference = addEJBRef(earContext, ejbContext, refContext, moduleURI, ejbRef, remoteRef, cl);
            builder.bind(ejbRefName, ejbReference);
        }
    }

    static Reference addEJBRef(Configuration earContext, Configuration ejbContext, RefContext refContext, URI moduleURI, EjbRefType ejbRef, GerEjbRefType remoteRef, ClassLoader cl) throws DeploymentException {
        String remote = getStringValue(ejbRef.getRemote());
        String refName = getStringValue(ejbRef.getEjbRefName());
        try {
            assureEJBObjectInterface(remote, cl);
        } catch (DeploymentException e) {
            throw new DeploymentException("Error processing 'remote' element for EJB Reference '" + refName + "' for module '" + moduleURI + "': " + e.getMessage());
        }

        String home = getStringValue(ejbRef.getHome());
        try {
            assureEJBHomeInterface(home, cl);
        } catch (DeploymentException e) {
            throw new DeploymentException("Error processing 'home' element for EJB Reference '" + refName + "' for module '" + moduleURI + "': " + e.getMessage());
        }

        Reference ejbReference;
        boolean isSession = "Session".equals(getStringValue(ejbRef.getEjbRefType()));

        if (isSession && remote.equals("javax.management.j2ee.Management") && home.equals("javax.management.j2ee.ManagementHome")) {
            AbstractNameQuery query = new AbstractNameQuery(null, Collections.singletonMap("name", "ejb/mgmt/MEJB"));
            ejbReference = refContext.getEJBRemoteRef(null, ejbContext, null, null, null, null, query, isSession, home, remote);
        } else {

            String ejbLink = null;
            if (remoteRef != null && remoteRef.isSetNsCorbaloc()) {
                try {
                    AbstractNameQuery cssBean;
                    if (remoteRef.isSetCssLink()) {
                        String cssLink = remoteRef.getCssLink().trim();
                        cssBean = buildAbstractNameQuery(null, null, cssLink, NameFactory.CORBA_CSS, NameFactory.EJB_MODULE);
                    } else {
                        GerPatternType css = remoteRef.getCss();
                        cssBean = buildAbstractNameQuery(css, NameFactory.CORBA_CSS, NameFactory.EJB_MODULE);
                    }
                    ejbReference = refContext.getCORBARemoteRef(earContext,
                            cssBean,
                            new URI(getStringValue(remoteRef.getNsCorbaloc())),
                            getStringValue(remoteRef.getName()),
                            home);
                } catch (URISyntaxException e) {
                    throw new DeploymentException("Could not construct CORBA NameServer URI: " + remoteRef.getNsCorbaloc(), e);
                }
            } else {
                Artifact targetConfigId = null;
                String optionalModule = moduleURI == null ? null : moduleURI.toString();
                String requiredModule = null;
                AbstractNameQuery containerQuery = null;
                if (remoteRef != null && remoteRef.isSetEjbLink()) {
                    ejbLink = remoteRef.getEjbLink();
                } else if (ejbRef.isSetEjbLink()) {
                    ejbLink = getStringValue(ejbRef.getEjbLink());
                    targetConfigId = ejbContext.getId();
                }
                if (ejbLink != null) {
                    String[] bits = ejbLink.split("#");
                    if (bits.length == 2) {
                        //look only in specified module.
                        requiredModule = bits[0];
                        if (moduleURI != null) {
                            requiredModule = moduleURI.resolve(requiredModule).getPath();
                        }
                        ejbLink = bits[1];
                    }
                } else if (remoteRef != null) {
                    GerPatternType patternType = remoteRef.getPattern();
                    containerQuery = buildAbstractNameQuery(patternType, null, NameFactory.EJB_MODULE);
                }
                ejbReference = refContext.getEJBRemoteRef(refName, ejbContext, ejbLink, requiredModule, optionalModule, targetConfigId, containerQuery, isSession, home, remote);
            }
        }
        return ejbReference;
    }

    static void addEJBLocalRefs(Configuration ejbContext, RefContext refContext, URI moduleURI, EjbLocalRefType[] ejbLocalRefs, Map ejbLocalRefMap, ClassLoader cl, ComponentContextBuilder builder) throws DeploymentException {
        Map newMap = new HashMap();
        for (Iterator it = ejbLocalRefMap.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            newMap.put(name.trim(), ejbLocalRefMap.get(name));
        }
        ejbLocalRefMap = newMap;
        for (int i = 0; i < ejbLocalRefs.length; i++) {
            EjbLocalRefType ejbLocalRef = ejbLocalRefs[i];

            String ejbRefName = getStringValue(ejbLocalRef.getEjbRefName());
            GerEjbLocalRefType localRef = (GerEjbLocalRefType) ejbLocalRefMap.get(ejbRefName);

            Reference ejbReference = addEJBLocalRef(ejbContext, refContext, moduleURI, ejbLocalRef, localRef, cl);
            builder.bind(ejbRefName, ejbReference);
        }
    }

    static Reference addEJBLocalRef(Configuration ejbContext, RefContext refContext, URI moduleURI, EjbLocalRefType ejbLocalRef, GerEjbLocalRefType localRef, ClassLoader cl) throws DeploymentException {
        String local = getStringValue(ejbLocalRef.getLocal());
        String refName = getStringValue(ejbLocalRef.getEjbRefName());
        try {
            assureEJBLocalObjectInterface(local, cl);
        } catch (DeploymentException e) {
            throw new DeploymentException("Error processing 'local' element for EJB Local Reference '" + refName + "' for module '" + moduleURI + "': " + e.getMessage());
        }

        String localHome = getStringValue(ejbLocalRef.getLocalHome());
        try {
            assureEJBLocalHomeInterface(localHome, cl);
        } catch (DeploymentException e) {
            throw new DeploymentException("Error processing 'local-home' element for EJB Local Reference '" + refName + "' for module '" + moduleURI + "': " + e.getMessage());
        }

        boolean isSession = "Session".equals(getStringValue(ejbLocalRef.getEjbRefType()));

        String ejbLink = null;
        if (localRef != null && localRef.isSetEjbLink()) {
            ejbLink = localRef.getEjbLink();
        } else if (ejbLocalRef.isSetEjbLink()) {
            ejbLink = getStringValue(ejbLocalRef.getEjbLink());
        }

        Artifact targetConfigId = null;
        String optionalModule = moduleURI == null ? null : moduleURI.toString();
        String requiredModule = null;
        AbstractNameQuery containerQuery = null;
        if (localRef != null && localRef.isSetEjbLink()) {
            ejbLink = localRef.getEjbLink();
        } else if (ejbLocalRef.isSetEjbLink()) {
            ejbLink = getStringValue(ejbLocalRef.getEjbLink());
            targetConfigId = ejbContext.getId();
        }
        if (ejbLink != null) {
            String[] bits = ejbLink.split("#");
            if (bits.length == 2) {
                //look only in specified module.
                requiredModule = bits[0];
                ejbLink = bits[1];
            }
        } else if (localRef != null) {
            GerPatternType patternType = localRef.getPattern();
            containerQuery = buildAbstractNameQuery(patternType, null, NameFactory.EJB_MODULE);
        }
        return refContext.getEJBLocalRef(refName, ejbContext, ejbLink, requiredModule, optionalModule, targetConfigId, containerQuery, isSession, localHome, local);
    }

//TODO current implementation does not deal with portComponentRef links.

    static void addServiceRefs(EARContext earContext, Module module, ServiceRefType[] serviceRefs, Map serviceRefMap, ClassLoader cl, ComponentContextBuilder builder) throws DeploymentException {
        Map newMap = new HashMap();
        for (Iterator it = serviceRefMap.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            newMap.put(name.trim(), serviceRefMap.get(name));
        }
        serviceRefMap = newMap;

        RefContext refContext = earContext.getRefContext();

        for (int i = 0; i < serviceRefs.length; i++) {
            ServiceRefType serviceRef = serviceRefs[i];
            String name = getStringValue(serviceRef.getServiceRefName());
            GerServiceRefType serviceRefType = (GerServiceRefType) serviceRefMap.get(name);
//            Map credentialsNameMap = (Map) serviceRefCredentialsNameMap.get(name);
            String serviceInterfaceName = getStringValue(serviceRef.getServiceInterface());
            assureInterface(serviceInterfaceName, "javax.xml.rpc.Service", "[Web]Service", cl);
            Class serviceInterface;
            try {
                serviceInterface = cl.loadClass(serviceInterfaceName);
            } catch (ClassNotFoundException e) {
                throw new DeploymentException("Could not load service interface class: " + serviceInterfaceName, e);
            }
            URI wsdlURI = null;
            if (serviceRef.isSetWsdlFile()) {
                try {
                    wsdlURI = new URI(getStringValue(serviceRef.getWsdlFile().getStringValue()));
                } catch (URISyntaxException e) {
                    throw new DeploymentException("could not construct wsdl uri from " + serviceRef.getWsdlFile().getStringValue(), e);
                }
            }
            URI jaxrpcMappingURI = null;
            if (serviceRef.isSetJaxrpcMappingFile()) {
                try {
                    jaxrpcMappingURI = new URI(getStringValue(serviceRef.getJaxrpcMappingFile()));
                } catch (URISyntaxException e) {
                    throw new DeploymentException("Could not construct jaxrpc mapping uri from " + serviceRef.getJaxrpcMappingFile(), e);
                }
            }
            QName serviceQName = null;
            if (serviceRef.isSetServiceQname()) {
                serviceQName = serviceRef.getServiceQname().getQNameValue();
            }
            Map portComponentRefMap = new HashMap();
            PortComponentRefType[] portComponentRefs = serviceRef.getPortComponentRefArray();
            if (portComponentRefs != null) {
                for (int j = 0; j < portComponentRefs.length; j++) {
                    PortComponentRefType portComponentRef = portComponentRefs[j];
                    String portComponentLink = getStringValue(portComponentRef.getPortComponentLink());
                    String serviceEndpointInterfaceType = getStringValue(portComponentRef.getServiceEndpointInterface());
                    assureInterface(serviceEndpointInterfaceType, "java.rmi.Remote", "ServiceEndpoint", cl);
                    Class serviceEndpointClass;
                    try {
                        serviceEndpointClass = cl.loadClass(serviceEndpointInterfaceType);
                    } catch (ClassNotFoundException e) {
                        throw new DeploymentException("could not load service endpoint class " + serviceEndpointInterfaceType, e);
                    }
                    portComponentRefMap.put(serviceEndpointClass, portComponentLink);
                }
            }
            ServiceRefHandlerType[] handlers = serviceRef.getHandlerArray();
            List handlerInfos = buildHandlerInfoList(handlers, cl);

//we could get a Reference or the actual serializable Service back.
            Object ref = refContext.getServiceReference(serviceInterface, wsdlURI, jaxrpcMappingURI, serviceQName, portComponentRefMap, handlerInfos, serviceRefType, earContext, module, cl);
            builder.bind(name, ref);
        }

    }

    private static List buildHandlerInfoList(ServiceRefHandlerType[] handlers, ClassLoader classLoader) throws DeploymentException {
        List handlerInfos = new ArrayList();
        for (int i = 0; i < handlers.length; i++) {
            ServiceRefHandlerType handler = handlers[i];
            org.apache.geronimo.xbeans.j2ee.String[] portNameArray = handler.getPortNameArray();
            List portNames = new ArrayList();
            for (int j = 0; j < portNameArray.length; j++) {
                portNames.add(portNameArray[j].getStringValue().trim());

            }
//            Set portNames = new HashSet(Arrays.asList(portNameArray));
            String handlerClassName = handler.getHandlerClass().getStringValue().trim();
            Class handlerClass;
            try {
                handlerClass = ClassLoading.loadClass(handlerClassName, classLoader);
            } catch (ClassNotFoundException e) {
                throw new DeploymentException("Could not load handler class", e);
            }
            Map config = new HashMap();
            ParamValueType[] paramValues = handler.getInitParamArray();
            for (int j = 0; j < paramValues.length; j++) {
                ParamValueType paramValue = paramValues[j];
                String paramName = paramValue.getParamName().getStringValue().trim();
                String paramStringValue = paramValue.getParamValue().getStringValue().trim();
                config.put(paramName, paramStringValue);
            }
            XsdQNameType[] soapHeaderQNames = handler.getSoapHeaderArray();
            QName[] headerQNames = new QName[soapHeaderQNames.length];
            for (int j = 0; j < soapHeaderQNames.length; j++) {
                XsdQNameType soapHeaderQName = soapHeaderQNames[j];
                headerQNames[j] = soapHeaderQName.getQNameValue();
            }
            Set soapRoles = new HashSet();
            for (int j = 0; j < handler.getSoapRoleArray().length; j++) {
                String soapRole = handler.getSoapRoleArray(j).getStringValue().trim();
                soapRoles.add(soapRole);
            }
            ServiceReferenceBuilder.HandlerInfoInfo handlerInfoInfo = new ServiceReferenceBuilder.HandlerInfoInfo(new HashSet(portNames), handlerClass, config, headerQNames, soapRoles);
            handlerInfos.add(handlerInfoInfo);
        }
        return handlerInfos;
    }

    public static Class assureEJBObjectInterface(String remote, ClassLoader cl) throws DeploymentException {
        return assureInterface(remote, "javax.ejb.EJBObject", "Remote", cl);
    }

    public static Class assureEJBHomeInterface(String home, ClassLoader cl) throws DeploymentException {
        return assureInterface(home, "javax.ejb.EJBHome", "Home", cl);
    }

    public static Class assureEJBLocalObjectInterface(String local, ClassLoader cl) throws DeploymentException {
        return assureInterface(local, "javax.ejb.EJBLocalObject", "Local", cl);
    }

    public static Class assureEJBLocalHomeInterface(String localHome, ClassLoader cl) throws DeploymentException {
        return assureInterface(localHome, "javax.ejb.EJBLocalHome", "LocalHome", cl);
    }

    public static Class assureInterface(String interfaceName, String superInterfaceName, String interfaceType, ClassLoader cl) throws DeploymentException {
        if (interfaceName == null || interfaceName.equals("")) {
            throw new DeploymentException("interface name cannot be blank");
        }
        Class clazz;
        try {
            clazz = cl.loadClass(interfaceName);
        } catch (ClassNotFoundException e) {
            throw new DeploymentException(interfaceType + " interface class not found: " + interfaceName);
        }
        if (!clazz.isInterface()) {
            throw new DeploymentException(interfaceType + " interface is not an interface: " + interfaceName);
        }
        Class superInterface;
        try {
            superInterface = cl.loadClass(superInterfaceName);
        } catch (ClassNotFoundException e) {
            throw new DeploymentException("Class " + superInterfaceName + " could not be loaded");
        }
        if (!superInterface.isAssignableFrom(clazz)) {
            throw new DeploymentException(interfaceType + " interface does not extend " + superInterfaceName + ": " + interfaceName);
        }
        return clazz;
    }

    private static String getStringValue(org.apache.geronimo.xbeans.j2ee.String string) {
        if (string == null) {
            return null;
        }
        String s = string.getStringValue();
        return s == null ? null : s.trim();
    }

    private static String getStringValue(XsdStringType string) {
        if (string == null) {
            return null;
        }
        String s = string.getStringValue();
        return s == null ? null : s.trim();
    }

    private static String getStringValue(String string) {
        return string == null ? null : string.trim();
    }


    public static void setResourceEnvironment(URI uri, ResourceEnvironmentBuilder builder, ResourceRefType[] resourceRefs, GerResourceRefType[] gerResourceRefs) {
        //TODO configid remove URI parameter?
        Map refMap = mapResourceRefs(gerResourceRefs);
        Set unshareableResources = new HashSet();
        Set applicationManagedSecurityResources = new HashSet();
        for (int i = 0; i < resourceRefs.length; i++) {
            ResourceRefType resourceRefType = resourceRefs[i];

            String type = resourceRefType.getResType().getStringValue().trim();

            if (!URL.class.getName().equals(type)
                    && !"javax.mail.Session".equals(type)
                    && !JAXR_CONNECTION_FACTORY_CLASS.equals(type)) {

                GerResourceRefType gerResourceRef = (GerResourceRefType) refMap.get(resourceRefType.getResRefName().getStringValue());
                AbstractNameQuery containerId = getResourceContainerId(getStringValue(resourceRefType.getResRefName()), NameFactory.JCA_MANAGED_CONNECTION_FACTORY, null, gerResourceRef);

                if ("Unshareable".equals(getStringValue(resourceRefType.getResSharingScope()))) {
                    unshareableResources.add(containerId);
                }
                if ("Application".equals(getStringValue(resourceRefType.getResAuth()))) {
                    applicationManagedSecurityResources.add(containerId);
                }
            }
        }
        builder.setUnshareableResources(unshareableResources);
        builder.setApplicationManagedSecurityResources(applicationManagedSecurityResources);
    }

    public static Map buildComponentContext(EARContext earContext,
                                            Configuration ejbContext,
                                            Module module,
                                            UserTransaction userTransaction,
                                            EnvEntryType[] envEntries,
                                            EjbRefType[] ejbRefs,
                                            GerEjbRefType[] gerEjbRefs,
                                            EjbLocalRefType[] ejbLocalRefs,
                                            GerEjbLocalRefType[] gerEjbLocalRef,
                                            ResourceRefType[] resourceRefs,
                                            GerResourceRefType[] gerResourceRef,
                                            ResourceEnvRefType[] resourceEnvRefs,
                                            GerResourceEnvRefType[] gerResourceEnvRef,
                                            MessageDestinationRefType[] messageDestinationRefs,
                                            ServiceRefType[] serviceRefs,
                                            GerServiceRefType[] gerServiceRefs,
                                            ClassLoader cl) throws DeploymentException {
        ComponentContextBuilder builder = new ComponentContextBuilder();
        RefContext refContext = earContext.getRefContext();
        Configuration earConfiguration = earContext.getConfiguration();

        if (userTransaction != null) {
            builder.addUserTransaction(userTransaction);
        }

        AbstractNameQuery corbaGBean = earContext.getCORBAGBeanObjectName();
        if (corbaGBean != null) {
            builder.addORB(earConfiguration.getId(), corbaGBean);
            builder.addHandleDelegateReference(earConfiguration.getId(), corbaGBean);
        }

        URI moduleURI = module.getModuleURI();

        addEnvEntries(envEntries, builder, cl);

        if (ejbContext == null) {
            ejbContext = earConfiguration;
        }

// ejb-ref
        addEJBRefs(earConfiguration, ejbContext, refContext, moduleURI, ejbRefs, mapEjbRefs(gerEjbRefs), cl, builder);

// ejb-local-ref
        addEJBLocalRefs(ejbContext, refContext, moduleURI, ejbLocalRefs, mapEjbLocalRefs(gerEjbLocalRef), cl, builder);

// resource-ref
        addResourceRefs(earConfiguration, refContext, moduleURI, resourceRefs, mapResourceRefs(gerResourceRef), cl, builder);

// resource-env-ref
        addResourceEnvRefs(earConfiguration, refContext, resourceEnvRefs, mapResourceEnvRefs(gerResourceEnvRef), cl, builder);

        addMessageDestinationRefs(earConfiguration, refContext, messageDestinationRefs, cl, builder);

//        Map serviceRefMap = new HashMap();
//        Map serviceRefCredentialsNameMap = new HashMap();
//        mapServiceRefs(gerServiceRefs, serviceRefMap, serviceRefCredentialsNameMap);
        Map serviceRefMap = mapServiceRefs(gerServiceRefs);
        addServiceRefs(earContext, module, serviceRefs, serviceRefMap, cl, builder);

        return builder.getContext();
    }

    private static Map mapEjbRefs(GerEjbRefType[] refs) {
        Map refMap = new HashMap();
        if (refs != null) {
            for (int i = 0; i < refs.length; i++) {
                GerEjbRefType ref = refs[i];
                refMap.put(ref.getRefName().trim(), ref);
            }
        }
        return refMap;
    }

    private static Map mapEjbLocalRefs(GerEjbLocalRefType[] refs) {
        Map refMap = new HashMap();
        if (refs != null) {
            for (int i = 0; i < refs.length; i++) {
                GerEjbLocalRefType ref = refs[i];
                refMap.put(ref.getRefName().trim(), ref);
            }
        }
        return refMap;
    }

    private static Map mapResourceRefs(GerResourceRefType[] refs) {
        Map refMap = new HashMap();
        if (refs != null) {
            for (int i = 0; i < refs.length; i++) {
                GerResourceRefType ref = refs[i];
                refMap.put(ref.getRefName().trim(), ref);
            }
        }
        return refMap;
    }

    private static Map mapResourceEnvRefs(GerResourceEnvRefType[] refs) {
        Map refMap = new HashMap();
        if (refs != null) {
            for (int i = 0; i < refs.length; i++) {
                GerResourceEnvRefType ref = refs[i];
                refMap.put(ref.getRefName().trim(), ref);
            }
        }
        return refMap;
    }

    private static Map mapServiceRefs(GerServiceRefType[] refs) {
        Map refMap = new HashMap();
        if (refs != null) {
            for (int i = 0; i < refs.length; i++) {
                GerServiceRefType ref = refs[i];
                String serviceRefName = ref.getServiceRefName().trim();
                refMap.put(serviceRefName, ref);
            }
        }
        return refMap;
    }

    //TODO consider including target interface
    public static AbstractNameQuery buildAbstractNameQuery(GerPatternType pattern, String type, String moduleType) {
        String groupId = pattern.isSetGroupId() ? pattern.getGroupId().trim() : null;
        String artifactid = pattern.isSetArtifactId() ? pattern.getArtifactId().trim() : null;
        String version = pattern.isSetVersion() ? pattern.getVersion().trim() : null;
        String module = pattern.isSetModule() ? pattern.getModule().trim() : null;
        String name = pattern.getName().trim();

        Artifact artifact = artifactid != null ? new Artifact(groupId, artifactid, version, "car") : null;
        Map nameMap = new HashMap();
        nameMap.put("name", name);
        if (type != null) {
            nameMap.put("j2eeType", type);
        }
        if (module != null) {
            nameMap.put(moduleType, module);
        }
        return new AbstractNameQuery(artifact, nameMap);
    }

    public static AbstractNameQuery buildAbstractNameQuery(Artifact configId, String module, String name, String type, String moduleType) {
        Map nameMap = new HashMap();
        nameMap.put("name", name);
        if (type != null) {
            nameMap.put("j2eeType", type);
        }
        if (module != null) {
            nameMap.put(moduleType, module);
        }
        return new AbstractNameQuery(configId, nameMap);
    }


}
