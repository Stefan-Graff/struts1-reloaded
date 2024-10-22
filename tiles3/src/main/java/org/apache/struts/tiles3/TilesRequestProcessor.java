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
package org.apache.struts.tiles3;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>RequestProcessor</strong> contains the processing logic that the
 * Struts controller servlet performs as it receives each servlet request from
 * the container.
 * <p>This processor subclasses the Struts RequestProcessor in order to
 * intercept calls to forward or include. When such calls are done, the Tiles
 * processor checks if the specified URI is a definition name. If {@code true},
 * the definition is retrieved and included. If {@code false}, the original URI
 * is included or a forward is performed.</p>
 * <p>Actually, catching is done by overloading the following methods:
 * <ul>
 * <li>{@link #processForwardConfig(HttpServletRequest,HttpServletResponse,ForwardConfig)}</li>
 * <li>{@link #internalModuleRelativeForward(String, HttpServletRequest , HttpServletResponse)}</li>
 * <li>{@link #internalModuleRelativeInclude(String, HttpServletRequest , HttpServletResponse)}</li>
 * </ul>
 * </p>
 *
 * @since Struts 1.1
 */
public class TilesRequestProcessor extends RequestProcessor {
    private static final long serialVersionUID = 6212184689866961850L;

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(TilesRequestProcessor.class);

    /**
     * Initialize this request processor instance.
     *
     * @param servlet      The ActionServlet we are associated with.
     * @param moduleConfig The ModuleConfig we are associated with.
     *
     * @throws ServletException If an error occurs during initialization.
     */
    public void init(ActionServlet servlet, ModuleConfig moduleConfig)
        throws ServletException {

        super.init(servlet, moduleConfig);
    }

    /**
     * Process a Tile definition name. This method tries to process the
     * parameter {@code definitionName} as a definition name. It returns
     * {@code true} if a definition has been processed, or {@code false}
     * otherwise.
     *
     * @param definitionName Definition name to insert.
     * @param req            Current page request.
     * @param res            Current page response.
     *
     * @return {@code true} if the method has processed uri as a definition
     *         name, [@code false} otherwise.
     *
     * @throws IOException      If something goes wrong during writing the
     *                          definition.
     * @throws ServletException If something goes wrong during the evaluation
     *                          of the definition
     */
    protected boolean processTilesDefinition(
        String definitionName,
        HttpServletRequest req,
        HttpServletResponse res)
        throws IOException, ServletException {

        ApplicationContext applicationContext = ServletUtil
                .getApplicationContext(getServletContext());
        Request request = new ServletRequest(applicationContext,
                req, res);
        TilesContainer container = TilesAccess.getContainer(applicationContext);
        if (container == null) {
            log.debug("Tiles container not found, so pass to next command.");
            return false;
        }

        boolean retValue = false;

        if (container.isValidDefinition(definitionName, request)) {
            retValue = true;
            try {
                container.render(definitionName, request);
            } catch (TilesException e) {
                throw new ServletException("Cannot render definition '"
                        + definitionName + "'");
            }
        } else {
            // ignore not found
            log.debug("Cannot find definition '{}'", definitionName);
        }

        return retValue;
    }

    /**
     * Do a forward using request dispatcher. Uri is a valid uri. If response
     * has already been commited, do an include instead.
     *
     * @param uri      Uri or Definition name to forward.
     * @param request  Current page request.
     * @param response Current page response.
     *
     * @throws IOException      If something goes wrong during writing the
     *                          definition.
     * @throws ServletException If something goes wrong during the evaluation
     *                          of the definition
     */
    protected void doForward(
        String uri,
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException, ServletException {

        if (response.isCommitted()) {
            this.doInclude(uri, request, response);

        } else {
            super.doForward(uri, request, response);
        }
    }

    /**
     * Overloaded method from Struts' RequestProcessor. Forward or redirect to
     * the specified destination by the specified mechanism.
     * <p>This method catches the Struts' actionForward call. It checks if the
     * actionForward is done on a Tiles definition name. If true, process the
     * definition and insert it. If false, call the original parent's
     * method.</p>
     *
     * @param request  The servlet request we are processing.
     * @param response The servlet response we are creating.
     * @param forward  The ActionForward controlling where we go next.
     *
     * @throws IOException      if an input/output error occurs.
     * @throws ServletException if a servlet exception occurs.
     */
    protected void processForwardConfig(
        HttpServletRequest request,
        HttpServletResponse response,
        ForwardConfig forward)
        throws IOException, ServletException {

        // Required by struts contract
        if (forward == null) {
            return;
        }

        log.debug("processForwardConfig({})", forward.getPath());

        // Try to process the definition.
        if (processTilesDefinition(forward.getPath(),
                request, response)) {
            log.debug("  '{}' - processed as definition", forward.getPath());
            return;
        }

        log.debug("  '{}' - processed as uri", forward.getPath());

        // forward doesn't contain a definition, let parent do processing
        super.processForwardConfig(request, response, forward);
    }

    /**
     * Catch the call to a module relative forward. If the specified uri is a
     * tiles definition name, insert it. Otherwise, parent processing is
     * called. Do a module relative forward to specified uri using request
     * dispatcher. Uri is relative to the current module. The real uri is
     * computed by prefixing the module name.
     * <p><strong>This method is used internally and is not part of the public
     * API. It is advised to not use it in subclasses.</strong></p>
     *
     * @param uri      Module-relative URI to forward to.
     * @param request  Current page request.
     * @param response Current page response.
     *
     * @throws IOException      If something goes wrong during writing the
     *                          definition.
     * @throws ServletException If something goes wrong during the evaluation
     *                          of the definition
     *
     * @since Struts 1.1
     */
    protected void internalModuleRelativeForward(
        String uri,
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException, ServletException {

        if (processTilesDefinition(uri, request, response)) {
            return;
        }

        super.internalModuleRelativeForward(uri, request, response);
    }

    /**
     * Do a module relative include to specified uri using request dispatcher.
     * Uri is relative to the current module. The real uri is computed by
     * prefixing the module name.
     * <p><strong>This method is used internally and is not part of the public
     * API. It is advised to not use it in subclasses.</strong></p>
     *
     * @param uri      Module-relative URI to forward to.
     * @param request  Current page request.
     * @param response Current page response.
     *
     * @throws IOException      If something goes wrong during writing the
     *                          definition.
     * @throws ServletException If something goes wrong during the evaluation
     *                          of the definition
     *
     * @since Struts 1.1
     */
    protected void internalModuleRelativeInclude(
        String uri,
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException, ServletException {

        if (processTilesDefinition(uri, request, response)) {
            return;
        }

        super.internalModuleRelativeInclude(uri, request, response);
    }
}