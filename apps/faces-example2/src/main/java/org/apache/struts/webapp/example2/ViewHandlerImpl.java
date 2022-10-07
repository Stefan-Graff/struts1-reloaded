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

package org.apache.struts.webapp.example2;


import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Custom {@code ViewHandler} implementation that adds features
 * specific to the Struts-Faces Integration Library. It leverages the
 * "decorator pattern" customization strategy that JSF supports, by
 * delegating most processing to the {@code ViewHandler} instance
 * handed to our constructor.
 */
public class ViewHandlerImpl extends ViewHandlerWrapper {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code LOG} instance for this class.
     */
    private static final Log LOG = LogFactory.getLog(ViewHandlerImpl.class);


    // ------------------------------------------------------ Instance Variables


    /**
     * The {@code ViewHandler} instance that we are decorating.
     */
    private final ViewHandler oldViewHandler;


    // ------------------------------------------------------------ Constructors


    /**
     * Construct a {@code ViewHandlerImpl} decorating the
     * specified {@code ViewHandler} instance.
     *
     * @param oldViewHandler {@code ViewHandler} to be decorated
     */
    public ViewHandlerImpl(ViewHandler oldViewHandler) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating ViewHandler instance, wrapping handler " +
                    oldViewHandler);
        }
        this.oldViewHandler = oldViewHandler;
    }


    // -------------------------------------------------------------- Properties


    /**
     * Returns the {@code ViewHandler} instance that we are decorating.
     *
     * @return the {@code ViewHandler} instance that we are decorating.
     */
    @Override
    protected ViewHandler getWrapped() {
        return oldViewHandler;
    }


    // ----------------------------------------------------- Specialized Methods


    /**
     * Replace extension {@code .jsp} with {@code .faces}.
     */
    public String getActionURL(FacesContext context, String viewId) {
        if (viewId.endsWith(".jsp")) {
            viewId = viewId.substring(0, viewId.length() - 4) + ".faces";
        }
        return super.getActionURL(context, viewId);
    }
}