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

package com.liferay.portal.security.sso.iam.internal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.iam.IAM;
import com.liferay.portal.security.sso.iam.configuration.IAMConfiguration;
import com.liferay.portal.security.sso.iam.constants.IAMConfigurationKeys;
import com.liferay.portal.security.sso.iam.constants.IAMConstants;
import com.liferay.portal.security.sso.iam.constants.IAMWebKeys;
import com.liferay.portal.security.sso.iam.internal.util.IAMEndPoints;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.RefreshTokenGrant;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.SerializeException;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.UserInfoErrorResponse;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

/**
 * Implementation of IAM method.
 *
 * This class perform all the interaction with the IAM service.
 *
 * @author Marco Fargetta
 */
@Component(
        configurationPid =
          "com.liferay.portal.security.sso.iam.configuration.IAMConfiguration",
        immediate = true,
        service = IAM.class)
public class IAMImpl implements IAM {

    @Override
    public final User addOrUpdateUser(
            final HttpSession session, final long companyId,
            final String authorizationCode, final String returnRequestUri,
            final List<String> scopes) throws Exception {
        log.debug("Add or update a new user");
        IAMEndPoints iamEP = new IAMEndPoints(getIAMConfiguration(companyId));
        AuthorizationCode authCode = new AuthorizationCode(authorizationCode);

        TokenRequest tokenReq = new TokenRequest(iamEP.getTokenURI(),
                new ClientSecretBasic(iamEP.getClientID(), iamEP.getSecret()),
                new AuthorizationCodeGrant(authCode, new URI(
                        returnRequestUri)));

        HTTPResponse tokenHTTPResp = null;
        try {
            log.debug("Token request header content: " + tokenReq
                    .toHTTPRequest().getHeader("Content-Type"));
            log.debug("Token request header authorisation: " + tokenReq
                    .toHTTPRequest().getHeader("Authorization"));
            log.debug("Token request query: " + tokenReq.toHTTPRequest()
                    .getQuery());
            tokenHTTPResp = tokenReq.toHTTPRequest().send();
        } catch (SerializeException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }

        TokenResponse tokenResp = null;
        try {
            log.debug("Token response: " + tokenHTTPResp.getContent());
            tokenResp = OIDCTokenResponseParser.parse(tokenHTTPResp);
        } catch (ParseException ex) {
            log.error(ex);
        }

        if (tokenResp == null || tokenResp instanceof TokenErrorResponse) {
            throw new AuthException(
                    "OpenId Connect server does not authenticate");
        }

        OIDCTokenResponse oidcTokenResponse = (OIDCTokenResponse) tokenResp;

        try {
            log.debug("Token response: " + oidcTokenResponse.toJSONObject()
                    .toString());
            IDTokenClaimsSet idtcs = iamEP.getValidator().validate(
                    oidcTokenResponse.getOIDCTokens().getIDToken(), null);
            log.debug("IDToken claims" + idtcs.toJSONObject().toJSONString());
        } catch (BadJOSEException bjse) {
            log.error("Invalid token detected: " + bjse.getMessage());
            throw bjse;
        }
        UserInfoRequest userInfoReq = new UserInfoRequest(iamEP.getUserInfo(),
                oidcTokenResponse.getOIDCTokens().getBearerAccessToken());

        HTTPResponse userInfoHTTPResp = null;
        try {
            userInfoHTTPResp = userInfoReq.toHTTPRequest().send();
        } catch (SerializeException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }
        UserInfoResponse userInfoResp = null;
        try {
            userInfoResp = UserInfoResponse.parse(userInfoHTTPResp);
        } catch (ParseException ex) {
            log.error(ex);
        }

        if (userInfoResp == null
                || userInfoResp instanceof UserInfoErrorResponse) {
            throw new AuthException(
                    "OpenId Connect server does not authenticate");
        }
        UserInfoSuccessResponse successUserResponse =
                (UserInfoSuccessResponse) userInfoResp;
        log.debug("User Info Details: " + successUserResponse.getUserInfo()
                .toJSONObject().toJSONString());
        return doAddOrUpdateUser(session, companyId, successUserResponse
                .getUserInfo(), oidcTokenResponse.getOIDCTokens()
                        .getBearerAccessToken(), oidcTokenResponse
                                .getOIDCTokens().getRefreshToken());
    }

