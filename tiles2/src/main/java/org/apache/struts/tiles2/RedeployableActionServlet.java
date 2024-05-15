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
package org.apache.struts.tiles2;

import javax.servlet.ServletException;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ModuleConfig;

/**
 * WebLogic (at least v6 and v7) attempts to serialize the TilesRequestProcessor
 * when re-deploying the Webapp in development mode. The TilesRequestProcessor
 * is not serializable, and loses the Tiles definitions. This results in
 * NullPointerException and/or NotSerializableException when using the app after
 * automatic redeploy.
 * <p>
 * This bug report proposes a workaround for this problem, in the hope it will
 * help others and maybe motivate an actual fix.
 * </p>
 * <p>
 * The attached class extends the Struts Action servlet and fixes the problem by
 * reloading the Tiles definitions when they have disappeared.
 * </p>
 * <p>
 * For background discussion see
 * <a href="https://issues.apache.org/jira/browse/STR-1937">STR-1937</a>.
 * </p>
 *
 * @since 1.2.1
 */
public class RedeployableActionServlet extends ActionServlet {
    private static final long serialVersionUID = -3681534284719373420L;

    /**
     * The request processor for Tiles definitions.
     */
    private TilesRequestProcessor tileProcessor;

    protected synchronized RequestProcessor getRequestProcessor(
            final ModuleConfig config) throws ServletException {

        if (tileProcessor != null) {
            final TilesRequestProcessor processor =
                    (TilesRequestProcessor) super.getRequestProcessor(config);
            return processor;
        }

        // reset the request processor
        final String requestProcessorKey = Globals.REQUEST_PROCESSOR_KEY
                + config.getPrefix();
        getServletContext().removeAttribute(requestProcessorKey);

        // create a new request processor instance
        final TilesRequestProcessor processor =
                (TilesRequestProcessor) super.getRequestProcessor(config);

        tileProcessor = processor;

        return processor;
    }
}