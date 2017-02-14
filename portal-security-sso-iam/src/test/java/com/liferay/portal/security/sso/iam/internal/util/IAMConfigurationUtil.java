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

package com.liferay.portal.security.sso.iam.internal.util;

import java.io.File;

import com.liferay.portal.security.sso.iam.configuration.IAMConfiguration;

/**
 * @author Marco Fargetta
 *
 */
public class IAMConfigurationUtil implements IAMConfiguration {

    /**
     * Build a IAM configuration.
     *
     * @param isGlobalConfigurationAvailable Provide a valid configuration URL
     */
    public IAMConfigurationUtil(final boolean isGlobalConfigurationAvailable) {
        globalConfigurationAvailable = isGlobalConfigurationAvailable;
    }

    @Override
    public final boolean enabled() {
        return IAM_ENABLED;
    }

    @Override
    public final boolean verifiedAccountRequired() {
        return VERIFIED_ACCOUNT;
    }

    @Override
    public final String appId() {
        return APP_ID;
    }

    @Override
    public final String appSecret() {
        return APP_SECRET;
    }

    @Override
    public final String configurationURL() {
        if (globalConfigurationAvailable) {
            File fi = new File(CONFIGURATION_FILE);
            return "file://" + fi.getAbsolutePath();
        }
        return null;
    }

    @Override
    public final String oauthAuthURL() {
        if (globalConfigurationAvailable) {
            return null;
        }
        return AUTH_URL;
    }

    @Override
    public final String oauthTokenURL() {
        return TOKEN_URL;
    }

    @Override
    public final String openidUserinfoURL() {
        return USER_INFO_URL;
    }

    @Override
    public final String openidJwkURL() {
        return JWK_URL;
    }

    @Override
    public final String openidIssuer() {
        return ISSUER;
    }

    @Override
    public final String oauthExtraScopes() {
        return EX_SCOPES;
    }

    /**
     * Fake application id.
     */
    public static final String APP_ID = "myAppId";

    /**
     * Fake application secret.
     */
    public static final String APP_SECRET = "myAppSecret";

    /**
     * Configuration file for IAM.
     * The values in this file are retrieved from IAM test instance at:
     * https://iam-test.indigo-datacloud.eu/.well-known/openid-configuration
     */
    public static final String CONFIGURATION_FILE =
            "config/iam/openid-configuration";

    /**
     * IAM Enabled check.
     */
    public static final boolean IAM_ENABLED = true;

    /**
     * Authorisation URL as in IAM test configuration.
     */
    public static final String AUTH_URL =
            "https://iam-test.indigo-datacloud.eu/authorize";

    /**
     * Extra scope could be requested.
     */
    public static final String EX_SCOPES = null;

    /**
     * Token URL as in IAM test configuration.
     */
    public static final String TOKEN_URL =
            "https://iam-test.indigo-datacloud.eu/token";

    /**
     * Issuer as in IAM test configuration.
     */
    public static final String ISSUER = "https://iam-test.indigo-datacloud.eu/";

    /**
     * JWK URL as in IAM test configuration.
     */
    public static final String JWK_URL =
            "https://iam-test.indigo-datacloud.eu/jwk";

    /**
     * User info URL as in IAM test configuration.
     */
    public static final String USER_INFO_URL =
            "https://iam-test.indigo-datacloud.eu/userinfo";

    /**
     * Verified account check.
     */
    public static final boolean VERIFIED_ACCOUNT = false;

    /**
     * Use of configuration file.
     * If true the configuration URL is returned and authorisation URL is null,
     * if false is the opposite.
     */
    private boolean globalConfigurationAvailable;
}
