/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.sso.iam.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.sso.iam.service.TokenServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link TokenServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.security.sso.iam.model.TokenSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.security.sso.iam.model.Token}, that is translated to a
 * {@link com.liferay.portal.security.sso.iam.model.TokenSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TokenServiceHttp
 * @see com.liferay.portal.security.sso.iam.model.TokenSoap
 * @see TokenServiceUtil
 * @generated
 */
@ProviderType
public class TokenServiceSoap {
	/**
	* Retrieves the token for the calling user.
	*
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	public static com.liferay.portal.security.sso.iam.model.TokenInfo getToken(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.security.sso.iam.model.TokenInfo returnValue = TokenServiceUtil.getToken(serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Retrieves the token for the user.
	*
	* @param userId The user identifier
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	public static com.liferay.portal.security.sso.iam.model.TokenInfo getToken(
		long userId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.security.sso.iam.model.TokenInfo returnValue = TokenServiceUtil.getToken(userId,
					serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Retrieves the token for the provided subject.
	*
	* @param subject The global user identifier from IAM
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	public static com.liferay.portal.security.sso.iam.model.TokenInfo getToken(
		java.lang.String subject,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.security.sso.iam.model.TokenInfo returnValue = TokenServiceUtil.getToken(subject,
					serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Retrieves the information associated with a token.
	* If the token is not valid an error message is included in the token
	* information and not other values are provided
	*
	* @param token The token to analyse
	* @param serviceContext The service context of the call
	* @return The token information
	* @throws PortalException If there are problem to collect the information
	*/
	public static com.liferay.portal.security.sso.iam.model.TokenInfo getTokenInfo(
		java.lang.String token,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.security.sso.iam.model.TokenInfo returnValue = TokenServiceUtil.getTokenInfo(token,
					serviceContext);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TokenServiceSoap.class);
}