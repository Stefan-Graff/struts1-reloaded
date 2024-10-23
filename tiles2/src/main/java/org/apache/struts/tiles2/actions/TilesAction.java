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

package org.apache.struts.tiles2.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.access.TilesAccess;

/**
 * Base class for Tiles Actions.
 * This class has the same role as Struts Action. It provides a method execute(...)
 * called when action is invoked. The difference is, that the execute() method takes
 * an additional parameter : tile context.
 * This class extends Struts Action. Subclasses should override
 * execute(AttributeContext ...) method instead of Struts
 * execute(ActionMapping ...) method.
 * @version $Rev$ $Date$
 */
public abstract class TilesAction extends Action {

    /**
     * Original Struts Action's method.
     * Retrieve current Tile context and call TilesAction execute method.
     * Do not overload this method!
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request (if any).
     * @param request The HTTP request we are processing.
     * @param response The HTTP response we are creating.
     *
     * @throws Exception if the application business logic throws
     *  an exception
     * @return The forward object.
     * @since Struts 1.1
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        // Try to retrieve tile context
        AttributeContext context = TilesAccess.getContainer(
                request.getSession().getServletContext()).getAttributeContext(
                new Object[] { request, response });
        if (context == null) {
            throw new ServletException(
                "Can't find Tile context for '"
                    + this.getClass().getName()
                    + "'. TilesAction subclasses must be called from a Tile");
        }

        return this.execute(context, mapping, form, request, response);
    }

    /**
     * Process the specified HTTP request and create the corresponding HTTP
     * response (or forward to another web component that will create it),
     * with provision for handling exceptions thrown by the business logic.
     * <br>
     * Override this method to provide functionality.
     *
     * @param context The current Tile context, containing Tile attributes.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request (if any).
     * @param request The HTTP request we are processing.
     * @param response The HTTP response we are creating.
     *
     * @throws Exception if the application business logic throws
     *  an exception
     * @return The forward object.
     * @since Struts 1.1
     */
    public ActionForward execute(
        AttributeContext context,
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        return null;
    }

}
