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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.iam.configuration.IAMConfiguration;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;

/**
 * IAM endpoint manager.
 * The IAMEndPoints configuration reads the IAM end points from the
 * configuration. If the configuration URL is provided all the other URL are
 * discarded and the correct information are retrieved from the IAM service.
 *
 * @author Marco Fargetta
 */
public class IAMEndPoints {
    /**
     * Builds the end points from the configuration.
     *
     * @param iamConf The IAM configuration
     * @throws ConfigurationException The configuration is not correct or the
     * provided values are incorrect
     */
    public IAMEndPoints(final IAMConfiguration iamConf)
            throws ConfigurationException {

        try {
            oidcMeta = getMetadata(iamConf);
        } catch (Exception ex) {
            log.error("IAM Configuration URL '" + iamConf.configurationURL()
                    + "' is not reachable");
        }
        if (oidcMeta != null) {
            authURI = oidcMeta.getAuthorizationEndpointURI();
            tokenURI = oidcMeta.getTokenEndpointURI();
            jwkURI = oidcMeta.getJWKSetURI();
            userInfo = oidcMeta.getUserInfoEndpointURI();
            issuer = oidcMeta.getIssuer();
            try {
                validator = new IDTokenValidator(oidcMeta.getIssuer(),
                        new ClientID(iamConf.appId()), JWSAlgorithm.RS256,
                        oidcMeta.getJWKSetURI().toURL());
            } catch (MalformedURLException mue) {
                throw new ConfigurationException("JWK URI publication errror");
            }
        } else {
            if (Validator.isNull(iamConf.oauthAuthURL()) || Validator.isNull(
                    iamConf.oauthTokenURL()) || Validator.isNull(iamConf
                            .openidJwkURL())) {
                throw new ConfigurationException(
                        "IAM Authentication is not properly configured. "
                        + "Authentication cannot proceed");
            }
            issuer = new Issuer(iamConf.openidIssuer());
            try {
                authURI = new URI(iamConf.oauthAuthURL());
                tokenURI = new URI(iamConf.oauthTokenURL());
                jwkURI = new URI(iamConf.openidJwkURL());
                userInfo = new URI(iamConf.openidUserinfoURL());
                validator = new IDTokenValidator(issuer, new ClientID(iamConf
                        .appId()), JWSAlgorithm.RS256, jwkURI.toURL());
            } catch (URISyntaxException use) {
                throw new ConfigurationException(
                        "IAM Authentication is not properly configured. "
                        + "Authentication cannot proceed");
            } catch (MalformedURLException mue) {
                throw new ConfigurationException(
                        "JWK URL is not properly configured");
            }

        }
        clientID = new ClientID(iamConf.appId());
        secret = new Secret(iamConf.appSecret());
    }

    /**
     * Retrieves metadata from the configuration URL.
     *
     * @param iamConf IAM configuration
     * @return The metadata
     * @throws Exception If configuration URL does not work or the provided
     * information are not correct
     */
    private OIDCProviderMetadata getMetadata(final IAMConfiguration iamConf)
            throws Exception {
        if (Validator.isNull(iamConf.configurationURL())) {
            return null;
        }
        URL configurationURL = new URL(iamConf.configurationURL());
        InputStream stream = configurationURL.openStream();
        String info = null;
        try (java.util.Scanner s = new java.util.Scanner(stream)) {
            if (s.useDelimiter("\\A").hasNext()) {
                info = s.next();
            } else {
                info = "";
            }
        }
        return OIDCProviderMetadata.parse(info);
    }

    /**
     * Retrieve the OAuth metadata.
     *
     * @return The oidcMeta
     */
    public final OIDCProviderMetadata getOidcMeta() {
        return oidcMeta;
    }

    /**
     * Retrieve The OAuth token URI.
     *
     * @return The tokenURI
     */
    public final URI getTokenURI() {
        return tokenURI;
    }

    /**
     * Retrieve The OAuth JWK URI.
     *
     * @return The jwkURI
     */
    public final URI getJwkURI() {
        return jwkURI;
    }

    /**
     * Retrieve the OAuth user info URI.
     *
     * @return The userInfo
     */
    public final URI getUserInfo() {
        return userInfo;
    }

    /**
     * Retrieve the OAuth issuer.
     *
     * @return The issuer
     */
    public final Issuer getIssuer() {
        return issuer;
    }

    /**
     * Retrieve the OAuth client identifier.
     *
     * @return The clientID
     */
    public final ClientID getClientID() {
        return clientID;
    }

    /**
     * Retrieve the OAuth client secret.
     *
     * @return The secret
     */
    public final Secret getSecret() {
        return secret;
    }

    /**
     * Retrieve the OAuth token validator.
     *
     * @return The validator
     */
    public final IDTokenValidator getValidator() {
        return validator;
    }

    /**
     * Retrieve the OAuth authentication URI.
     *
     * @return The authURI
     */
    public final URI getAuthURI() {
        return authURI;
    }

    /**
     * The OAuth metadata.
     */
    private OIDCProviderMetadata oidcMeta = null;

    /**
     * The OAuth authentication URI.
     */
    private URI authURI = null;

    /**
     * The OAuth token URI.
     */
    private URI tokenURI = null;

    /**
     * The OAuth JWK URI.
     */
    private URI jwkURI = null;

    /**
     * The OAuth user info uri.
     */
    private URI userInfo = null;

    /**
     * The OAuth issuer.
     */
    private Issuer issuer = null;

    /**
     * The OAuth client identifier.
     */
    private ClientID clientID = null;

    /**
     * The OAuth client secret.
     */
    private Secret secret = null;

    /**
     * The OAuth token validator.
     */
    private IDTokenValidator validator = null;

    /**
     * The logger.
     */
    private final Log log = LogFactoryUtil.getLog(IAMEndPoints.class);
}
