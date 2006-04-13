/**
 *
 * Copyright 2005 The Apache Software Foundation
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
package org.apache.geronimo.console;

import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.enterprise.deploy.spi.status.ProgressObject;
import java.io.IOException;

/**
 * ReplaceMe
 *
 * @version $Rev$ $Date$
 */
public abstract class MultiPageAbstractHandler {
    protected final static String BEFORE_ACTION="-before";
    protected final static String AFTER_ACTION="-after";
    protected PortletRequestDispatcher view;
    private final String mode;
    private final String viewName;

    protected MultiPageAbstractHandler(String mode, String viewName) {
        this.mode = mode;
        this.viewName = viewName;
    }

    public String getMode() {
        return mode;
    }

    public void init(PortletConfig portletConfig) throws PortletException {
        if(viewName != null) {
            view = portletConfig.getPortletContext().getRequestDispatcher(viewName);
        }
    }

    public void destroy() {
        view = null;
    }

    public PortletRequestDispatcher getView() {
        return view;
    }

    protected static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    /**
     * Returns the mode for the next screen to render (usually this one)
     */
    public abstract String actionBeforeView(ActionRequest request, ActionResponse response, MultiPageModel model) throws PortletException, IOException;

    public abstract void renderView(RenderRequest request, RenderResponse response, MultiPageModel model) throws PortletException, IOException;

    /**
     * Returns the mode for the next screen to render (usually the one after this in the sequence)
     */
    public abstract String actionAfterView(ActionRequest request, ActionResponse response, MultiPageModel model) throws PortletException, IOException;


    protected static void waitForProgress(ProgressObject po) {
        while(po.getDeploymentStatus().isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
