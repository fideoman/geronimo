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
package org.apache.geronimo.testsuite.javaee6.jndiinfer.test;

import org.apache.geronimo.testsupport.TestSupport;
import org.testng.annotations.Test;
import org.apache.geronimo.testsupport.HttpUtils;

import java.net.URL;
import java.io.IOException;
/**
 * Unit test for simple App.
 */
public class JndiInferTest extends TestSupport{
    private String baseURL = "http://localhost:8080/";

    @Test
    public void testServlet() throws Exception {
        checkReply("/hello");
    }


    private void checkReply(String address)
        throws Exception {
        String warName = System.getProperty("appContext");
        assertNotNull(warName);
        URL url = new URL(baseURL + warName + address);
       
        String reply = doGET(url, 6, 10 * 1000);
        assertTrue("JNDI Type Infer", 
                   reply.contains("Hello World from env-entry!"));
        
        
        
    }
  
    private String doGET(URL url, int repeat, long delay) {
        for (int i = 0; i < repeat; i++) {
            try {
                return HttpUtils.doGET(url); 
            } catch (IOException e) {
                // ignore
                try {
                    Thread.sleep(delay);
                } catch (Exception ee) {
                    break;
                }
            }
        }
        fail("Did not get servlet response in time");
        return "";
    }   
}
