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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.sso.iam.model.TokenInfo;

/**
 * Provides the remote service interface for Token. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TokenServiceUtil
 * @see com.liferay.portal.security.sso.iam.service.base.TokenServiceBaseImpl
 * @see com.liferay.portal.security.sso.iam.service.impl.TokenServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=iam", "json.web.service.context.path=Token"}, service = TokenService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface TokenService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TokenServiceUtil} to access the token remote service. Add custom service methods to {@link com.liferay.portal.security.sso.iam.service.impl.TokenServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Retrieves the token for the calling user.
	*
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TokenInfo getToken(ServiceContext serviceContext)
		throws PortalException;

	/**
	* Retrieves the token for the provided subject.
	*
	* @param subject The global user identifier from IAM
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TokenInfo getToken(java.lang.String subject,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Retrieves the token for the user.
	*
	* @param userId The user identifier
	* @param serviceContext The service context of the call
	* @return The token info containing the token
	* @throws PortalException If there are problem to collect the information
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TokenInfo getToken(long userId, ServiceContext serviceContext)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TokenInfo getTokenInfo(java.lang.String token,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();
}