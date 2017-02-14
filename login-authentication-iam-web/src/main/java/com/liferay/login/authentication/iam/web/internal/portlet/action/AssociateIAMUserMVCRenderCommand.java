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

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.iam.IAM;

/**
 * @author Marco Fargetta
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PortletKeys.FAST_LOGIN,
                "javax.portlet.name=" + PortletKeys.LOGIN,
                "mvc.command.name=/login/associate_iam_user" },
        service = MVCRenderCommand.class)
public class AssociateIAMUserMVCRenderCommand implements MVCRenderCommand {

    @Override
    public final String render(final RenderRequest renderRequest,
            final RenderResponse renderResponse) throws PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(
                WebKeys.THEME_DISPLAY);

        if (!iam.isEnabled(themeDisplay.getCompanyId())) {
            throw new PortletException(new PrincipalException.MustBeEnabled(
                    themeDisplay.getCompanyId(), IAMLoginAction.class
                            .getName()));
        }

        long iamIncompleteUserId = ParamUtil.getLong(renderRequest, "userId");

        if (!Validator.isNull(iamIncompleteUserId)) {
            User user = userLocalService.fetchUser(iamIncompleteUserId);

            return renderUpdateAccount(renderRequest, user);
        }

        return "/login.jsp";
    }

    /**
     * Redirect to the user update account page.
     *
     * @param portletRequest The portlet request
     * @param user The user to update
     * @return The jsp page for the update
     * @throws PortletException If the user cannot be associated with
     * the request
     */
    protected final String renderUpdateAccount(
            final PortletRequest portletRequest, final User user)
                    throws PortletException {

        portletRequest.setAttribute("selUser", user);

        return "/update_account.jsp";
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
     * Sets the user local service.
     *
     * @param userLocalServiceComp The user local service component
     */
    @Reference(unbind = "-")
    protected final void setUserLocalService(
            final UserLocalService userLocalServiceComp) {
        this.userLocalService = userLocalServiceComp;
    }

    /**
     * The iam component.
     */
    private IAM iam;

    /**
     * The user local service.
     */
    private UserLocalService userLocalService;
}
