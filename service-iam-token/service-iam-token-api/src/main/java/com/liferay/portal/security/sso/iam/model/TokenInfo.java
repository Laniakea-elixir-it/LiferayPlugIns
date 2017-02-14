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

package com.liferay.portal.security.sso.iam.model;

import java.util.List;

import com.liferay.portal.kernel.json.JSON;
import aQute.bnd.annotation.ProviderType;

/**
 * This is the  class object returned from the token service.
 * This object is used by the remote interface of the token service
 * and is not internally used.
 *
 * @author Marco Fargetta
 */
@ProviderType
public class TokenInfo {

    /**
     * Retrieves the error message if a problem arise for the service.
     *
     * @return The error
     */
    public final String getError() {
        return error;
    }

    /**
     * Sets the error message.
     *
     * @param errorMsg The error to set
     */
    public final void setError(final String errorMsg) {
        this.error = errorMsg;
    }

    /**
     * Retrieves the user token.
     *
     * @return The token
     */
    public final String getToken() {
        return token;
    }

    /**
     * Sets the user token.
     *
     * @param aToken The token to set
     */
    public final void setToken(final String aToken) {
        this.token = aToken;
    }

    /**
     * Retrieves the subject.
     *
     * @return The subject
     */
    public final String getSubject() {
        return subject;
    }

    /**
     * Sets the user subject.
     *
     * @param aSubject The subject to set
     */
    public final void setSubject(final String aSubject) {
        this.subject = aSubject;
    }

    /**
     * Retrieves the user groups.
     *
     * @return The groups
     */
    @JSON
    public final List<String> getGroups() {
        return groups;
    }

    /**
     * Sets the user groups.
     *
     * @param someGroups The groups to set
     */
    public final void setGroups(final List<String> someGroups) {
        this.groups = someGroups;
    }

    /**
     * The error message.
     */
    private String error;

    /**
     * The user subject.
     */
    private String subject;

    /**
     * The user token.
     */
    private String token;

    /**
     * The user groups.
     */
    private List<String> groups;
}
