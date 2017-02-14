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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * IAM endpoint manager. The IAMEndPoints configuration reads the IAM end points
 * from the configuration. If the configuration URL is provided all the other
 * URL are discarded and the correct information are retrieved from the IAM
 * service.
 *
 * @author Marco Fargetta
 */
public class IAMEndPointsTest {

    /**
     * Test method for
     *  {@link com.liferay.portal.security.sso.iam.internal.util.
     *  IAMEndPoints#IAMEndPoints(
     *  com.liferay.portal.security.sso.iam.configuration.IAMConfiguration)}.
     */
    @Test
    public final void testIAMEndPoints() {
        IAMEndPoints iamEP = null;
        try {
            iamEP = new IAMEndPoints(new IAMConfigurationUtil(false));
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
        try {
            iamEP = new IAMEndPoints(new IAMConfigurationUtil(true));
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
        assertNotNull(iamEP);
    }

    /**
     * Test method for
     *  {@link com.liferay.portal.security.sso.iam.internal.util.
     *  IAMEndPoints#getTokenURI()}.
     */
    @Test
    public final void testGetTokenURI() {
        try {
            IAMEndPoints iamEP = new IAMEndPoints(
                    new IAMConfigurationUtil(true));
            assertNotNull(iamEP.getTokenURI());
            assertEquals("Token URL is not correct",
                    IAMConfigurationUtil.TOKEN_URL, iamEP.getTokenURI()
                            .toString());
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
    }

    /**
     * Test method for
     *  {@link com.liferay.portal.security.sso.iam.internal.util.
     *  IAMEndPoints#getJwkURI()}.
     */
    @Test
    public final void testGetJwkURI() {
        try {
            IAMEndPoints iamEP = new IAMEndPoints(
                    new IAMConfigurationUtil(true));
            assertNotNull(iamEP.getJwkURI());
            assertEquals("JWK URL is not correct",
                    IAMConfigurationUtil.JWK_URL, iamEP.getJwkURI()
                            .toString());
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
    }

    /**
     * Test method for
     *  {@link com.liferay.portal.security.sso.iam.internal.util.
     *  IAMEndPoints#getUserInfo()}.
     */
    @Test
    public final void testGetUserInfo() {
        try {
            IAMEndPoints iamEP = new IAMEndPoints(
                    new IAMConfigurationUtil(true));
            assertNotNull(iamEP.getUserInfo());
            assertEquals("User Info URL is not correct",
                    IAMConfigurationUtil.USER_INFO_URL, iamEP.getUserInfo()
                            .toString());
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
    }

    /**
     * Test method for
     *  {@link com.liferay.portal.security.sso.iam.internal.util.
     *  IAMEndPoints#getIssuer()}.
     */
    @Test
    public final void testGetIssuer() {
        try {
            IAMEndPoints iamEP = new IAMEndPoints(
                    new IAMConfigurationUtil(true));
            assertNotNull(iamEP.getIssuer());
            assertEquals("Issuer is not correct", IAMConfigurationUtil.ISSUER,
                    iamEP.getIssuer().getValue());
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
    }

    /**
     * Test method for
     *  {@link com.liferay.portal.security.sso.iam.internal.util.
     *  IAMEndPoints#getClientID()}.
     */
    @Test
    public final void testGetClientID() {
        try {
            IAMEndPoints iamEP = new IAMEndPoints(
                    new IAMConfigurationUtil(true));
            assertNotNull(iamEP.getClientID());
            assertEquals("Client is not correct", IAMConfigurationUtil.APP_ID,
                    iamEP.getClientID().getValue());
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
    }

    /**
     * Test method for
     *  {@link com.liferay.portal.security.sso.iam.internal.util.
     *  IAMEndPoints#getSecret()}.
     */
    @Test
    public final void testGetSecret() {
        try {
            IAMEndPoints iamEP = new IAMEndPoints(
                    new IAMConfigurationUtil(true));
            assertNotNull(iamEP.getSecret());
            assertEquals("Secret is not correct",
                    IAMConfigurationUtil.APP_SECRET, iamEP.getSecret()
                            .getValue());
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
    }

    /**
     * Test method for
     *  {@link com.liferay.portal.security.sso.iam.internal.util.
     *  IAMEndPoints#getAuthURI()}.
     */
    @Test
    public final void testGetAuthURI() {
        try {
            IAMEndPoints iamEP = new IAMEndPoints(
                    new IAMConfigurationUtil(true));
            assertNotNull(iamEP.getAuthURI());
            assertEquals("Auth URL is not correct",
                    IAMConfigurationUtil.AUTH_URL, iamEP.getAuthURI()
                            .toString());
        } catch (Exception ex) {
            fail("Not possible to create endpoints");
        }
    }
}
