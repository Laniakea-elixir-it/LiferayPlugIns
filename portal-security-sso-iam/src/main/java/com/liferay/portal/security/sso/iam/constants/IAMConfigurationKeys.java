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

package com.liferay.portal.security.sso.iam.constants;

/**
 * Configuration keys.
 *
 * @author Marco Fargetta
 */
public final class IAMConfigurationKeys {

    /**
     * Application identifier.
     */
    public static final String APP_ID = "appId";

    /**
     * Application secret.
     */
    public static final String APP_SECRET = "appSecret";

    /**
     * Application enabled.
     */
    public static final String AUTH_ENABLED = "enabled";

    /**
     * OAuth configuration URL.
     */
    public static final String CONFIGURATION_URL = "configurationURL";

    /**
     * OAuth authentication URL.
     */
    public static final String OAUTH_AUTH_URL = "oauthAuthURL";

    /**
     * OAuth token URL.
     */
    public static final String OAUTH_TOKEN_URL = "oauthTokenURL";

    /**
     * Extra scopes.
     */
    public static final String OAUTH_EXTRA_SCOPES = "oauthExtraScopes";

    /**
     * User info URL.
     */
    public static final String OPENID_USERINFO_URL = "openidUserinfoURL";

    /**
     * JWK URL.
     */
    public static final String OPENID_JWK_URL = "openidJwkURL";

    /**
     * OAuth issuer.
     */
    public static final String OPENID_ISSUER = "openidIssuer";

    /**
     * Verified account request.
     */
    public static final String VERIFIED_ACCOUNT_REQUIRED =
            "verifiedAccountRequired";

    /**
     * Prefix for the group provided by IAM.
     */
    public static final String GROUP_PREFIX = "iam-";

    /**
     * Minimum default token lifetime to be valid.
     * Value is milliseconds and it is 3 minutes.
     */
    public static final long MINIMUM_TOKEN_LIFETIME = 180000L;

    /**
     * Default constructor to avoid instances.
     */
    private IAMConfigurationKeys() {
    }
}
