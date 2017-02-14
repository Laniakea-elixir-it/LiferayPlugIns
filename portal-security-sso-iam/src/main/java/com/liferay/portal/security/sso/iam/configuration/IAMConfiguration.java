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

package com.liferay.portal.security.sso.iam.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations
    .ExtendedObjectClassDefinition;


/**
 * @author Marco Fargetta
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
        id =
          "com.liferay.portal.security.sso.iam.configuration.IAMConfiguration",
        localization = "content/Language", name = "iam.configuration.name")
public interface IAMConfiguration {

    /**
     * Checks if the IAM authentication is enabled.
     *
     * @return True if enabled, false otherwise
     */
    @Meta.AD(deflt = "false", required = false)
    boolean enabled();

    /**
     * Checks if the verified account is required.
     *
     * @return True if required, false otherwise
     */
    @Meta.AD(
            deflt = "false",
            name = "%require-verified-account",
            required = false)
    boolean verifiedAccountRequired();

    /**
     * Retrieves the application id.
     *
     * @return The application id
     */
    @Meta.AD(name = "%application-id", required = false)
    String appId();

    /**
     * Retrieve the application secret.
     *
     * @return The application secret
     */
    @Meta.AD(name = "%application-secret", required = false)
    String appSecret();

    /**
     * Retrieves the oAuth configuration URL.
     *
     * @return The configuration URL
     */
    @Meta.AD(
            deflt =
             "https://iam.indigo-datacloud.eu/.well-known/openid-configuration",
            name = "%configuration-url",
            required = false)
    String configurationURL();

    /**
     * Retrieves the oAuth authentication URL.
     *
     * @return The authentication URL
     */
    @Meta.AD(
            deflt = "https://iam.indigo-datacloud.eu/oauth2-as/oauth2-authz",
            name = "%oauth-authentication-url",
            required = false)
    String oauthAuthURL();

    /**
     * Retrieves the oAuth token URL.
     *
     * @return The token URL
     */
    @Meta.AD(
            deflt = "https://iam.indigo-datacloud.eu/oauth2/token",
            name = "%oauth-token-url",
            required = false)
    String oauthTokenURL();

    /**
     * Retrieves the user info URL.
     *
     * @return The user info URL
     */
    @Meta.AD(
            deflt = "https://iam.indigo-datacloud.eu/oauth2/userinfo",
            name = "%openid-userinfo-url",
            required = false)
    String openidUserinfoURL();

    /**
     * Retrieves the JWK URL.
     *
     * @return The JWK URL
     */
    @Meta.AD(
            deflt = "https://iam.indigo-datacloud.eu/oauth2/jwk",
            name = "%openid-jwk-url",
            required = false)
    String openidJwkURL();

    /**
     * Retrieves the OpenId issuer.
     *
     * @return The OpenId isser
     */
    @Meta.AD(
            name = "%openid-issuer", required = false)
    String openidIssuer();

    /**
     * Retrieves the OpenId extra scopes.
     *
     * @return The OpenId extra scopes
     */
    @Meta.AD(
            name = "%oauth-extra-scopes", required = false)
    String oauthExtraScopes();
}
