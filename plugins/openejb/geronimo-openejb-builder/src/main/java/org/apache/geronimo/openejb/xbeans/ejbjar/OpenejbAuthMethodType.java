/*
 * XML Type:  auth-methodType
 * Namespace: http://geronimo.apache.org/xml/ns/j2ee/ejb/openejb-2.0
 * Java type: org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType
 *
 * Automatically generated - do not modify.
 */
package org.apache.geronimo.openejb.xbeans.ejbjar;


/**
 * An XML auth-methodType(@http://geronimo.apache.org/xml/ns/j2ee/ejb/openejb-2.0).
 *
 * This is an atomic type that is a restriction of org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType.
 */
public interface OpenejbAuthMethodType extends org.apache.xmlbeans.XmlString
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OpenejbAuthMethodType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sCB8E5D801CA82AB8B1999B71F1BE50C7").resolveHandle("authmethodtype60b5type");
    
    org.apache.xmlbeans.StringEnumAbstractBase enumValue();
    void set(org.apache.xmlbeans.StringEnumAbstractBase e);
    
    static final Enum BASIC = Enum.forString("BASIC");
    static final Enum DIGEST = Enum.forString("DIGEST");
    static final Enum CLIENT_CERT = Enum.forString("CLIENT-CERT");
    static final Enum NONE = Enum.forString("NONE");
    
    static final int INT_BASIC = Enum.INT_BASIC;
    static final int INT_DIGEST = Enum.INT_DIGEST;
    static final int INT_CLIENT_CERT = Enum.INT_CLIENT_CERT;
    static final int INT_NONE = Enum.INT_NONE;
    
    /**
     * Enumeration value class for org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType.
     * These enum values can be used as follows:
     * <pre>
     * enum.toString(); // returns the string value of the enum
     * enum.intValue(); // returns an int value, useful for switches
     * // e.g., case Enum.INT_BASIC
     * Enum.forString(s); // returns the enum value for a string
     * Enum.forInt(i); // returns the enum value for an int
     * </pre>
     * Enumeration objects are immutable singleton objects that
     * can be compared using == object equality. They have no
     * public constructor. See the constants defined within this
     * class for all the valid values.
     */
    static final class Enum extends org.apache.xmlbeans.StringEnumAbstractBase
    {
        /**
         * Returns the enum value for a string, or null if none.
         */
        public static Enum forString(java.lang.String s)
            { return (Enum)table.forString(s); }
        /**
         * Returns the enum value corresponding to an int, or null if none.
         */
        public static Enum forInt(int i)
            { return (Enum)table.forInt(i); }
        
        private Enum(java.lang.String s, int i)
            { super(s, i); }
        
        static final int INT_BASIC = 1;
        static final int INT_DIGEST = 2;
        static final int INT_CLIENT_CERT = 3;
        static final int INT_NONE = 4;
        
        public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
            new org.apache.xmlbeans.StringEnumAbstractBase.Table
        (
            new Enum[]
            {
                new Enum("BASIC", INT_BASIC),
                new Enum("DIGEST", INT_DIGEST),
                new Enum("CLIENT-CERT", INT_CLIENT_CERT),
                new Enum("NONE", INT_NONE),
            }
        );
        private static final long serialVersionUID = 1L;
        private java.lang.Object readResolve() { return forInt(intValue()); } 
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType newValue(java.lang.Object obj) {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) type.newValue( obj ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType newInstance() {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.apache.geronimo.openejb.xbeans.ejbjar.OpenejbAuthMethodType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
