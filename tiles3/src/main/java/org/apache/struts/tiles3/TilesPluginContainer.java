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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.ModuleUtils;
import org.apache.tiles.Definition;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.locale.LocaleResolver;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.ServletRequest;

public class TilesPluginContainer extends BasicTilesContainer {

    /**
     * Maps definition factories to their keys.
     */
    private final Map<String, DefinitionsFactory> key2definitionsFactory =
            new HashMap<>();

    /**
     * The locale resolver.
     */
    private LocaleResolver localeResolver;

    /**
     * Sets the locale resolver.
     *
     * @param localeResolver the locale resolver for this container.
     *
     * @since Struts 1.4.1
     */
    void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    /**
     * Returns the locale resolver.
     *
     * @return The locale resolve.
     *
     * @since Struts 1.4.1
     */
    LocaleResolver getLocaleResolverIntern() {
        return localeResolver;
    }

    /**
     * Returns the proper definition factory for the given key, i.e. if the key
     * is not present, <code>null</code> will be returned.
     *
     * @return the definitions factory used by this container. If the key is not
     * valid, <code>null</code> will be returned.
     * @param key The key of the needed definitions factory.
     */
    public DefinitionsFactory getProperDefinitionsFactory(String key) {
        DefinitionsFactory retValue = null;

        if (key != null) {
            retValue = key2definitionsFactory.get(key);
        }

        return retValue;
    }

    /**
     * Set the definitions factory. This method first ensures that the container
     * has not yet been initialized.
     *
     * @param key The key under which the definitions factory is cataloged.
     * @param definitionsFactory the definitions factory for this instance.
     * @since 2.1.0
     */
    public void setDefinitionsFactory(String key,
            DefinitionsFactory definitionsFactory) {
        if (key != null) {
            key2definitionsFactory.put(key, definitionsFactory);
        } else {
            setDefinitionsFactory(definitionsFactory);
        }
    }

    @Override
    public Definition getDefinition(String definitionName,
            Request request) {
        Definition retValue = null;
        String key = getDefinitionsFactoryKey(request);
        if (key != null) {
            DefinitionsFactory definitionsFactory =
                key2definitionsFactory.get(key);
            if (definitionsFactory != null) {
                retValue = definitionsFactory.getDefinition(definitionName,
                        request);
            }
        }
        if (retValue == null) {
            retValue = super.getDefinition(definitionName, request);
        }
        return retValue;
    }

    /**
     * Returns the definitions factory key.
     *
     * @param request The request object.
     * @return The needed factory key.
     */
    protected String getDefinitionsFactoryKey(Request request) {
        String retValue = null;

        if (request instanceof ServletRequest) {
            HttpServletRequest servletRequest =
                    ((ServletRequest) request).getRequest();
            ModuleConfig config = ModuleUtils.getInstance().getModuleConfig(
                    servletRequest);

            if (config == null) {
                // ModuleConfig not found in current request. Select it.
                ModuleUtils.getInstance().selectModule(servletRequest,
                        servletRequest.getSession().getServletContext());
                config = ModuleUtils.getInstance().getModuleConfig(servletRequest);
            }

            if (config != null) {
                retValue = config.getPrefix();
            }
        }
        return retValue;
    }
}