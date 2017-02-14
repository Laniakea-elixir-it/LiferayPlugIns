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

package com.liferay.portal.security.sso.iam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.liferay.portal.kernel.model.User;

/**
 * IAM component used by all IAM related plug-in to access user information.
 *
 * @author Marco Fargetta
 */
public interface IAM {
    /**
     * Add or update user.
     *
     * Using the authorisation code released by IAM retrieves the id token, the
     * access token and eventually the refresh token. Then it connect with the
     * user info endpoint to get user information and create the account or
     * liferay, or if the user exist the related information are updated.
     *
     * @param session
     *            Current session
     * @param companyId
     *            Company Id
     * @param authorizationCode
     *            IAM authorisation code
     * @param returnRequestUri
     *            URI where the user is redirect after the authentication
     * @param scopes
     *            The list of requested scopes
     *
     * @return The user
     * @throws Exception
     *             If there are problem to authenticate or identify the user
     */
    User addOrUpdateUser(HttpSession session, long companyId,
            String authorizationCode, String returnRequestUri,
            List<String> scopes) throws Exception;

    /**
     * Get the IAM URL where the user has to be re-direct
     * for the authentication.
     *
     * @param companyId
     *            Company Id
     * @param returnRequestUri
     *            URI where the user is redirect after the authentication
     * @param scopes
     *            The list of requested scopes
     * @param isRefreshTokenRequested
     *            True if the associated user has not a refresh token, false
     *            otherwise
     * @return The URL
     * @throws Exception
     *             If there is a configuration problem
     */
    String getLoginRedirect(long companyId, String returnRequestUri,
            List<String> scopes, boolean isRefreshTokenRequested)
            throws Exception;

    /**
     * Verifies if the IAM authentication is enabled.
     *
     * @param companyId
     *            Company Id
     * @return True if enabled, false otherwise
     */
    boolean isEnabled(long companyId);

    /**
     * Verifies if the user has a refresh token.
     *
     * @param user
     *            The user
     * @return True if present, false otherwise
     */
    boolean hasRefreshToken(User user);

    /**
     * Retrieves the user access token. If the token is not valid it is updated
     * using the refresh token.
     *
     * @param userId
     *            The user Id
     * @return The user access token
     * @throws Exception
     *             If the token cannot be created using the refresh token
     */
    String getUserToken(long userId) throws Exception;

    /**
     * Retrieves the liferay user. Identify the user associated with a valid
     * access token
     *
     * @param companyId
     *            Company Id
     * @param token
     *            The token
     * @return The user
     * @throws Exception
     *             No user associated with the token
     */
    User getTokenUser(long companyId, String token) throws Exception;

    /**
     * Retrieve the user information. Contact the user info end-point to get
     * the user details managed by IAM.
     *
     * @param companyId
     *            Company Id
     * @param token
     *            The token
     * @return A map of user attributes released by IAM
     * @throws Exception
     *            Token not valid
     */
    Map<String, String> getTokenUserInfo(long companyId, String token)
            throws Exception;

    /**
     * Retrieves the subject associated with the token.
     *
     * @param companyId
     *            Company Id
     * @param token
     *            The token
     * @return The subject
     * @throws Exception
     *             Impossible read the token
     */
    String getTokenSubject(long companyId, String token) throws Exception;

    /**
     * Retrieves the subject of the user.
     *
     * @param companyId
     *            Company Id
     * @param userId
     *            The user
     * @return The subject
     * @throws Exception
     *             User has not subject
     */
    String getTokenSubject(long companyId, long userId) throws Exception;

    /**
     * Retrieves the user by the subject.
     *
     * @param companyId
     *            Company Id
     * @param subject User subject
     * @return The user or null if not available
     * @throws Exception If users cannot be verified
     */
    User getUserBySubject(long companyId, String subject) throws Exception;
}
