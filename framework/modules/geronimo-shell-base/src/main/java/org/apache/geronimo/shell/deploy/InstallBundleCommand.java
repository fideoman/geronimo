/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.geronimo.shell.deploy;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.geronimo.cli.deployer.InstallBundleCommandArgs;
import org.apache.geronimo.deployment.cli.CommandInstallBundle;
import org.apache.geronimo.deployment.cli.ServerConnection;

/**
 * @version $Rev$ $Date$
 */
@Command(scope = "deploy", name = "install-bundle", description = "Install an OSGi bundle")
public class InstallBundleCommand extends ConnectCommand {

    @Option(name = "--groupId", description = "Group Id")
    String groupId;

    @Option(name = "--startLevel", description = "Start level of the bundle")
    int startLevel;

    @Option(name = "--start", description = "Whether to start the bundle")
    boolean start; 

    @Argument(required = true, description = "Bundle file")
    String bundleFile;

    @Override
    protected Object doExecute() throws Exception {
        ServerConnection connection = connect();

        CommandInstallBundle command = new CommandInstallBundle();

        InstallBundleCommandArgs args = new InstallBundleCommandArgsImpl(groupId, startLevel, start, new String[]{bundleFile});

        command.execute(this, connection, args);
        return null;
    }

}

class InstallBundleCommandArgsImpl implements InstallBundleCommandArgs {
    String[] args;

    String groupId;

    int startLevel;
  
    boolean start;

    public InstallBundleCommandArgsImpl(String groupId, int startLevel, boolean start, String[] args) {
        this.args = args;
        this.groupId = groupId;
        this.start = start;
        this.startLevel = startLevel;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }
    
    @Override
    public int getStartLevel() {
        return startLevel;
    }

    @Override
    public boolean isStart() {
        return start;
    }

    @Override
    public String[] getArgs() {
        return args;
    }

     
}

