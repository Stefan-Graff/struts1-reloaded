/*
 * $Id$
 *
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
package org.apache.struts.chain.commands.servlet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.commands.AbstractPopulateActionForm;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.PopulateEvent;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.RequestUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>Populate the form bean (if any) for this request.  Sets the multipart
 * class from the action config in the request attributes.</p>
 *
 * @version $Rev$ $Date: 2005-11-12 13:01:44 -0500 (Sat, 12 Nov 2005)
 *          $
 */
public class PopulateActionForm extends AbstractPopulateActionForm {

    // ------------------------------------------------------- Protected Methods

    protected void populate(ActionContext context, ActionConfig actionConfig,
        ActionForm actionForm, MultipartRequestHandler multipartHandler)
        throws Exception {
        ServletActionContext saContext = (ServletActionContext) context;
        HttpServletRequest request = saContext.getRequest();

        RequestUtils.populate(actionForm, actionConfig.getPrefix(),
            actionConfig.getSuffix(), request, multipartHandler);
    }

    protected void reset(ActionContext context, ActionConfig actionConfig,
        ActionForm actionForm) {
        ServletActionContext saContext = (ServletActionContext) context;
        HttpServletRequest request = saContext.getRequest();

        actionForm.reset((ActionMapping) actionConfig, request);
    }

    // ---------------------------------------------------------- Helper Methods

    /**
     * Determines whether an action form should be populated.
     *
     * @param context the ActionContext we are processing
     * @param actionConfig action config for current request
     *
     * @return true if action form should be populated
     *
     * @since Struts 1.4
     */
    protected boolean isPopulate(ActionContext context, ActionConfig actionConfig) {
        String[] events = actionConfig.getPopulateNames();
        return getResetOrPopulate(context, events);
    }

    /**
     * Determines whether an action form should be reset.
     *
     * @param context the ActionContext we are processing
     * @param actionConfig action config for current request
     *
     * @return true if action form should be reset
     *
     * @since Struts 1.4
     */
    protected boolean isReset(ActionContext context, ActionConfig actionConfig) {
        String[] events = actionConfig.getResetNames();
        return getResetOrPopulate(context, events);
    }

    /**
     * Compares current request state (direct or forwarded) with configuration
     * from action mapping.
     *
     * @param request current HTTP request
     * @param events values of either "reset" or "populate" attributes of
     *        an action mapping
     * @return true if action mapping is configured to reset (or populate)
     *         corresponding action form; false if if action mapping is
     *         configured not to reset (or populate) the action form.
     *
     * @since Struts 1.4
     */
    private boolean getResetOrPopulate(ActionContext context, String[] events) {
        // Reset configuration is not defined, but this should not happen
        // because default value is provided
        if ((events == null) || (events.length == 0)) {
            return true;
        }

        // First check against events that must be alone
        if (events.length == 1) {
            if (events[0].equals(PopulateEvent.ALL)) {
                return true;
            }
            if (events[0].equals(PopulateEvent.NONE)) {
                return false;
            }
        }

        // Then check against the list of combinable events
        HttpServletRequest request = ((ServletActionContext) context).getRequest();
        for(int i = 0; i < events.length; i++) {
            String attribute = events[i];
            if (attribute.equals(PopulateEvent.FORWARD)) {
                if (RequestUtils.isRequestForwarded(request)) {
                    return true;
                }
            } else if (attribute.equals(PopulateEvent.INCLUDE)) {
                if (RequestUtils.isRequestIncluded(request)) {
                    return true;
                }
            } else if (attribute.equals(PopulateEvent.CHAIN)) {
                if (RequestUtils.isRequestChained(request)) {
                    return true;
                }
            } else if (attribute.equals(PopulateEvent.CANCEL)) {
                if (RequestUtils.isRequestCancelled(request)) {
                    return true;
                }
            } else if (attribute.equals(PopulateEvent.REQUEST)) {
                if (RequestUtils.isRequestForwarded(request)) {
                    continue;
                }
                if (RequestUtils.isRequestIncluded(request)) {
                    continue;
                }
                return true;
            }
        }

        // Do not reset/populate if a user explicity set anything
        // else besides "request" or "populate".
        return false;
    }
}
