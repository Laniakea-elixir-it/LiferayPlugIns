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
package com.liferay.portal.settings.authentication.iam.web.internal.
    portlet.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.iam.constants.IAMConstants;
import com.liferay.portal.settings.portlet.action.
    BasePortalSettingsFormMVCActionCommand;
import com.liferay.portal.settings.web.constants.PortalSettingsPortletKeys;

/**
 * @author Marco Fargetta
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name="
                        + PortalSettingsPortletKeys.PORTAL_SETTINGS,
                "mvc.command.name=/portal_settings/iam_connect" },
        service = MVCActionCommand.class)
public class PortalSettingsIAMFormMVCActionCommand extends
        BasePortalSettingsFormMVCActionCommand {

    @Override
    protected final String getParameterNamespace() {
        return "iam--";
    }

    @Override
    protected final String getSettingsId() {
        return IAMConstants.SERVICE_NAME;
    }

    @Override
    protected final void doValidateForm(final ActionRequest actionRequest,
            final ActionResponse actionResponse) throws Exception {

        boolean iamEnabled = getBoolean(actionRequest, "enabled");

        if (!iamEnabled) {
            return;
        }

        String iamConfigurationURL = getString(actionRequest,
                "configurationURL");
        String iamOauthAuthURL = getString(actionRequest, "oauthAuthURL");
        String iamOauthTokenURL = getString(actionRequest, "oauthTokenURL");
        String iamOpenidUserinfoURL = getString(actionRequest,
                "openidUserinfoURL");
        String iamOpenidJwkURL = getString(actionRequest, "openidJwkURL");

        if (Validator.isNotNull(iamConfigurationURL) && !Validator.isUrl(
                iamConfigurationURL)) {

            SessionErrors.add(actionRequest, "iamConfigurationURLInvalid");
        }

        if (Validator.isNotNull(iamOauthAuthURL) && !Validator.isUrl(
                iamOauthAuthURL)) {

            SessionErrors.add(actionRequest, "iamOauthAuthURLInvalid");
        }

        if (Validator.isNotNull(iamOauthTokenURL) && !Validator.isUrl(
                iamOauthTokenURL)) {

            SessionErrors.add(actionRequest, "iamOauthTokenURLInvalid");
        }

        if (Validator.isNotNull(iamOpenidUserinfoURL) && !Validator.isUrl(
                iamOpenidUserinfoURL)) {

            SessionErrors.add(actionRequest, "iamOpenidUserinfoURLInvalid");
        }

        if (Validator.isNotNull(iamOpenidJwkURL) && !Validator.isUrl(
                iamOpenidJwkURL)) {

            SessionErrors.add(actionRequest, "iamOpenidJWKURLInvalid");
        }
    }
}
