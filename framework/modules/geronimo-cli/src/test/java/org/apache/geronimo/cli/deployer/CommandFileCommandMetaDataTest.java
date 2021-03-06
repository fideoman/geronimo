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
package org.apache.geronimo.cli.deployer;

import org.apache.geronimo.cli.CLParserException;

import junit.framework.TestCase;

/**
 * @version $Rev:385659 $ $Date: 2007-03-07 14:40:07 +1100 (Wed, 07 Mar 2007) $
 */
public class CommandFileCommandMetaDataTest extends TestCase {

    public void testWithCommandFile() throws Exception {
        String file = "file";
        CommandArgs args = CommandFileCommandMetaData.META_DATA.parse(new String[] {file});
        assertEquals(file, args.getArgs()[0]);
    }

    public void testWithoutCommandFileFails() throws Exception {
        try {
            CommandFileCommandMetaData.META_DATA.parse(new String[0]);
            fail();
        } catch (CLParserException e) {
        }
    }

    public void testWithMoreThanOneFileFails() throws Exception {
        try {
            CommandFileCommandMetaData.META_DATA.parse(new String[] {"file1", "file2"});
            fail();
        } catch (CLParserException e) {
        }
    }

}
