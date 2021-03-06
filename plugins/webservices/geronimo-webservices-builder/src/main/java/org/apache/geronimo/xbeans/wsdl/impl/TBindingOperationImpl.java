/*
 * XML Type:  tBindingOperation
 * Namespace: http://schemas.xmlsoap.org/wsdl/
 * Java type: org.apache.geronimo.xbeans.wsdl.TBindingOperation
 *
 * Automatically generated - do not modify.
 */
package org.apache.geronimo.xbeans.wsdl.impl;
/**
 * An XML tBindingOperation(@http://schemas.xmlsoap.org/wsdl/).
 *
 * This is a complex type.
 */
public class TBindingOperationImpl extends org.apache.geronimo.xbeans.wsdl.impl.TExtensibleDocumentedImpl implements org.apache.geronimo.xbeans.wsdl.TBindingOperation
{
    private static final long serialVersionUID = 1L;
    
    public TBindingOperationImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INPUT$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "input");
    private static final javax.xml.namespace.QName OUTPUT$2 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "output");
    private static final javax.xml.namespace.QName FAULT$4 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "fault");
    private static final javax.xml.namespace.QName NAME$6 = 
        new javax.xml.namespace.QName("", "name");
    
    
    /**
     * Gets the "input" element
     */
    public org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage getInput()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage)get_store().find_element_user(INPUT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "input" element
     */
    public boolean isSetInput()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INPUT$0) != 0;
        }
    }
    
    /**
     * Sets the "input" element
     */
    public void setInput(org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage input)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage)get_store().find_element_user(INPUT$0, 0);
            if (target == null)
            {
                target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage)get_store().add_element_user(INPUT$0);
            }
            target.set(input);
        }
    }
    
    /**
     * Appends and returns a new empty "input" element
     */
    public org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage addNewInput()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage)get_store().add_element_user(INPUT$0);
            return target;
        }
    }
    
    /**
     * Unsets the "input" element
     */
    public void unsetInput()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INPUT$0, 0);
        }
    }
    
    /**
     * Gets the "output" element
     */
    public org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage getOutput()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage)get_store().find_element_user(OUTPUT$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "output" element
     */
    public boolean isSetOutput()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OUTPUT$2) != 0;
        }
    }
    
    /**
     * Sets the "output" element
     */
    public void setOutput(org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage output)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage)get_store().find_element_user(OUTPUT$2, 0);
            if (target == null)
            {
                target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage)get_store().add_element_user(OUTPUT$2);
            }
            target.set(output);
        }
    }
    
    /**
     * Appends and returns a new empty "output" element
     */
    public org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage addNewOutput()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationMessage)get_store().add_element_user(OUTPUT$2);
            return target;
        }
    }
    
    /**
     * Unsets the "output" element
     */
    public void unsetOutput()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OUTPUT$2, 0);
        }
    }
    
    /**
     * Gets array of all "fault" elements
     */
    public org.apache.geronimo.xbeans.wsdl.TBindingOperationFault[] getFaultArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(FAULT$4, targetList);
            org.apache.geronimo.xbeans.wsdl.TBindingOperationFault[] result = new org.apache.geronimo.xbeans.wsdl.TBindingOperationFault[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "fault" element
     */
    public org.apache.geronimo.xbeans.wsdl.TBindingOperationFault getFaultArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationFault target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationFault)get_store().find_element_user(FAULT$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "fault" element
     */
    public int sizeOfFaultArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FAULT$4);
        }
    }
    
    /**
     * Sets array of all "fault" element
     */
    public void setFaultArray(org.apache.geronimo.xbeans.wsdl.TBindingOperationFault[] faultArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(faultArray, FAULT$4);
        }
    }
    
    /**
     * Sets ith "fault" element
     */
    public void setFaultArray(int i, org.apache.geronimo.xbeans.wsdl.TBindingOperationFault fault)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationFault target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationFault)get_store().find_element_user(FAULT$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(fault);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "fault" element
     */
    public org.apache.geronimo.xbeans.wsdl.TBindingOperationFault insertNewFault(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationFault target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationFault)get_store().insert_element_user(FAULT$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "fault" element
     */
    public org.apache.geronimo.xbeans.wsdl.TBindingOperationFault addNewFault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.geronimo.xbeans.wsdl.TBindingOperationFault target = null;
            target = (org.apache.geronimo.xbeans.wsdl.TBindingOperationFault)get_store().add_element_user(FAULT$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "fault" element
     */
    public void removeFault(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FAULT$4, i);
        }
    }
    
    /**
     * Gets the "name" attribute
     */
    public java.lang.String getName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NAME$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "name" attribute
     */
    public org.apache.xmlbeans.XmlNCName xgetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNCName target = null;
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(NAME$6);
            return target;
        }
    }
    
    /**
     * Sets the "name" attribute
     */
    public void setName(java.lang.String name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NAME$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NAME$6);
            }
            target.setStringValue(name);
        }
    }
    
    /**
     * Sets (as xml) the "name" attribute
     */
    public void xsetName(org.apache.xmlbeans.XmlNCName name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlNCName target = null;
            target = (org.apache.xmlbeans.XmlNCName)get_store().find_attribute_user(NAME$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlNCName)get_store().add_attribute_user(NAME$6);
            }
            target.set(name);
        }
    }
}
