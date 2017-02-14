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
 * Keys used in the web interfaces.
 *
 * @author Marco Fargetta
 */
public final class IAMWebKeys {

    /**
     * IAM access token.
     */
    public static final String IAM_ACCESS_TOKEN = "IAM_ACCESS_TOKEN";

    /**
     * IAM application identifier.
     */
    public static final String IAM_APP_ID = "IAM_APP_ID";

    /**
     * IAM authentication redirect URL.
     */
    public static final String IAM_AUTH_REDIRECT_URL = "IAM_AUTH_REDIRECT_URL";

    /**
     * IAM authentication URL.
     */
    public static final String IAM_AUTH_URL = "IAM_AUTH_URL";

    /**
     * IAM user identifier.
     */
    public static final String IAM_USER_ID = "IAM_USER_ID";

    /**
     * IAM user email address.
     */
    public static final String IAM_USER_EMAIL_ADDRESS =
            "IAM_USER_EMAIL_ADDRESS";

    /**
     * Default constructor to avoid instances.
     */
    private IAMWebKeys() {
    }
}
