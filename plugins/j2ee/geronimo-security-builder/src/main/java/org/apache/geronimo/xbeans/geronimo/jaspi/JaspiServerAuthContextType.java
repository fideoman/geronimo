/*
 * XML Type:  serverAuthContextType
 * Namespace: http://geronimo.apache.org/xml/ns/geronimo-jaspi
 * Java type: org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType
 *
 * Automatically generated - do not modify.
 */
package org.apache.geronimo.xbeans.geronimo.jaspi;


/**
 * An XML serverAuthContextType(@http://geronimo.apache.org/xml/ns/geronimo-jaspi).
 *
 * This is a complex type.
 */
public interface JaspiServerAuthContextType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(JaspiServerAuthContextType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4769874CABA12AE1DF68A448E73C1B12").resolveHandle("serverauthcontexttype1716type");
    
    /**
     * Gets the "messageLayer" element
     */
    java.lang.String getMessageLayer();
    
    /**
     * Gets (as xml) the "messageLayer" element
     */
    org.apache.xmlbeans.XmlString xgetMessageLayer();
    
    /**
     * True if has "messageLayer" element
     */
    boolean isSetMessageLayer();
    
    /**
     * Sets the "messageLayer" element
     */
    void setMessageLayer(java.lang.String messageLayer);
    
    /**
     * Sets (as xml) the "messageLayer" element
     */
    void xsetMessageLayer(org.apache.xmlbeans.XmlString messageLayer);
    
    /**
     * Unsets the "messageLayer" element
     */
    void unsetMessageLayer();
    
    /**
     * Gets the "appContext" element
     */
    java.lang.String getAppContext();
    
    /**
     * Gets (as xml) the "appContext" element
     */
    org.apache.xmlbeans.XmlString xgetAppContext();
    
    /**
     * True if has "appContext" element
     */
    boolean isSetAppContext();
    
    /**
     * Sets the "appContext" element
     */
    void setAppContext(java.lang.String appContext);
    
    /**
     * Sets (as xml) the "appContext" element
     */
    void xsetAppContext(org.apache.xmlbeans.XmlString appContext);
    
    /**
     * Unsets the "appContext" element
     */
    void unsetAppContext();
    
    /**
     * Gets the "authenticationContextID" element
     */
    java.lang.String getAuthenticationContextID();
    
    /**
     * Gets (as xml) the "authenticationContextID" element
     */
    org.apache.xmlbeans.XmlString xgetAuthenticationContextID();
    
    /**
     * True if has "authenticationContextID" element
     */
    boolean isSetAuthenticationContextID();
    
    /**
     * Sets the "authenticationContextID" element
     */
    void setAuthenticationContextID(java.lang.String authenticationContextID);
    
    /**
     * Sets (as xml) the "authenticationContextID" element
     */
    void xsetAuthenticationContextID(org.apache.xmlbeans.XmlString authenticationContextID);
    
    /**
     * Unsets the "authenticationContextID" element
     */
    void unsetAuthenticationContextID();
    
    /**
     * Gets array of all "serverAuthModule" elements
     */
    org.apache.geronimo.xbeans.geronimo.jaspi.JaspiAuthModuleType[] getServerAuthModuleArray();
    
    /**
     * Gets ith "serverAuthModule" element
     */
    org.apache.geronimo.xbeans.geronimo.jaspi.JaspiAuthModuleType getServerAuthModuleArray(int i);
    
    /**
     * Returns number of "serverAuthModule" element
     */
    int sizeOfServerAuthModuleArray();
    
    /**
     * Sets array of all "serverAuthModule" element
     */
    void setServerAuthModuleArray(org.apache.geronimo.xbeans.geronimo.jaspi.JaspiAuthModuleType[] serverAuthModuleArray);
    
    /**
     * Sets ith "serverAuthModule" element
     */
    void setServerAuthModuleArray(int i, org.apache.geronimo.xbeans.geronimo.jaspi.JaspiAuthModuleType serverAuthModule);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "serverAuthModule" element
     */
    org.apache.geronimo.xbeans.geronimo.jaspi.JaspiAuthModuleType insertNewServerAuthModule(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "serverAuthModule" element
     */
    org.apache.geronimo.xbeans.geronimo.jaspi.JaspiAuthModuleType addNewServerAuthModule();
    
    /**
     * Removes the ith "serverAuthModule" element
     */
    void removeServerAuthModule(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType newInstance() {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.apache.geronimo.xbeans.geronimo.jaspi.JaspiServerAuthContextType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
