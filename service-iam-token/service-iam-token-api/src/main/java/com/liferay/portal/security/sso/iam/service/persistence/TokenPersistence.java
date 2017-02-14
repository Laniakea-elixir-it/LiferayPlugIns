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

package com.liferay.portal.security.sso.iam.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.security.sso.iam.exception.NoSuchTokenException;
import com.liferay.portal.security.sso.iam.model.Token;

/**
 * The persistence interface for the token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.security.sso.iam.service.persistence.impl.TokenPersistenceImpl
 * @see TokenUtil
 * @generated
 */
@ProviderType
public interface TokenPersistence extends BasePersistence<Token> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TokenUtil} to access the token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the tokens where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching tokens
	*/
	public java.util.List<Token> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the tokens where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of tokens
	* @param end the upper bound of the range of tokens (not inclusive)
	* @return the range of matching tokens
	*/
	public java.util.List<Token> findByUuid(java.lang.String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the tokens where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of tokens
	* @param end the upper bound of the range of tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching tokens
	*/
	public java.util.List<Token> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator);

	/**
	* Returns an ordered range of all the tokens where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of tokens
	* @param end the upper bound of the range of tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching tokens
	*/
	public java.util.List<Token> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first token in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching token
	* @throws NoSuchTokenException if a matching token could not be found
	*/
	public Token findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator)
		throws NoSuchTokenException;

	/**
	* Returns the first token in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching token, or <code>null</code> if a matching token could not be found
	*/
	public Token fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator);

	/**
	* Returns the last token in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching token
	* @throws NoSuchTokenException if a matching token could not be found
	*/
	public Token findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator)
		throws NoSuchTokenException;

	/**
	* Returns the last token in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching token, or <code>null</code> if a matching token could not be found
	*/
	public Token fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator);

	/**
	* Returns the tokens before and after the current token in the ordered set where uuid = &#63;.
	*
	* @param id the primary key of the current token
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next token
	* @throws NoSuchTokenException if a token with the primary key could not be found
	*/
	public Token[] findByUuid_PrevAndNext(long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator)
		throws NoSuchTokenException;

	/**
	* Removes all the tokens where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of tokens where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching tokens
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Caches the token in the entity cache if it is enabled.
	*
	* @param token the token
	*/
	public void cacheResult(Token token);

	/**
	* Caches the tokens in the entity cache if it is enabled.
	*
	* @param tokens the tokens
	*/
	public void cacheResult(java.util.List<Token> tokens);

	/**
	* Creates a new token with the primary key. Does not add the token to the database.
	*
	* @param id the primary key for the new token
	* @return the new token
	*/
	public Token create(long id);

	/**
	* Removes the token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the token
	* @return the token that was removed
	* @throws NoSuchTokenException if a token with the primary key could not be found
	*/
	public Token remove(long id) throws NoSuchTokenException;

	public Token updateImpl(Token token);

	/**
	* Returns the token with the primary key or throws a {@link NoSuchTokenException} if it could not be found.
	*
	* @param id the primary key of the token
	* @return the token
	* @throws NoSuchTokenException if a token with the primary key could not be found
	*/
	public Token findByPrimaryKey(long id) throws NoSuchTokenException;

	/**
	* Returns the token with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the token
	* @return the token, or <code>null</code> if a token with the primary key could not be found
	*/
	public Token fetchByPrimaryKey(long id);

	@Override
	public java.util.Map<java.io.Serializable, Token> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the tokens.
	*
	* @return the tokens
	*/
	public java.util.List<Token> findAll();

	/**
	* Returns a range of all the tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tokens
	* @param end the upper bound of the range of tokens (not inclusive)
	* @return the range of tokens
	*/
	public java.util.List<Token> findAll(int start, int end);

	/**
	* Returns an ordered range of all the tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tokens
	* @param end the upper bound of the range of tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of tokens
	*/
	public java.util.List<Token> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator);

	/**
	* Returns an ordered range of all the tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tokens
	* @param end the upper bound of the range of tokens (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of tokens
	*/
	public java.util.List<Token> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Token> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the tokens from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of tokens.
	*
	* @return the number of tokens
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}