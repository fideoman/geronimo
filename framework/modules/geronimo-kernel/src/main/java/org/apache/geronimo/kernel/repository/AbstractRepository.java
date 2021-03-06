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
package org.apache.geronimo.kernel.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.geronimo.kernel.util.InputUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version $Rev$ $Date$
 */
public abstract class AbstractRepository implements WriteableRepository {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final static ArtifactTypeHandler DEFAULT_TYPE_HANDLER = new CopyArtifactTypeHandler();
    protected final File rootFile;
    private final Map<String, ArtifactTypeHandler> typeHandlers = new HashMap<String, ArtifactTypeHandler>();

    public AbstractRepository(File rootFile) {
        if (rootFile == null) throw new NullPointerException("root is null");

        if (!rootFile.exists() || !rootFile.isDirectory() || !rootFile.canRead()) {
            throw new IllegalStateException("Maven2Repository must have a root that's a valid readable directory (not " + rootFile.getAbsolutePath() + ")");
        }

        this.rootFile = rootFile;
        log.debug("Repository root is {}", rootFile.getAbsolutePath());

        typeHandlers.put("car", new UnpackArtifactTypeHandler());
    }

    public boolean contains(Artifact artifact) {
        // Note: getLocation(artifact) does an artifact.isResolved() check - no need to do it here.
        File location = getLocation(artifact);
        return location.canRead() && (location.isFile() || new File(location, "META-INF").isDirectory());
    }

    public void setTypeHandler(String type, ArtifactTypeHandler handler) {
        typeHandlers.put(type, handler);
    }

    public void copyToRepository(File source, Artifact destination, FileWriteMonitor monitor) throws IOException {
        File location = verifyDestination(destination);
        
        if (!source.exists() || !source.canRead()) {
            throw new IllegalArgumentException("Cannot read source file at " + source.getAbsolutePath());
        }
        
        ArtifactTypeHandler typeHandler = typeHandlers.get(destination.getType());
        if (typeHandler == null) {
            typeHandler = DEFAULT_TYPE_HANDLER;
        }
        typeHandler.install(source, destination, monitor, location);

        if (destination.getType().equalsIgnoreCase("car")) {
            log.debug("Installed module configuration; id={}; location={}", destination, location);
        }
    }

    public void copyToRepository(InputStream source, int size, Artifact destination, FileWriteMonitor monitor) throws IOException {
        File location = verifyDestination(destination);
        
        ArtifactTypeHandler typeHandler = typeHandlers.get(destination.getType());
        if (typeHandler == null) {
            typeHandler = DEFAULT_TYPE_HANDLER;
        }
        typeHandler.install(source, size, destination, monitor, location);

        if (destination.getType().equalsIgnoreCase("car")) {
            log.debug("Installed module configuration; id={}; location={}", destination, location);
        }
    }
    
    private File verifyDestination(Artifact destination) {
        // is this a writable repository
        if (!rootFile.canWrite()) {
            throw new IllegalStateException("This repository is not writable");
        }
                
        if (!destination.isResolved()) {
            throw new IllegalArgumentException("Artifact "+destination+" is not fully resolved");
        }
        
        // ensure there are no illegal chars in destination elements
        InputUtils.validateSafeInput(Arrays.asList(destination.getGroupId(), destination.getArtifactId(), destination.getVersion().toString(), destination.getType()));

        // where are we going to install the file
        File location = getLocation(destination);

        // assure that there isn't already a file installed at the specified location
        if (location.exists()) {
            throw new IllegalArgumentException("Destination " + location.getAbsolutePath() + " already exists!");
        }
        return location;
    }

    public File getRootFile() {
        return rootFile;
    }
}
