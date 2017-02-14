/**
 * *********************************************************************
 * Copyright (c) 2016: Istituto Nazionale di Fisica Nucleare (INFN) -
 * INDIGO-DataCloud
 *
 * See http://www.infn.it and and http://www.consorzio-cometa.it for details on
 * the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 **********************************************************************
 */

package com.liferay.login.authentication.iam.web.internal.servlet.taglib;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.iam.IAM;

/**
 * @author Marco Fargetta
 */
@Component(immediate = true, service = DynamicInclude.class)
public class IAMNavigationPreDynamicInclude extends BaseDynamicInclude {

    @Override
    public final void include(final HttpServletRequest request,
            final HttpServletResponse response, final String key)
                    throws IOException {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
                WebKeys.THEME_DISPLAY);

        if (!iam.isEnabled(themeDisplay.getCompanyId())) {
            return;
        }

        RequestDispatcher requestDispatcher = servletContext
                .getRequestDispatcher(JSP_PATH);

        try {
            requestDispatcher.include(request, response);
        } catch (ServletException se) {
            log.error("Unable to include JSP " + JSP_PATH, se);

            throw new IOException("Unable to include JSP " + JSP_PATH, se);
        }
    }

    @Override
    public final void register(
            final DynamicIncludeRegistry dynamicIncludeRegistry) {
        dynamicIncludeRegistry.register(
                "com.liferay.login.web#/navigation.jsp#pre");
    }

    /**
     * Sets the iam component.
     *
     * @param iamComp The iam component
     */
    @Reference(unbind = "-")
    protected final void setIam(final IAM iamComp) {
        this.iam = iamComp;
    }

    /**
     * Sets the servlet context.
     *
     * @param servletContextComp The servlet context
     */
    @Reference(
            target =
             "(osgi.web.symbolicname=com.liferay.login.authentication.iam.web)",
            unbind = "-")
    protected final void setServletContext(
            final ServletContext servletContextComp) {
        this.servletContext = servletContextComp;
    }

    /**
     * JSP path to the main UI file.
     */
    private static final String JSP_PATH =
            "/html/portlet/login/navigation/iam.jsp";

    /**
     * The servlet context.
     */
    private ServletContext servletContext;

    /**
     * The iam component.
     */
    private IAM iam;

    /**
     * The logger.
     */
    private final Log log = LogFactoryUtil.getLog(
            IAMNavigationPreDynamicInclude.class);
}
