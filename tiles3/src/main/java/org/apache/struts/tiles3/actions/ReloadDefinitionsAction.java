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

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This standard <strong>Action</strong> is for compatibility with
 * {@code struts-tiles}. The configuration is now reloaded automatically.
 */
public class ReloadDefinitionsAction extends Action {
    private static final long serialVersionUID = -7402180335252956248L;

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it), with
     * provision for handling exceptions thrown by the business logic.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @return The forward object.
     *
     * @throws Exception if the application business logic throws an exception
     *
     * @since Struts 1.1
     */
    public ActionForward execute(final ActionMapping mapping,
            final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        response.setContentType("text/plain");

        final PrintWriter writer = response.getWriter();

        writer.println("OK");

        writer.flush();
        writer.close();

        return null;
    }
}