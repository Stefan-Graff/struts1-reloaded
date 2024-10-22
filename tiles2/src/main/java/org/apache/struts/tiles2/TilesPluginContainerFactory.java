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

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.definition.UnresolvingLocaleDefinitionsFactory;
import org.apache.tiles.evaluator.AttributeEvaluatorFactory;
import org.apache.tiles.factory.BasicTilesContainerFactory;
import org.apache.tiles.factory.TilesContainerFactoryException;
import org.apache.tiles.locale.LocaleResolver;

public class TilesPluginContainerFactory extends BasicTilesContainerFactory {

    @Override
    public TilesContainer createContainer(TilesApplicationContext applicationContext) {
        TilesPluginContainer container = instantiateContainer(applicationContext);
        TilesRequestContextFactory requestContextFactory =
                createRequestContextFactory(applicationContext);
        container.setApplicationContext(applicationContext);
        LocaleResolver resolver = createLocaleResolver(applicationContext, requestContextFactory);
        container.setLocaleResolver(resolver);
        container.setDefinitionsFactory(createDefinitionsFactory(applicationContext, requestContextFactory,
                resolver));
        AttributeEvaluatorFactory attributeEvaluatorFactory = createAttributeEvaluatorFactory(
                applicationContext, requestContextFactory, resolver);
        container.setAttributeEvaluatorFactory(attributeEvaluatorFactory);
        container.setPreparerFactory(createPreparerFactory(applicationContext, requestContextFactory));
        container.setRendererFactory(createRendererFactory(applicationContext, requestContextFactory,
                container, attributeEvaluatorFactory));
        return container;
    }

    @Override
    protected TilesPluginContainer instantiateContainer(
            TilesApplicationContext context) {
        return new TilesPluginContainer();
    }

    /**
     * Returns a list containing the URLs to be parsed. By default, it returns a
     * list containing the URL point to "definitions-config".
     *
     * @param applicationContext The Tiles application context.
     * @param contextFactory The Tiles-Request-Context-Factory
     * @return The source URLs.
     * @since Struts 1.4.1
     */
    protected List<URL> getSourceURLs(TilesApplicationContext applicationContext,
                                      TilesRequestContextFactory contextFactory) {
        String param = applicationContext
                .getInitParams().get(DefinitionsFactory.DEFINITIONS_CONFIG);
        if (param == null) {
            param = applicationContext
                    .getInitParams().get("definitions-config");
        }
        if (param == null) {
            param = "/WEB-INF/tiles.xml";
        }

        try {
            return Collections
                    .singletonList(applicationContext.getResource(param));
        } catch (IOException e) {
            throw new TilesContainerFactoryException(
                    "Cannot get URL: " + param, e);
        }
    }

    /**
     * Creates the definitions factory. By default it creates a
     * {@link UnresolvingLocaleDefinitionsFactory} with default dependencies.
     *
     * @param container The Tiles-Plugin-Container.
     * @param applicationContext The Tiles application context.
     *
     * @return The definitions factory.
     * @since Struts 1.4.1
     */
    public DefinitionsFactory createDefinitionsFactory(TilesPluginContainer container,
                                                       TilesApplicationContext applicationContext) {
        TilesRequestContextFactory requestContextFactory =
                createRequestContextFactory(applicationContext);
        LocaleResolver resolver = container.getLocaleResolverIntern();
        return createDefinitionsFactory(applicationContext, requestContextFactory, resolver);
    }

}