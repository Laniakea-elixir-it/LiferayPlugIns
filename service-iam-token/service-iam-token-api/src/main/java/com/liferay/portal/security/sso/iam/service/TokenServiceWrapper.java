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

package com.liferay.portal.security.sso.iam.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TokenService}.
 *
 * @author Brian Wing Shun Chan
 * @see TokenService
 * @generated
 */
@ProviderType
public class TokenServiceWrapper implements TokenService,
	ServiceWrapper<TokenService> {
	public TokenServiceWrapper(TokenService tokenService) {
		_tokenService = tokenService;
	}

	/**
	* Retrieves the token for the calling user.
	*
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	@Override
	public com.liferay.portal.security.sso.iam.model.TokenInfo getToken(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _tokenService.getToken(serviceContext);
	}

	/**
	* Retrieves the token for the provided subject.
	*
	* @param subject The global user identifier from IAM
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	@Override
	public com.liferay.portal.security.sso.iam.model.TokenInfo getToken(
		java.lang.String subject,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _tokenService.getToken(subject, serviceContext);
	}

	/**
	* Retrieves the token for the user.
	*
	* @param userId The user identifier
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	@Override
	public com.liferay.portal.security.sso.iam.model.TokenInfo getToken(
		long userId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _tokenService.getToken(userId, serviceContext);
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
	@Override
	public com.liferay.portal.security.sso.iam.model.TokenInfo getTokenInfo(
		java.lang.String token,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _tokenService.getTokenInfo(token, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _tokenService.getOSGiServiceIdentifier();
	}

	@Override
	public TokenService getWrappedService() {
		return _tokenService;
	}

	@Override
	public void setWrappedService(TokenService tokenService) {
		_tokenService = tokenService;
	}

	private TokenService _tokenService;
}