    @Override
    public final String getLoginRedirect(final long companyId,
            final String returnRequestUri, final List<String> scopes,
            final boolean isRefreshTokenRequested)
            throws Exception {
        IAMConfiguration iamConf = getIAMConfiguration(companyId);
        IAMEndPoints iamEP = new IAMEndPoints(iamConf);
        String fullScopes = StringUtil.merge(scopes, " ") + " " + iamConf
                .oauthExtraScopes();
        if (isRefreshTokenRequested) {
            fullScopes = fullScopes.concat(" offline_access");
        }
        AuthenticationRequest aReq = new AuthenticationRequest(iamEP
                .getAuthURI(), new ResponseType(ResponseType.Value.CODE), Scope
                        .parse(fullScopes), new ClientID(iamConf.appId()),
                new URI(returnRequestUri), new State(), new Nonce());
        log.debug("Generated IAM authentication request at " + aReq.toURI()
                .toString());
        return aReq.toURI().toString();
    }

    @Override
    public final boolean isEnabled(final long companyId) {
        IAMConfiguration iamConfiguration = getIAMConfiguration(companyId);
        if (Validator.isNull(iamConfiguration.appId()) || Validator.isNull(
                iamConfiguration.appSecret())) {
            return false;
        }

        return iamConfiguration.enabled();
    }

    @Override
    public final String getUserToken(final long userId) throws Exception {
        User user = userLocalService.getUser(userId);
        String token;
        ExpandoValue accessToken = expandoValueLocalService.getValue(user
                .getCompanyId(), User.class.getName(),
                ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamAccessToken",
                user.getUserId());
        token = accessToken.getData();
        log.debug("Requested the token for the user " + userId + " and it is "
                + token);
        if (!isValidToken(user.getCompanyId(), token)) {
            token = updateUserToken(user);
        }
        return token;
    }

    @Override
    public final boolean hasRefreshToken(final User user) {
        ExpandoValue refreshToken = expandoValueLocalService.getValue(user
                .getCompanyId(), User.class.getName(),
                ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamRefreshToken",
                user.getUserId());
        log.debug("User has the refresh token: " + refreshToken);
        return (Validator.isNotNull(refreshToken) && Validator.isNotNull(
                refreshToken.getData()));
    }

    /**
     * @see com.liferay.portal.security.sso.iam.IAM#getTokenUser(long,
     *      java.lang.String)
     */
    @Override
    public final User getTokenUser(final long companyId, final String token)
            throws Exception {
        if (!isValidToken(companyId, token)) {
            return null;
        }
        ExpandoColumn column = ExpandoColumnLocalServiceUtil
                .getDefaultTableColumn(companyId, User.class.getName(),
                        "iamAccessToken");
        DynamicQuery userDynamicQuery = DynamicQueryFactoryUtil.forClass(
                ExpandoValue.class, PortalClassLoaderUtil.getClassLoader())
                .add(PropertyFactoryUtil.forName("columnId").eq(GetterUtil
                        .getLong(column.getColumnId()))).add(
                                PropertyFactoryUtil.forName("data").eq(token))
                .add(PropertyFactoryUtil.forName("classNameId").eq(GetterUtil
                        .getLong(ClassNameLocalServiceUtil.getClassNameId(
                                User.class.getName()))));
        List<ExpandoValue> expandoList = expandoValueLocalService
                .dynamicQuery(userDynamicQuery);

        if (expandoList.size() == 1) {
            long userId = expandoList.get(0).getClassPK();
            return UserLocalServiceUtil.getUserById(userId);
        }
        return null;
    }

    @Override
    public final Map<String, String> getTokenUserInfo(
            final long companyId, final String token) throws Exception {
        log.debug("User information retrieved from IAM");
        IAMEndPoints iamEP = new IAMEndPoints(getIAMConfiguration(companyId));
        UserInfoRequest userInfoReq = new UserInfoRequest(iamEP.getUserInfo(),
                new BearerAccessToken(token));
        HTTPResponse userInfoHTTPResp = null;
        try {
            userInfoHTTPResp = userInfoReq.toHTTPRequest().send();
        } catch (SerializeException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }
        UserInfoResponse userInfoResp = null;
        try {
            userInfoResp = UserInfoResponse.parse(userInfoHTTPResp);
        } catch (ParseException ex) {
            log.error(ex);
        }

        if (userInfoResp == null
                || userInfoResp instanceof UserInfoErrorResponse) {
            throw new AuthException(
                    "OpenId Connect server does not authenticate");
        }
        UserInfoSuccessResponse successUserResponse =
                (UserInfoSuccessResponse) userInfoResp;
        UserInfo uInfo = successUserResponse.getUserInfo();
        List<String> lstGroups = uInfo.getStringListClaim("groups");
        Map<String, String> finalInfo = new HashMap<>();
        finalInfo.put("groups", StringUtils.join(lstGroups, ","));
        finalInfo.put("subject", uInfo.getSubject().getValue());
        return finalInfo;
    }


