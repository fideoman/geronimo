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
package org.openejb.test.simple.cmp;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

/**
 * 
 * 
 * @version $Revision: 1.1 $ $Date: 2004/05/19 20:54:00 $
 */
public interface SimpleCMPEntity extends EJBObject {
    Integer getId() throws RemoteException;

    String getFirstName() throws RemoteException;

    void setFirstName(String firstName) throws RemoteException;

    String getLastName() throws RemoteException;

    void setLastName(String lastName) throws RemoteException;
}
