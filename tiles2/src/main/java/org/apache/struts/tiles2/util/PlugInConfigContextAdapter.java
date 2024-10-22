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

package org.apache.struts.tiles2.util;

import org.apache.struts.config.PlugInConfig;
import org.apache.tiles.servlet.context.ServletTilesApplicationContext;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Adapts a {@link PlugInConfig} object to become a ServletContext object,
 * exposing init parameters methods.
 */
public class PlugInConfigContextAdapter extends ServletTilesApplicationContext implements ServletContext {

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
     * @param servletContext     The servlet context to use.
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

    @Override
    public String getContextPath() {
        return getServletContext().getContextPath();
    }

    @Override
    public ServletContext getContext(String uripath) {
        return getServletContext().getContext(uripath);
    }

    @Override
    public int getMajorVersion() {
        return getServletContext().getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
        return getServletContext().getMinorVersion();
    }

    @Override
    public int getEffectiveMajorVersion() {
        return getServletContext().getEffectiveMajorVersion();
    }

    @Override
    public int getEffectiveMinorVersion() {
        return getServletContext().getEffectiveMinorVersion();
    }

    @Override
    public String getMimeType(String file) {
        return getServletContext().getMimeType(file);
    }

    @Override
    public Set<String> getResourcePaths(String path) {
        return getServletContext().getResourcePaths(path);
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        return getServletContext().getResourceAsStream(path);
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return getServletContext().getRequestDispatcher(path);
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String name) {
        return getServletContext().getNamedDispatcher(name);
    }

    @Override
    public Servlet getServlet(String name) throws ServletException {
        return getServletContext().getServlet(name);
    }

    @Override
    public Enumeration<Servlet> getServlets() {
        return getServletContext().getServlets();
    }

    @Override
    public Enumeration<String> getServletNames() {
        return getServletContext().getServletNames();
    }

    @Override
    public void log(String msg) {
        getServletContext().log(msg);
    }

    @Override
    public void log(Exception exception, String msg) {
        getServletContext().log(exception, msg);
    }

    @Override
    public void log(String message, Throwable throwable) {
        getServletContext().log(message, throwable);
    }

    @Override
    public String getRealPath(String path) {
        return getServletContext().getRealPath(path);
    }

    @Override
    public String getServerInfo() {
        return getServletContext().getServerInfo();
    }

    /**
     * Returns an initialization parameter.
     *
     * @param parameterName The name of the parameter.
     * @return The value of the parameter.
     */
    public String getInitParameter(String parameterName) {
        String retValue;

        retValue = (String) plugInConfigObject.getProperties()
                .get(parameterName);
        if (retValue == null) {
            retValue = getServletContext().getInitParameter(parameterName);
        }

        return retValue;
    }

    /**
     * Returns the names of all initialization parameters.
     *
     * @return The names of all initialization parameters.
     */
    public Enumeration<String> getInitParameterNames() {
        return getServletContext().getInitParameterNames();
    }

    @Override
    public boolean setInitParameter(String name, String value) {
        return getServletContext().setInitParameter(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return getServletContext().getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return getServletContext().getAttributeNames();
    }

    @Override
    public void setAttribute(String name, Object object) {
        getServletContext().setAttribute(name, object);
    }

    @Override
    public void removeAttribute(String name) {
        getServletContext().removeAttribute(name);
    }

    @Override
    public String getServletContextName() {
        return getServletContext().getServletContextName();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, String className) {
        return getServletContext().addServlet(servletName, className);
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
        return getServletContext().addServlet(servletName, servlet);
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
        return getServletContext().addServlet(servletName, servletClass);
    }

    @Override
    public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
        return getServletContext().addJspFile(servletName, jspFile);
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        return getServletContext().createServlet(clazz);
    }

    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        return getServletContext().getServletRegistration(servletName);
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return getServletContext().getServletRegistrations();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
        return getServletContext().addFilter(filterName, className);
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        return getServletContext().addFilter(filterName, filter);
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
        return getServletContext().addFilter(filterName, filterClass);
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        return getServletContext().createFilter(clazz);
    }

    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        return getServletContext().getFilterRegistration(filterName);
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return getServletContext().getFilterRegistrations();
    }

    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return getServletContext().getSessionCookieConfig();
    }

    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        getServletContext().setSessionTrackingModes(sessionTrackingModes);
    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return getServletContext().getDefaultSessionTrackingModes();
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return getServletContext().getEffectiveSessionTrackingModes();
    }

    @Override
    public void addListener(String className) {
        getServletContext().addListener(className);
    }

    @Override
    public <T extends EventListener> void addListener(T t) {
        getServletContext().addListener(t);
    }

    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        getServletContext().addListener(listenerClass);
    }

    @Override
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        return getServletContext().createListener(clazz);
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        return getServletContext().getJspConfigDescriptor();
    }

    @Override
    public ClassLoader getClassLoader() {
        return getServletContext().getClassLoader();
    }

    @Override
    public void declareRoles(String... roleNames) {
        getServletContext().declareRoles(roleNames);
    }

    @Override
    public String getVirtualServerName() {
        return getServletContext().getVirtualServerName();
    }

    @Override
    public int getSessionTimeout() {
        return getServletContext().getSessionTimeout();
    }

    @Override
    public void setSessionTimeout(int sessionTimeout) {
        getServletContext().setSessionTimeout(sessionTimeout);
    }

    @Override
    public String getRequestCharacterEncoding() {
        return getServletContext().getRequestCharacterEncoding();
    }

    @Override
    public void setRequestCharacterEncoding(String encoding) {
        getServletContext().setRequestCharacterEncoding(encoding);
    }

    @Override
    public String getResponseCharacterEncoding() {
        return getServletContext().getResponseCharacterEncoding();
    }

    @Override
    public void setResponseCharacterEncoding(String encoding) {
        getServletContext().setResponseCharacterEncoding(encoding);
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
        return getServletContext().getResource(path);
    }
}