    /**
     * @see com.liferay.portal.security.sso.iam.IAM#getTokenSubject(long,
     *      java.lang.String)
     */
    @Override
    public final String getTokenSubject(
            final long companyId, final String token) throws Exception {
        User user = getTokenUser(companyId, token);
        if (user == null) {
            return null;
        }
        return getTokenSubject(companyId, user.getUserId());
    }

    /**
     * @see com.liferay.portal.security.sso.iam.IAM#getTokenSubject(long, long)
     */
    @Override
    public final String getTokenSubject(final long companyId, final long userId)
            throws Exception {
        ExpandoValue subjectToken = expandoValueLocalService.getValue(
                companyId, User.class.getName(),
                ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamUserID", userId);
        if (subjectToken == null) {
            return null;
        }
        return subjectToken.getData();
    }

    /**
     * @see com.liferay.portal.security.sso.iam.IAM#getUserBySubject(String)
     */
    @Override
    public final User getUserBySubject(
            final long companyId, final String subject) throws Exception {
        ExpandoColumn column = ExpandoColumnLocalServiceUtil
                .getDefaultTableColumn(companyId, User.class.getName(),
                        "iamUserID");
        DynamicQuery userDynamicQuery = DynamicQueryFactoryUtil.forClass(
                ExpandoValue.class, PortalClassLoaderUtil.getClassLoader())
                .add(PropertyFactoryUtil.forName("columnId").eq(GetterUtil
                        .getLong(column.getColumnId()))).add(
                                PropertyFactoryUtil.forName("data").eq(subject))
                .add(PropertyFactoryUtil.forName("classNameId").eq(GetterUtil
                        .getLong(ClassNameLocalServiceUtil.getClassNameId(
                                User.class.getName()))));
        List<ExpandoValue> expandoList = expandoValueLocalService
                .dynamicQuery(userDynamicQuery);

        if (expandoList.size() == 1) {
            long userId = expandoList.get(0).getClassPK();
            return UserLocalServiceUtil.getUserById(userId);
        }
        if (expandoList.size() > 1) {
            log.error("Subject '" + subject + "' associated to multiple users");
            throw new Exception("Subject associated to multiple users.");
        }
        return null;
    }

    /**
     * Retrieves the configuration of the IAM endpoint.
     *
     * @param companyId The portal company id
     * @return The iam configuration
     */
    private IAMConfiguration getIAMConfiguration(final long companyId) {
        try {
            return configurationProvider.getConfiguration(
                    IAMConfiguration.class, new CompanyServiceSettingsLocator(
                            companyId, IAMConstants.SERVICE_NAME));
        } catch (ConfigurationException ce) {
            throw new SystemException(ce);
        }
    }

    /**
     * Add a new user into the portal DB.
     * Add a new user but if the user already exist the information are
     * updated with the one provided by IAM.
     *
     * @param session Active portal session
     * @param companyId The company id
     * @param userInfo The user information
     * @param bearerAccessToken The new access token
     * @param refreshToken The refresh token. Could be null
     * @return The user
     * @throws Exception If the user information are not valid or the portal
     * DB cannot be updated
     */
    private User doAddOrUpdateUser(final HttpSession session,
            final long companyId, final UserInfo userInfo,
            final BearerAccessToken bearerAccessToken,
            final RefreshToken refreshToken) throws Exception {

        session.setAttribute(IAMWebKeys.IAM_USER_ID, userInfo.getSubject()
                .getValue());
        long classNameId = ClassNameLocalServiceUtil.getClassNameId(
                User.class);
        List<ExpandoValue> values = expandoValueLocalService
                .getColumnValues(companyId, classNameId,
                        ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamUserID",
                        userInfo.getSubject().getValue(), -1, -1);
        if (values.size() > 1) {
            throw new AuthException(
                    "Multiple user with the same IAM identifier");
        }
        if (values.size() == 1) {
            return updateUser(companyId, userLocalService.getUser(values.get(0)
                    .getClassPK()), userInfo, bearerAccessToken, refreshToken);
        }
        try {
            User user = userLocalService.getUserByEmailAddress(companyId,
                    userInfo.getEmail().toString());
            session.setAttribute(IAMWebKeys.IAM_USER_EMAIL_ADDRESS, userInfo
                    .getEmail().toString());
            return updateUser(companyId, user, userInfo, bearerAccessToken,
                    refreshToken);
        } catch (PortalException pe) {
            log.debug("No user found. It will be added");
        }
        return addUser(companyId, userInfo, bearerAccessToken, refreshToken);
    }

    /**
     * Add a new user.
     *
     * @param companyId The company id
     * @param userInfo The user information
     * @param bearerAccessToken The new access token
     * @param refreshToken The refresh token. Could be null
     * @return The new user
     * @throws Exception If the user cannot be created
     */
    private User addUser(final long companyId, final UserInfo userInfo,
            final BearerAccessToken bearerAccessToken,
            final RefreshToken refreshToken)
            throws Exception {
        long creatorUserId = 0;
        boolean autoPassword = true;
        String password1 = StringPool.BLANK;
        String password2 = StringPool.BLANK;
        boolean autoScreenName = true;
        String screenName = StringPool.BLANK;
        String emailAddress = userInfo.getEmail().toString();
        String openId = StringPool.BLANK;
        Locale locale = LocaleUtil.getDefault();
        long prefixId = 0;
        long suffixId = 0;
        boolean male = Validator.equals(userInfo.getGender(), "male");
        String jobTitle = StringPool.BLANK;
        long[] groupIds = null;
        long[] organizationIds = null;
        long[] roleIds = null;
        long[] userGroupIds = getUserGroupIds(companyId,
                userInfo.getStringListClaim("groups"));
        boolean sendEmail = true;

        Calendar birth = new GregorianCalendar();
        birth.setTime(new Date(0));
        String firstName = userInfo.getGivenName();
        if (Validator.isNull(firstName)) {
            if (Validator.isNotNull(userInfo.getName())) {
                String[] fullName = userInfo.getName().split(" ");
                firstName = fullName[0];
            } else {
                firstName = userInfo.getSubject().getValue().substring(0,
                        userInfo.getSubject().getValue().indexOf("-"))
                        + "...";
            }
        }
        String middleName = StringPool.BLANK;
        String lastName = userInfo.getFamilyName();
        if (Validator.isNull(lastName)) {
            if (Validator.isNotNull(userInfo.getName())) {
                String[] fullName = userInfo.getName().split(" ");
                firstName = fullName[fullName.length - 1];
            } else {
                lastName = "IAM User";
            }
        }

        if (Validator.isNotNull(userInfo.getPreferredUsername())) {
            try {
                userLocalService.getUserByScreenName(companyId, userInfo
                        .getPreferredUsername());
            } catch (PortalException pe) {
                autoScreenName = false;
                screenName = userInfo.getPreferredUsername();
            }
        }

        ServiceContext serviceContext = new ServiceContext();
        log.debug("New user with mail " + emailAddress + " to be added");
        User user = userLocalService.addUser(creatorUserId, companyId,
                autoPassword, password1, password2, autoScreenName, screenName,
                emailAddress, 0, openId, locale, firstName, middleName,
                lastName, prefixId, suffixId, male,
                birth.get(Calendar.MONTH), birth.get(Calendar.DAY_OF_MONTH),
                birth.get(Calendar.YEAR), jobTitle, groupIds, organizationIds,
                roleIds, userGroupIds, sendEmail, serviceContext);

        user = userLocalService.updatePasswordReset(user.getUserId(), false);

        user = userLocalService.updateEmailAddressVerified(user.getUserId(),
                true);

        user = userLocalService.updateAgreedToTermsOfUse(user.getUserId(),
                true);

        user = userLocalService.updateReminderQuery(user.getUserId(),
                "IAM Authentication", "NOT REQUESTED");

        expandoValueLocalService.addValue(companyId, User.class.getName(),
                ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamUserID", user
                        .getUserId(), userInfo.getSubject().getValue());
        expandoValueLocalService.addValue(companyId, User.class.getName(),
                ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamAccessToken",
                user.getUserId(), bearerAccessToken.getValue());
        if (Validator.isNotNull(refreshToken)) {
            expandoValueLocalService.addValue(companyId, User.class
                    .getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME,
                    "iamRefreshToken", user.getUserId(), refreshToken
                            .getValue());
        }
        log.info("New user with id '" + user.getUserId() + "' added");
        return user;
    }

    /**
     * Retrieves the user group ids.
     * Searches the id for the groups provided by IAM. If a group is not
     * present then it is created and the id retrieved.
     *
     * @param companyId The company id
     * @param listGroups The list of group
     * @return An array of group ids
     */
    private long[] getUserGroupIds(final long companyId,
            final List<String> listGroups) {
        if (Validator.isNull(listGroups) || listGroups.isEmpty()) {
            return null;
        }
        List<Long> groupIds = new LinkedList<>();
        for (String gr: listGroups) {
            String groupName = IAMConfigurationKeys.GROUP_PREFIX
                    + gr;

            UserGroup lifeUG = null;
            ServiceContext serviceContext = new ServiceContext();
            try {
                lifeUG = userGroupLocalService.getUserGroup(companyId,
                        groupName);
            } catch (PortalException pe) {
                try {
                    lifeUG = userGroupLocalService.addUserGroup(
                            userLocalService.getDefaultUserId(companyId),
                            companyId, groupName, "IAM Provided group",
                            serviceContext);
                    log.info("Created the group: " + groupName);
                } catch (Exception pe2) {
                    log.error("Impossible update group: " + groupName);
                    continue;
                }
            }
            groupIds.add(lifeUG.getUserGroupId());
        }
        if (groupIds.isEmpty()) {
            return null;
        }
        long[] realGroups = ArrayUtils.toPrimitive(groupIds.toArray(
                new Long[groupIds.size()]));
        return realGroups;
    }

    /**
     * Update the user.
     *
     * @param companyId The company id
     * @param user The user to update
     * @param userInfo The new user information
     * @param bearerAccessToken The access token
     * @param refreshToken The refresh token
     * @return The updated user
     * @throws Exception User cannot be updated
     */
    private User updateUser(final long companyId, final User user,
            final UserInfo userInfo, final BearerAccessToken bearerAccessToken,
            final RefreshToken refreshToken)
            throws Exception {

        String emailAddress = userInfo.getEmail().toString();
        String firstName = userInfo.getName();
        String lastName = userInfo.getFamilyName();
        boolean male = Validator.equals(userInfo.getGender(), "male");

        expandoValueLocalService.addValue(companyId, User.class.getName(),
                ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamUserID", user
                        .getUserId(), userInfo.getSubject().getValue());
        expandoValueLocalService.addValue(companyId, User.class.getName(),
                ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamAccessToken",
                user.getUserId(), bearerAccessToken.getValue());
        if (Validator.isNotNull(refreshToken)) {
            expandoValueLocalService.addValue(companyId, User.class
                    .getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME,
                    "iamRefreshToken", user.getUserId(), refreshToken
                            .getValue());
        }

        Contact contact = user.getContact();

        Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

        birthdayCal.setTime(contact.getBirthday());

        int birthdayMonth = birthdayCal.get(Calendar.MONTH);
        int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
        int birthdayYear = birthdayCal.get(Calendar.YEAR);

        long[] groupIds = null;
        long[] organizationIds = null;
        long[] roleIds = null;
        List<UserGroupRole> userGroupRoles = null;
        long[] userGroupIds = getUserGroupIds(companyId,
                userInfo.getStringListClaim("groups"));
        ServiceContext serviceContext = new ServiceContext();

        if (!StringUtil.equalsIgnoreCase(emailAddress, user
                .getEmailAddress())) {

            userLocalService.updateEmailAddress(user.getUserId(),
                    StringPool.BLANK, emailAddress, emailAddress);
        }

        userLocalService.updateEmailAddressVerified(user.getUserId(), true);

        return userLocalService.updateUser(user.getUserId(), StringPool.BLANK,
                StringPool.BLANK, StringPool.BLANK, false,
                user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
                user.getScreenName(), emailAddress, 0, user.getOpenId(), true,
                null, user.getLanguageId(), user.getTimeZoneId(),
                user.getGreeting(), user.getComments(), firstName,
                user.getMiddleName(), lastName, contact.getPrefixId(),
                contact.getSuffixId(), male, birthdayMonth, birthdayDay,
                birthdayYear, contact.getSmsSn(), contact.getFacebookSn(),
                contact.getJabberSn(), contact.getSkypeSn(),
                contact.getTwitterSn(), contact.getJobTitle(), groupIds,
                organizationIds, roleIds, userGroupRoles, userGroupIds,
                serviceContext);
    }

    /**
     * Update the user access token.
     * The user token is updated using the refresh token.
     *
     * @param user The user requiring to update the access token
     * @return The new access token
     * @throws Exception If the new token cannot be retrieved
     */
    private String updateUserToken(final User user) throws Exception {
        ExpandoValue refreshTokenEV = expandoValueLocalService.getValue(
                user.getCompanyId(), User.class.getName(),
                ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamRefreshToken",
                user.getUserId());
        RefreshToken refreshToken = new RefreshToken(refreshTokenEV.getData());
        AuthorizationGrant refreshTokenGrant = new RefreshTokenGrant(
                refreshToken);
        IAMEndPoints iamEP;
        try {
            iamEP = new IAMEndPoints(getIAMConfiguration(user.getCompanyId()));
        } catch (ConfigurationException e) {
            log.error(e);
            return null;
        }
        ClientAuthentication clientAuth = new ClientSecretBasic(iamEP
                .getClientID(), iamEP.getSecret());
        TokenRequest request = new TokenRequest(iamEP.getTokenURI(),
                clientAuth, refreshTokenGrant);
        TokenResponse response;
        try {
            response = TokenResponse.parse(request.toHTTPRequest().send());
        } catch (ParseException | IOException e) {
            log.error(
                    "Impossible to parse the token endpoint response to "
                    + "update the token");
            return null;
        }
        if (!response.indicatesSuccess()) {
            TokenErrorResponse errorResponse = (TokenErrorResponse) response;
            log.error("Refresh token error: \n" + errorResponse.toJSONObject()
                    .toString());
            try {
                expandoValueLocalService.deleteValue(
                        refreshTokenEV.getValueId());
            } catch (PortalException pe) {
                log.error("The refresh token does not work and cannot be "
                        + "removed to be renewed");
            }
            throw new PortalException("Unable to retrieve the access token");
        }
        AccessTokenResponse successResponse = (AccessTokenResponse) response;
        AccessToken accessToken = successResponse.getTokens().getAccessToken();
        try {
            expandoValueLocalService.addValue(user.getCompanyId(),
                    User.class.getName(),
                    ExpandoTableConstants.DEFAULT_TABLE_NAME, "iamAccessToken",
                    user.getUserId(), accessToken.getValue());
        } catch (PortalException e) {
            log.error("Impossible to store the updated access token "
                    + "for the user '" + user.getScreenName() + "'");
        }

        return accessToken.getValue();
    }

    /**
     * Checks if the access token is valid.
     *
     * @param companyId The company id
     * @param token The token to validate
     * @return True if valid, false otherwise
     */
    private boolean isValidToken(final long companyId, final String token) {
        IAMEndPoints iamEP = null;
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor =
                new DefaultJWTProcessor<SecurityContext>();
        JWKSource<SecurityContext> keySource;
        try {
            iamEP = new IAMEndPoints(getIAMConfiguration(companyId));
            keySource = new RemoteJWKSet<SecurityContext>(iamEP.getJwkURI()
                    .toURL());
        } catch (MalformedURLException | ConfigurationException mue) {
            log.error("Impossible to access the jwk key");
            return false;
        }
        jwtProcessor.setJWSKeySelector(
                new JWSVerificationKeySelector<SecurityContext>(
                        JWSAlgorithm.RS256, keySource));
        JWTClaimsSet claimsSet;
        try {
            claimsSet = jwtProcessor.process(token, null);
        } catch (java.text.ParseException | BadJOSEException
                | JOSEException e) {
            log.error("The following token cannot be parsed: " + token);
            return false;
        }
        return claimsSet.getExpirationTime().after(
                new Date(System.currentTimeMillis()
                        + IAMConfigurationKeys.MINIMUM_TOKEN_LIFETIME));
    }

    /**
     * Reference to the configuration manager.
     */
    @Reference
    private ConfigurationProvider configurationProvider;

    /**
     * Reference to the user service.
     */
    @Reference
    private UserLocalService userLocalService;

    /**
     * Reference to the user group service.
     */
    @Reference
    private UserGroupLocalService userGroupLocalService;

    /**
     * Reference to the expando value service.
     */
    @Reference
    private ExpandoValueLocalService expandoValueLocalService;

    /**
     * Logger.
     */
    private final Log log = LogFactoryUtil.getLog(IAMImpl.class);
}
