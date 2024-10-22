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

package org.apache.struts.tiles3.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>An <strong>Action</strong> that dispatches to a Tiles Definition
 * that is named by the request parameter whose name is specified
 * by the <code>parameter</code> property of the corresponding
 * ActionMapping.
 * This action is useful in following situations:
 * <li>
 * <ul>To associate an Url to a definition</ul>
 * <ul>To use Struts &lt;html:link&gt; tag on a definition</ul>
 * </li>
 * <p>To configure the use of this action in your
 * <code>struts-config.xml</code> file, create an entry like this:</p>
 *
 * <code>
 *   &lt;action path="/saveSubscription"
 *           type="org.apache.struts.tiles3.actions.DefinitionDispatcherAction"
 *           parameter="def"/&gt;
 *     &lt;forward name="success"   path="anything" //&gt;
 *     &lt;forward name="error"     path="path.to.error.page" //&gt;
 * </code>
 *
 * <p>which will use the value of the request parameter named "def"
 * to pick the appropriate definition name.
 * <p>  The value for success doesn't matter. The forward will forward to
 * appropriate definition.
 * <p> The value for error should denote a valid jsp path or definition name.
 *
 * @version $Rev$ $Date$
 */
public class DefinitionDispatcherAction extends Action {
    private static final long serialVersionUID = 9052008321390723214L;

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(DefinitionDispatcherAction.class);

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it),
     * with provision for handling exceptions thrown by the business logic.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param req The HTTP request we are processing
     * @param res The HTTP response we are creating
     *
     * @throws Exception if the application business logic throws
     *  an exception
     * @return The forward object..
     * @since Struts 1.1
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res)
        throws Exception {

        // Identify the request parameter containing the method name
        // If none defined, use "def"
        String parameter = mapping.getParameter();
        if (parameter == null) {
            parameter = "def";
        }

        // Identify the method name to be dispatched to
        String name = req.getParameter(parameter);
        if (name == null) {
            log.error("Can't get parameter '{}'.", parameter);

            return mapping.findForward("error");
        }

        // Try to dispatch to requested definition
        // Read definition from factory, but we can create it here.
        ApplicationContext applicationContext = ServletUtil
                .getApplicationContext(req.getSession().getServletContext());
        Request request = new ServletRequest(applicationContext,
                req, res);
        TilesContainer container = TilesAccess.getContainer(applicationContext);
        if (container != null
                && container.isValidDefinition(name, request)) {
            container.render(name, request);
        } else {
            log.error("Can't get definition '{}'.", name);
            return mapping.findForward("error");
        }

        return mapping.findForward("success");
    }
}