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
package org.apache.geronimo.resource.mail;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;


/**
 * @version $Rev$ $Date$
 */
public class TestTransport extends Transport {

    public TestTransport(Session session, URLName name) {
        super(session, name);
    }

    public void sendMessage(Message message, Address[] addresses) throws MessagingException {
    }

    public boolean isEHLO() {
        return "true".equals(session.getProperties().getProperty("mail.smtp.ehlo"));
    }

    public boolean isQuitWait() {
        return "true".equals(session.getProperties().getProperty("mail.smtp.quitwait"));
    }
}
