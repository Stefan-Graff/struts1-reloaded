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

package org.apache.struts.tiles2.preparer;

import org.apache.struts.action.Action;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.ServletRequest;

/**
 * Struts wrapper implementation of Controller.  This implementation wraps an
 * <code>Action</code> in a <code>Controller</code>.
 */
public class ActionPreparer implements ViewPreparer {

    /**
     * Struts action wrapped.
     */
    private Action action = null;

    /**
     * Constructor.
     *
     * @param action Action to be wrapped.
     */
    public ActionPreparer(Action action) {
        this.action = action;
    }

    /** {@inheritDoc} */
    public void execute(Request tilesContext,
            AttributeContext attributeContext) throws PreparerException {
        if (tilesContext instanceof ServletRequest) {
            ServletRequest servletTilesContext =
                    (ServletRequest) tilesContext;
            try {
                this.action.execute(null, null, servletTilesContext.getRequest(),
                        servletTilesContext.getResponse());
            } catch (Exception e) {
                throw new PreparerException(
                        "The enclosed action threw an exception", e);
            }
        } else {
            throw new PreparerException("Not using a ServletTilesRequestContext");
        }
    }
}
