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

package com.liferay.portal.security.sso.iam.internal.auto.login;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.iam.IAM;
import com.liferay.portal.security.sso.iam.constants.IAMWebKeys;

/**
 * @author Marco Fargetta
 */
@Component(immediate = true, service = AutoLogin.class)
public class IAMAutoLogin extends BaseAutoLogin {

    @Override
    protected final String[] doLogin(final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        long companyId = PortalUtil.getCompanyId(request);

        if (!iam.isEnabled(companyId)) {
            return null;
        }

        User user = getUser(request, companyId);

        if (user == null) {
            return null;
        }

        List<String> credentials = new ArrayList<>();

        credentials.add(String.valueOf(user.getUserId()));
        credentials.add(user.getPassword());
        credentials.add(Boolean.FALSE.toString());

        return credentials.toArray(new String[credentials.size()]);
    }

    /**
     * Retrieve the user requesting the login.
     *
     * @param request The servlet request
     * @param companyId The company id
     * @return The user
     * @throws Exception If the user cannot be retrieved
     */
    protected final User getUser(final HttpServletRequest request,
            final long companyId)
            throws Exception {

        HttpSession session = request.getSession();

        String emailAddress = GetterUtil.getString(session.getAttribute(
                IAMWebKeys.IAM_USER_EMAIL_ADDRESS));

        if (Validator.isNotNull(emailAddress)) {
            session.removeAttribute(IAMWebKeys.IAM_USER_EMAIL_ADDRESS);

            return userLocalService.getUserByEmailAddress(companyId,
                    emailAddress);
        } else {
            String iamUserId = GetterUtil.getString((String) session
                    .getAttribute(IAMWebKeys.IAM_USER_ID));

            if (Validator.isNotNull(iamUserId)) {
                long classNameId = ClassNameLocalServiceUtil.getClassNameId(
                        User.class);
                List<ExpandoValue> values = ExpandoValueLocalServiceUtil
                        .getColumnValues(companyId, classNameId,
                                ExpandoTableConstants.DEFAULT_TABLE_NAME,
                                "iamUserID", iamUserId, -1, -1);
                if (values.size() > 1) {
                    throw new AuthException(
                            "Multiple user with the same IAM identifier");
                }
                if (values.size() == 1) {
                    return userLocalService.getUser(values.get(0)
                            .getClassPK());
                }
            }
        }

        return null;
    }

    /**
     * Sets the iam component.
     * @param iamComp The iam component
     */
    @Reference(unbind = "-")
    protected final void setIam(final IAM iamComp) {
        this.iam = iamComp;
    }

    /**
     * Sets the user service.
     * @param userLocalServiceComp The user local service
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
