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

package org.apache.struts.tiles3.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts.config.PlugInConfig;
import org.apache.tiles.request.servlet.ServletApplicationContext;


/**
 * Adapts a {@link PlugInConfig} object to become a ServletContext object,
 * exposing init parameters methods.
 */
public class PlugInConfigContextAdapter extends ServletApplicationContext {

    /**
     * The internal plugin config object.
     */
    private final PlugInConfig plugInConfigObject;

    /**
     * The <code>Map</code> of context initialization parameters.
     */
    private final Map<String, String> initParam;

    /**
     * Constructor.
     *
     * @param plugInConfigObject The plugin config object to use.
     * @param servletContext The servlet context to use.
     */
    public PlugInConfigContextAdapter(PlugInConfig plugInConfigObject,
            ServletContext servletContext) {
        super(servletContext);
        this.plugInConfigObject = plugInConfigObject;

        HashMap<String, String> initParam_ = new HashMap<>(super.getInitParams());
        for (Map.Entry<String, Object> entry : plugInConfigObject.getProperties().entrySet()) {
            initParam_.put(entry.getKey(), entry.getValue().toString());
        }
        initParam = Collections.unmodifiableMap(initParam_);
    }

    @Override
    public Map<String, String> getInitParams() {
        return initParam;
    }

    /**
     * Returns the internal plugin config object.
     *
     * @return the internal plugin config object
     */
    public PlugInConfig getPlugInConfigObject() {
        return plugInConfigObject;
    }
}