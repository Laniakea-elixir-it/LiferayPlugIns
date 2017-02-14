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

package com.liferay.portal.security.sso.iam.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.security.sso.iam.IAM;
import com.liferay.portal.security.sso.iam.constants.IAMConfigurationKeys;
import com.liferay.portal.security.sso.iam.model.TokenInfo;
import com.liferay.portal.security.sso.iam.service.base.TokenServiceBaseImpl;
import com.liferay.portal.security.sso.iam.service.permission.
    TokenAccessPermissionChecker;
import com.liferay.portal.spring.extender.service.ServiceReference;

import aQute.bnd.annotation.ProviderType;

/**
 * The implementation of the token remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link com.liferay.portal.security.sso.iam.service.TokenService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have
 * security checks based on the propagated JAAS credentials because this service
 * can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Marco Fargetta
 * @see TokenServiceBaseImpl
 * @see com.liferay.portal.security.sso.iam.service.TokenServiceUtil
 */
@ProviderType
public class TokenServiceImpl extends TokenServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. Always use {@link
     * com.liferay.portal.security.sso.iam.service.TokenServiceUtil} to access
     * the token remote service.
     */

    /**
     * Retrieves the token for the calling user.
     *
     * @param serviceContext The service context of the call
     * @return The token info containing the token
     * @throws PortalException If there are problem to collect the information
     */
    public final TokenInfo getToken(final ServiceContext serviceContext)
            throws PortalException {
        TokenAccessPermissionChecker.check(getPermissionChecker(), getUserId(),
                serviceContext.getScopeGroupId(), "VIEW");
        TokenInfo ti = new TokenInfo();
        try {
            ti.setToken(iam.getUserToken(getUserId()));
            ti.setSubject(iam.getTokenSubject(
                    serviceContext.getCompanyId(), getUserId()));
        } catch (Exception e) {
            ti.setError("User wiht id '" + getUserId()
                    + "' has not access token");
        }
        return ti;
    }

    /**
     * Retrieves the token for the user.
     *
     * @param userId The user identifier
     * @param serviceContext The service context of the call
     * @return The token info containing the token
     * @throws PortalException If there are problem to collect the information
     */
    public final TokenInfo getToken(
            final long userId, final ServiceContext serviceContext)
            throws PortalException {
        TokenAccessPermissionChecker.check(getPermissionChecker(), userId,
                serviceContext.getScopeGroupId(), "VIEW");
        TokenInfo ti = new TokenInfo();
        try {
            ti.setToken(iam.getUserToken(userId));
            ti.setSubject(iam.getTokenSubject(
                    serviceContext.getCompanyId(), userId));
        } catch (Exception e) {
            ti.setError("User with id '" + userId
                    + "' has not access token");
        }
        return ti;
    }

    /**
     * Retrieves the token for the provided subject.
     *
     * @param subject The global user identifier from IAM
     * @param serviceContext The service context of the call
     * @return The token info containing the token
     * @throws PortalException If there are problem to collect the information
     */
    public final TokenInfo getToken(
            final String subject, final ServiceContext serviceContext)
            throws PortalException {
        TokenInfo ti = new TokenInfo();
        ti.setSubject(subject);
        try {
            User user = iam.getUserBySubject(
                    serviceContext.getCompanyId(), subject);
            TokenAccessPermissionChecker.check(
                    getPermissionChecker(), user.getUserId(),
                    serviceContext.getScopeGroupId(), "VIEW");
            ti.setToken(iam.getUserToken(user.getUserId()));
        } catch (Exception e) {
            ti.setError("User with subject '" + subject
                    + "' has not access token");
        }
        return ti;
    }

    /**
     * Retrieves the information associated with a token.
     * If the token is not valid an error message is included in the token
     * information and not other values are provided
     *
     * @param token The token to analyse
     * @param serviceContext The service context of the call
     * @return The token information
     * @throws PortalException If there are problem to collect the information
     */
    public final TokenInfo getTokenInfo(
            final String token, final ServiceContext serviceContext)
            throws PortalException {
        TokenAccessPermissionChecker.check(getPermissionChecker(), getUserId(),
                serviceContext.getScopeGroupId(), "TOKEN_INFO");
        TokenInfo ti = new TokenInfo();
        ti.setToken(token);
        try {
            User user = iam.getTokenUser(serviceContext.getCompanyId(), token);
            if (user != null) {
                ti.setSubject(iam.getTokenSubject(serviceContext
                        .getCompanyId(), user.getUserId()));
                List<String> ugList = new ArrayList<>();

                for (UserGroup ug : user.getUserGroups()) {
                    if (ug.getName().startsWith(
                            IAMConfigurationKeys.GROUP_PREFIX)) {
                        ugList.add(ug.getName().substring(
                                IAMConfigurationKeys.GROUP_PREFIX.length()));
                    }
                }
                ti.setGroups(ugList);
            } else {
                Map<String, String> userInfo = iam.getTokenUserInfo(
                        serviceContext.getCompanyId(), token);
                ti.setSubject(userInfo.get("subject"));
                ti.setGroups(Arrays.asList(userInfo.get("groups").split(",")));
            }
        } catch (Exception e) {
            ti.setError("Token '" + token + "' is not valid");
        }
        return ti;
    }

    /**
     * Reference to the OSGi IAM service.
     */
    @ServiceReference(type = com.liferay.portal.security.sso.iam.IAM.class)
    private IAM iam;
}
