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
package org.apache.struts.chain.commands;

import java.util.Map;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>Populate the form bean (if any) for this request.</p>
 *
 * @version $Rev$ $Date: 2005-11-12 13:01:44 -0500 (Sat, 12 Nov 2005)
 *          $
 */
public abstract class AbstractPopulateActionForm extends ActionCommandBase {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(AbstractPopulateActionForm.class);

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>Populate the form bean (if any) for this request.</p>
     *
     * @param actionCtx The <code>Context</code> for the current request
     * @return <code>false</code> so that processing continues
     * @throws Exception On an unexpected error
     */
    @Override
    protected boolean execute_(ActionContext actionCtx)
        throws Exception {

        ActionConfig actionConfig = actionCtx.getActionConfig();
        ActionForm actionForm = actionCtx.getActionForm();

        ServletActionContext saContext = (ServletActionContext) actionCtx;
        HttpServletRequest request = saContext.getRequest();

        final boolean isReset = isReset(actionCtx, actionConfig);

        // Set the multipart class
        if (isReset && actionConfig.getMultipartClass() != null) {
            saContext.getRequestScope().put(Globals.MULTIPART_KEY,
                actionConfig.getMultipartClass());
        }

        final MultipartRequestHandler multipartHandler = RequestUtils.populateMultipartPost(request);

        // First determine if the request was cancelled
        handleCancel(actionCtx, actionConfig, actionForm);

        // Is there a form bean for this request?
        if (actionForm == null) {
            if (multipartHandler != null) {
                multipartHandler.rollback();
            }
            return CONTINUE_PROCESSING;
        }

        // Reset the form bean only if configured so
        if (isReset) {
            log.debug("Reseting form bean '{}'",  actionConfig.getName());
            reset(actionCtx, actionConfig, actionForm);
        }

        // Populate the form bean only if configured so
        if (isPopulate(actionCtx, actionConfig)) {
            log.debug("Populating form bean '{}'", actionConfig.getName());
            populate(actionCtx, actionConfig, actionForm, multipartHandler);
        } else {
            if (multipartHandler != null) {
                multipartHandler.rollback();
            }
        }

        return CONTINUE_PROCESSING;
    }

    // ------------------------------------------------------- Protected Methods

    /**
     * Determines whether an action form should be reset
     *
     * @param context      the ActionContext we are processing
     * @param actionConfig action config for current request
     *
     * @return true if action form should be reset
     *
     * @since Struts 1.4
     */
    protected abstract boolean isReset(ActionContext context,
            ActionConfig actionConfig);

    /**
     * Determines whether an action form should be populated.
     *
     * @param context the ActionContext we are processing
     * @param actionConfig action config for current request
     * @return true if action form should be populated
     * @since Struts 1.4
     */
    protected abstract boolean isPopulate(ActionContext context,
            ActionConfig actionConfig);

    /**
     * <p>Call the <code>reset()</code> method on the specified form
     * bean.</p>
     *
     * @param context      The context for this request
     * @param actionConfig The actionConfig for this request
     * @param actionForm   The form bean for this request
     */
    protected abstract void reset(ActionContext context,
        ActionConfig actionConfig, ActionForm actionForm);

    /**
     * <p> Populate the given <code>ActionForm</code> with request parameter
     * values, taking into account any prefix/suffix values configured on the
     * given <code>ActionConfig</code>. </p>
     *
     * @param context      The ActionContext we are processing
     * @param actionConfig The ActionConfig we are processing
     * @param actionForm   The ActionForm we are processing
     * @throws Exception On an unexpected error
     */
    protected abstract void populate(ActionContext context,
        ActionConfig actionConfig, ActionForm actionForm,
        MultipartRequestHandler multipartHandler)
        throws Exception;

    // original implementation casting context to WebContext is not safe
    // when the input value is an ActionContext.

    /**
     * <p>For a given request parameter name, trim off any prefix and/or
     * suffix which are defined in <code>actionConfig</code> and return what
     * remains. If either prefix or suffix is defined, then return null for
     * <code>name</code> values which do not begin or end accordingly.</p>
     *
     * @param actionConfig The ActionConfig we are processing
     * @param name         The request parameter name to proceess
     * @return The request parameter name trimmed of any suffix or prefix
     */
    protected String trimParameterName(ActionConfig actionConfig, String name) {
        String stripped = name;
        String prefix = actionConfig.getPrefix();
        String suffix = actionConfig.getSuffix();

        if (prefix != null) {
            if (!stripped.startsWith(prefix)) {
                return null;
            }

            stripped = stripped.substring(prefix.length());
        }

        if (suffix != null) {
            if (!stripped.endsWith(suffix)) {
                return null;
            }

            stripped =
                stripped.substring(0, stripped.length() - suffix.length());
        }

        return stripped;
    }

    /**
     * <p>Take into account whether the request includes any defined value for
     * the global "cancel" parameter.</p> <p> An issue was raised (but I don't
     * think a Bugzilla ticket created) about the security implications of
     * using a well-known cancel property which skips form validation, as you
     * may not write your actions to deal with the cancellation case. </p>
     *
     * @param context      The ActionContext we are processing
     * @param actionConfig The ActionConfig we are processing
     * @param actionForm   The ActionForm we are processing
     * @throws Exception On an unexpected error
     * @see Globals#CANCEL_PROPERTY
     * @see Globals#CANCEL_PROPERTY_X
     */
    protected void handleCancel(ActionContext context,
        ActionConfig actionConfig, ActionForm actionForm)
        throws Exception {
        Map<String, String[]> paramValues = context.getParameterMap();

        // Set the cancellation attribute if appropriate
        if ((paramValues.get(Globals.CANCEL_PROPERTY) != null)
            || (paramValues.get(Globals.CANCEL_PROPERTY_X) != null)) {
            context.setCancelled(Boolean.TRUE);
        } else {
            context.setCancelled(Boolean.FALSE);
        }
    }
}
