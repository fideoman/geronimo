/*
 * XML Type:  javabeanType
 * Namespace: http://geronimo.apache.org/xml/ns/deployment/javabean-1.0
 * Java type: org.apache.geronimo.deployment.javabean.xbeans.JavabeanType
 *
 * Automatically generated - do not modify.
 */
package org.apache.geronimo.deployment.javabean.xbeans;


/**
 * An XML javabeanType(@http://geronimo.apache.org/xml/ns/deployment/javabean-1.0).
 *
 * This is a complex type.
 */
public interface JavabeanType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(JavabeanType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1D00E640B90252F7DCFA98454B9E9CE2").resolveHandle("javabeantype768ctype");
    
    /**
     * Gets array of all "property" elements
     */
    org.apache.geronimo.deployment.javabean.xbeans.PropertyType[] getPropertyArray();
    
    /**
     * Gets ith "property" element
     */
    org.apache.geronimo.deployment.javabean.xbeans.PropertyType getPropertyArray(int i);
    
    /**
     * Returns number of "property" element
     */
    int sizeOfPropertyArray();
    
    /**
     * Sets array of all "property" element
     */
    void setPropertyArray(org.apache.geronimo.deployment.javabean.xbeans.PropertyType[] propertyArray);
    
    /**
     * Sets ith "property" element
     */
    void setPropertyArray(int i, org.apache.geronimo.deployment.javabean.xbeans.PropertyType property);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "property" element
     */
    org.apache.geronimo.deployment.javabean.xbeans.PropertyType insertNewProperty(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "property" element
     */
    org.apache.geronimo.deployment.javabean.xbeans.PropertyType addNewProperty();
    
    /**
     * Removes the ith "property" element
     */
    void removeProperty(int i);
    
    /**
     * Gets array of all "bean-property" elements
     */
    org.apache.geronimo.deployment.javabean.xbeans.BeanPropertyType[] getBeanPropertyArray();
    
    /**
     * Gets ith "bean-property" element
     */
    org.apache.geronimo.deployment.javabean.xbeans.BeanPropertyType getBeanPropertyArray(int i);
    
    /**
     * Returns number of "bean-property" element
     */
    int sizeOfBeanPropertyArray();
    
    /**
     * Sets array of all "bean-property" element
     */
    void setBeanPropertyArray(org.apache.geronimo.deployment.javabean.xbeans.BeanPropertyType[] beanPropertyArray);
    
    /**
     * Sets ith "bean-property" element
     */
    void setBeanPropertyArray(int i, org.apache.geronimo.deployment.javabean.xbeans.BeanPropertyType beanProperty);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "bean-property" element
     */
    org.apache.geronimo.deployment.javabean.xbeans.BeanPropertyType insertNewBeanProperty(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "bean-property" element
     */
    org.apache.geronimo.deployment.javabean.xbeans.BeanPropertyType addNewBeanProperty();
    
    /**
     * Removes the ith "bean-property" element
     */
    void removeBeanProperty(int i);
    
    /**
     * Gets the "class" attribute
     */
    java.lang.String getClass1();
    
    /**
     * Gets (as xml) the "class" attribute
     */
    org.apache.xmlbeans.XmlString xgetClass1();
    
    /**
     * True if has "class" attribute
     */
    boolean isSetClass1();
    
    /**
     * Sets the "class" attribute
     */
    void setClass1(java.lang.String class1);
    
    /**
     * Sets (as xml) the "class" attribute
     */
    void xsetClass1(org.apache.xmlbeans.XmlString class1);
    
    /**
     * Unsets the "class" attribute
     */
    void unsetClass1();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType newInstance() {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.geronimo.deployment.javabean.xbeans.JavabeanType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.apache.geronimo.deployment.javabean.xbeans.JavabeanType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
