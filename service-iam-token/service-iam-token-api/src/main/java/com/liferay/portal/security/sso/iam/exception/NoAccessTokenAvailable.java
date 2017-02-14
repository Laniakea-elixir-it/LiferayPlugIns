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
package com.liferay.portal.security.sso.iam.exception;

import com.liferay.portal.kernel.exception.PortalException;

import aQute.bnd.annotation.ProviderType;

/**
 * The access token is not valid and cannot be retrieved a new token.
 *
 * @author Marco Fargetta
 */
@ProviderType
public class NoAccessTokenAvailable extends PortalException {

    /**
     * Empty exception.
     */
    public NoAccessTokenAvailable() {
        super();
    }

    /**
     * Exception with message and cause.
     *
     * @param msg The message
     * @param cause The cause
     */
    public NoAccessTokenAvailable(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    /**
     * Exception with message.
     *
     * @param msg The message
     */
    public NoAccessTokenAvailable(final String msg) {
        super(msg);
    }

    /**
     * Exception with cause.
     *
     * @param cause The cause
     */
    public NoAccessTokenAvailable(final Throwable cause) {
        super(cause);
    }

}
