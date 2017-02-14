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

package com.liferay.login.authentication.iam.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.sso.iam.IAM;
import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Fargetta
 */
@Component(
        immediate = true,
        property = {"path=/portal/iam_openidconnect" },
        service = StrutsAction.class)
public class IAMLoginAction extends BaseStrutsAction {

    @Override
    public final String execute(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
                WebKeys.THEME_DISPLAY);

        if (!iam.isEnabled(themeDisplay.getCompanyId())) {
            throw new PrincipalException.MustBeEnabled(themeDisplay
                    .getCompanyId(), IAM.class.getName());
        }
        String cmd = ParamUtil.getString(request, Constants.CMD);
        if (cmd.equals("login")) {
            String returnRequestUri = getReturnRequestUri(request);

            String loginRedirect = iam.getLoginRedirect(themeDisplay
                    .getCompanyId(), returnRequestUri, SCOPES_LOGIN, false);

            response.sendRedirect(loginRedirect);
        } else if (cmd.equals("token")) {
            HttpSession session = request.getSession();

            String authorizationCode = ParamUtil.getString(request, "code");

            if (Validator.isNotNull(authorizationCode)) {
                String returnRequestUri = getReturnRequestUri(request);

                User user = null;
                try {
                    user = iam.addOrUpdateUser(session, themeDisplay
                            .getCompanyId(), authorizationCode,
                            returnRequestUri, SCOPES_LOGIN);
                    if (!iam.hasRefreshToken(user)) {
                        String loginRedirect = iam.getLoginRedirect(
                                themeDisplay.getCompanyId(), returnRequestUri,
                                SCOPES_LOGIN, true);

                        response.sendRedirect(loginRedirect);
                        return super.execute(request, response);
                    }

                } catch (Exception ex) {
                    log.error(ex);
                    throw new PortalException(
                            "Impossible to authenticate the user");
                }

                if ((user != null) && (user
                        .getStatus() == WorkflowConstants.STATUS_INCOMPLETE)) {

                    sendUpdateAccountRedirect(request, response, user);

                    return null;
                }

                sendLoginRedirect(request, response);

                return null;
            }

            String error = ParamUtil.getString(request, "error");

            if (Validator.isNotNull(error) && error.equals("access_denied")) {
                sendLoginRedirect(request, response);

                return null;
            }
        }
        return super.execute(request, response);
    }

    /**
     * Send the user to the page after login.
     *
     * @param request The servlet request
     * @param response The servlet response
     * @throws Exception If the user cannot be redirect
     */
    protected final void sendLoginRedirect(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
                WebKeys.THEME_DISPLAY);

        PortletURL portletURL = PortletURLFactoryUtil.create(request,
                PortletKeys.LOGIN, themeDisplay.getPlid(),
                PortletRequest.RENDER_PHASE);

        portletURL.setParameter("mvcRenderCommandName",
                "/login/login_redirect");
        portletURL.setWindowState(LiferayWindowState.POP_UP);

        response.sendRedirect(portletURL.toString());
    }

    /**
     * Send the user to the update account page.
     *
     * @param request The servlet request
     * @param response The servlet response
     * @param user The user to update
     * @throws Exception If the update page cannot be created
     */
    protected final void sendUpdateAccountRedirect(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final User user) throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
                WebKeys.THEME_DISPLAY);

        PortletURL portletURL = PortletURLFactoryUtil.create(request,
                PortletKeys.LOGIN, themeDisplay.getPlid(),
                PortletRequest.RENDER_PHASE);

        portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
        portletURL.setParameter("mvcRenderCommandName",
                "/login/associate_iam_user");

        PortletURL redirectURL = PortletURLFactoryUtil.create(request,
                PortletKeys.LOGIN, themeDisplay.getPlid(),
                PortletRequest.RENDER_PHASE);

        redirectURL.setParameter("mvcRenderCommandName",
                "/login/login_redirect");
        redirectURL.setParameter("emailAddress", user.getEmailAddress());
        redirectURL.setParameter("anonymousUser", Boolean.FALSE.toString());
        redirectURL.setPortletMode(PortletMode.VIEW);
        redirectURL.setWindowState(LiferayWindowState.POP_UP);

        portletURL.setParameter("redirect", redirectURL.toString());

        portletURL.setParameter("userId", String.valueOf(user.getUserId()));
        portletURL.setParameter("emailAddress", user.getEmailAddress());
        portletURL.setParameter("firstName", user.getFirstName());
        portletURL.setParameter("lastName", user.getLastName());
        portletURL.setPortletMode(PortletMode.VIEW);
        portletURL.setWindowState(LiferayWindowState.POP_UP);

        response.sendRedirect(portletURL.toString());
    }

    /**
     * Retrieves the URI to send the user after authentication on IAM.
     *
     * @param request The servlet request
     * @return The URI
     */
    protected final String getReturnRequestUri(
            final HttpServletRequest request) {
        return PortalUtil.getPortalURL(request) + PortalUtil.getPathMain()
                + REDIRECT_URI;
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
     * The redirect URI part of the portlet.
     */
    private static final String REDIRECT_URI =
            "/portal/iam_openidconnect?cmd=token";

    /**
     * Login scopes for the OpenIDConnect.
     */
    private static final List<String> SCOPES_LOGIN = Arrays.asList("openid",
            "profile", "email");

    /**
     * The iam component.
     */
    private IAM iam;

    /**
     * The logger.
     */
    private final Log log = LogFactoryUtil.getLog(IAMLoginAction.class);
}
