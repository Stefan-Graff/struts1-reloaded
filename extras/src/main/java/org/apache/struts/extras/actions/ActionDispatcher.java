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
package org.apache.struts.extras.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.dispatcher.Dispatcher;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>Action <i>helper</i> class that dispatches to a public method in an
 * Action.</p> <p/> <p>This class is provided as an alternative mechanism to
 * using DispatchAction and its various flavours and means <i>Dispatch</i>
 * behaviour can be easily implemented into any <code>Action</code> without
 * having to inherit from a particular super <code>Action</code>.</p> <p/>
 * <p>To implement <i>dispatch</i> behaviour in an <code>Action</code> class,
 * create your custom Action as follows, along with the methods you require
 * (and optionally "cancelled" and "unspecified" methods):</p> <p/>
 * <pre>
 *   public class MyCustomAction extends Action {
 *
 *       protected ActionDispatcher dispatcher
 *                = new ActionDispatcher(this, ActionDispatcher.MAPPING_FLAVOR);
 *
 *       public ActionForward execute(ActionMapping mapping,
 *                                    ActionForm form,
 *                                    HttpServletRequest request,
 *                                    HttpServletResponse response)
 *                           throws Exception {
 *           return dispatcher.execute(mapping, form, request, response);
 *       }
 *   }
 * </pre>
 * <p/>
 *
 * <p>It provides three flavours of determing the name of the method:</p>
 *
 * <ul>
 *
 * <li><strong>{@link #DEFAULT_FLAVOR}</strong> - uses the parameter
 * specified in the struts-config.xml to get the method name from the Request
 * (equivalent to <code>DispatchAction</code> <b>except</b> uses "method" as a
 * default if the <code>parameter</code> is not specified in the
 * struts-config.xml).</li>
 *
 * <li><strong>{@link #DISPATCH_FLAVOR}</strong>
 * - uses the parameter specified in the struts-config.xml to get the method
 * name from the Request (equivalent to <code>DispatchAction</code>).</li>
 *
 * <li><strong>{@link #MAPPING_FLAVOR}</strong> - uses the parameter
 * specified in the struts-config.xml as the method name (equivalent to
 * <code>MappingDispatchAction</code>).</li>

 * </ul>
 *
 * @version $Rev$ $Date$
 * @since Struts 1.2.7
 */
public class ActionDispatcher implements Dispatcher {
    private static final long serialVersionUID = 3784151345188637566L;

    // ----------------------------------------------------- Instance Variables

    /**
     * Indicates "default" dispatch flavor.
     */
    public static final int DEFAULT_FLAVOR = 0;

    /**
     * Indicates "mapping" dispatch flavor.
     */
    public static final int MAPPING_FLAVOR = 1;

    /**
     * Indicates flavor compatible with DispatchAction.
     */
    public static final int DISPATCH_FLAVOR = 2;

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(ActionDispatcher.class);

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(
            "org.apache.struts.extras.actions.LocalStrings");

    /**
     * The associated Action to dispatch to.
     */
    protected Action actionInstance;

    /**
     * Indicates dispatch <i>flavor</i>.
     */
    protected int flavor;

    /**
     * The Class instance of this <code>DispatchAction</code> class.
     */
    protected Class<? extends Action> clazz;

    /**
     * The set of Method objects we have introspected for this class, keyed by
     * method name.  This collection is populated as different methods are
     * called, so that introspection needs to occur only once per method
     * name.
     */
    protected HashMap<String, Method> methods = new HashMap<>();

    /**
     * The set of argument type classes for the reflected method call.  These
     * are the same for all calls, so calculate them only once.
     */
    protected Class<?>[] types =
        {
            ActionMapping.class, ActionForm.class, HttpServletRequest.class,
            HttpServletResponse.class
        };

    // ----------------------------------------------------- Constructors

    /**
     * Construct an instance of this class from the supplied parameters.
     *
     * @param actionInstance The action instance to be invoked.
     */
    public ActionDispatcher(Action actionInstance) {
        this(actionInstance, DEFAULT_FLAVOR);
    }

    /**
     * Construct an instance of this class from the supplied parameters.
     *
     * @param actionInstance The action instance to be invoked.
     * @param flavor         The flavor of dispatch to use.
     */
    public ActionDispatcher(Action actionInstance, int flavor) {
        this.actionInstance = actionInstance;
        this.flavor = flavor;

        clazz = actionInstance.getClass();
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if an exception occurs
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        // Process "cancelled"
        if (isCancelled(request)) {
            ActionForward af = cancelled(mapping, form, request, response);

            if (af != null) {
                return af;
            }
        }

        // Identify the request parameter containing the method name
        String parameter = getParameter(mapping, form, request, response);

        // Get the method's name. This could be overridden in subclasses.
        String name =
            getMethodName(mapping, form, request, response, parameter);

        // Prevent recursive calls
        if ("execute".equals(name) || "perform".equals(name)) {
            String message =
                messages.getMessage("dispatch.recursive", mapping.getPath());

            log.error(message);
            throw new ServletException(message);
        }

        // Invoke the named method, and return the result
        return dispatchMethod(mapping, form, request, response, name);
    }

    /**
     * <p>Dispatches to the target class' <code>unspecified</code> method, if
     * present, otherwise throws a ServletException. Classes utilizing
     * <code>ActionDispatcher</code> should provide an <code>unspecified</code>
     * method if they wish to provide behavior different than throwing a
     * ServletException.</p>
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The non-HTTP request we are processing
     * @param response The non-HTTP response we are creating
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if the application business logic throws an
     *                   exception.
     */
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        // Identify if there is an "unspecified" method to be dispatched to
        String name = "unspecified";
        Method method = null;

        try {
            method = getMethod(name);
        } catch (NoSuchMethodException e) {
            String message =
                messages.getMessage("dispatch.parameter", mapping.getPath(),
                    mapping.getParameter());

            log.error(message);

            throw new ServletException(message, e);
        }

        return dispatchMethod(mapping, form, request, response, name, method);
    }

    /**
     * <p>Dispatches to the target class' cancelled method, if present,
     * otherwise returns null. Classes utilizing <code>ActionDispatcher</code>
     * should provide a <code>cancelled</code> method if they wish to provide
     * behavior different than returning null.</p>
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The non-HTTP request we are processing
     * @param response The non-HTTP response we are creating
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if the application business logic throws an
     *                   exception.
     */
    protected ActionForward cancelled(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        // Identify if there is an "cancelled" method to be dispatched to
        String name = "cancelled";
        Method method = null;

        try {
            method = getMethod(name);
        } catch (NoSuchMethodException e) {
            return null;
        }

        return dispatchMethod(mapping, form, request, response, name, method);
    }

    // ----------------------------------------------------- Protected Methods

    /**
     * Dispatch to the specified method.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The non-HTTP request we are processing
     * @param response The non-HTTP response we are creating
     * @param name     The name of the method to invoke
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if the application business logic throws an
     *                   exception.
     */
    protected ActionForward dispatchMethod(ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response, String name)
        throws Exception {
        // Make sure we have a valid method name to call.
        // This may be null if the user hacks the query string.
        if (name == null) {
            return this.unspecified(mapping, form, request, response);
        }

        // Identify the method object to be dispatched to
        Method method = null;

        try {
            method = getMethod(name);
        } catch (NoSuchMethodException e) {
            log.atError()
                .setMessage(() -> messages.getMessage("dispatch.method", mapping.getPath(), name))
                .setCause(e).log();

            String userMsg =
                messages.getMessage("dispatch.method.user", mapping.getPath());
            NoSuchMethodException e2 = new NoSuchMethodException(userMsg);
            e2.initCause(e);
            throw e2;
        }

        return dispatchMethod(mapping, form, request, response, name, method);
    }

    /**
     * Dispatch to the specified method.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The non-HTTP request we are processing
     * @param response The non-HTTP response we are creating
     * @param name     The name of the method to invoke
     * @param method   The method to invoke
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if the application business logic throws an
     *                   exception.
     */
    protected ActionForward dispatchMethod(ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response, String name, Method method)
        throws Exception {
        ActionForward forward = null;

        try {
            Object[] args = { mapping, form, request, response };

            forward = (ActionForward) method.invoke(actionInstance, args);
        } catch (ClassCastException e) {
            log.atError()
                .setMessage(() -> messages.getMessage("dispatch.return", mapping.getPath(), name))
                .setCause(e).log();
            throw e;
        } catch (IllegalAccessException e) {
            log.atError()
                .setMessage(() -> messages.getMessage("dispatch.error", mapping.getPath(), name))
                .setCause(e).log();
            throw e;
        } catch (InvocationTargetException e) {
            // Rethrow the target exception if possible so that the
            // exception handling machinery can deal with it
            Throwable t = e.getTargetException();

            if (t instanceof Exception) {
                throw ((Exception) t);
            } else {
                log.atError()
                    .setMessage(() -> messages.getMessage("dispatch.error", mapping.getPath(),
                        name))
                    .setCause(e).log();
                throw new ServletException(t);
            }
        }

        // Return the returned ActionForward instance
        return (forward);
    }

    /**
     * Introspect the current class to identify a method of the specified name
     * that accepts the same parameter types as the <code>execute</code>
     * method does.
     *
     * @param name Name of the method to be introspected
     * @return The method with the specified name.
     * @throws NoSuchMethodException if no such method can be found
     */
    protected Method getMethod(String name)
        throws NoSuchMethodException {
        synchronized (methods) {
            Method method = methods.get(name);

            if (method == null) {
                method = clazz.getMethod(name, types);
                methods.put(name, method);
            }

            return (method);
        }
    }

    /**
     * <p>Returns the parameter value as influenced by the selected {@link
     * #flavor} specified for this <code>ActionDispatcher</code>.</p>
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The <code>ActionMapping</code> parameter's value
     * @throws Exception if an error occurs.
     */
    protected String getParameter(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String parameter = mapping.getParameter();

        if ("".equals(parameter)) {
            parameter = null;
        }

        if ((parameter == null) && (flavor == DEFAULT_FLAVOR)) {
            // use "method" for DEFAULT_FLAVOR if no parameter was provided
            return "method";
        }

        if ((parameter == null)
            && ((flavor == MAPPING_FLAVOR) || (flavor == DISPATCH_FLAVOR))) {
            String message =
                messages.getMessage("dispatch.handler", mapping.getPath());

            log.error(message);

            throw new ServletException(message);
        }

        return parameter;
    }

    /**
     * Returns the method name, given a parameter's value.
     *
     * @param mapping   The ActionMapping used to select this instance
     * @param form      The optional ActionForm bean for this request (if
     *                  any)
     * @param request   The HTTP request we are processing
     * @param response  The HTTP response we are creating
     * @param parameter The <code>ActionMapping</code> parameter's name
     * @return The method's name.
     * @throws Exception if an error occurs.
     */
    protected String getMethodName(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response,
        String parameter) throws Exception {
        // "Mapping" flavor, defaults to "method"
        if (flavor == MAPPING_FLAVOR) {
            return parameter;
        }

        // default behaviour
        return request.getParameter(parameter);
    }

    /**
     * <p>Returns <code>true</code> if the current form's cancel button was
     * pressed.  This method will check if the <code>Globals.CANCEL_KEY</code>
     * request attribute has been set, which normally occurs if the cancel
     * button generated by <strong>CancelTag</strong> was pressed by the user
     * in the current request.  If <code>true</code>, validation performed by
     * an <strong>ActionForm</strong>'s <code>validate()</code> method will
     * have been skipped by the controller servlet.</p>
     *
     * @param request The servlet request we are processing
     * @return <code>true</code> if the current form's cancel button was
     *         pressed; <code>false</code> otherwise.
     * @see org.apache.struts.taglib.html.CancelTag
     */
    protected boolean isCancelled(HttpServletRequest request) {
        return RequestUtils.isRequestCancelled(request);
    }

    /**
     * @since Struts 1.4
     */
    public Object dispatch(ActionContext context) throws Exception {
        ServletActionContext servletContext = (ServletActionContext) context;
        return execute((ActionMapping) context.getActionConfig(), context.getActionForm(),
            servletContext.getRequest(), servletContext.getResponse());
    }
}