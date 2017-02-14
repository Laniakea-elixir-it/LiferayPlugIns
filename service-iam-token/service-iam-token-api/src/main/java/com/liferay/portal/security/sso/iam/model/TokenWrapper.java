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

package com.liferay.portal.security.sso.iam.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link Token}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Token
 * @generated
 */
@ProviderType
public class TokenWrapper implements Token, ModelWrapper<Token> {
	public TokenWrapper(Token token) {
		_token = token;
	}

	@Override
	public Class<?> getModelClass() {
		return Token.class;
	}

	@Override
	public String getModelClassName() {
		return Token.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("id", getId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}
	}

	@Override
	public Token toEscapedModel() {
		return new TokenWrapper(_token.toEscapedModel());
	}

	@Override
	public Token toUnescapedModel() {
		return new TokenWrapper(_token.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _token.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _token.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _token.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _token.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Token> toCacheModel() {
		return _token.toCacheModel();
	}

	@Override
	public int compareTo(Token token) {
		return _token.compareTo(token);
	}

	@Override
	public int hashCode() {
		return _token.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _token.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new TokenWrapper((Token)_token.clone());
	}

	/**
	* Returns the uuid of this token.
	*
	* @return the uuid of this token
	*/
	@Override
	public java.lang.String getUuid() {
		return _token.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _token.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _token.toXmlString();
	}

	/**
	* Returns the ID of this token.
	*
	* @return the ID of this token
	*/
	@Override
	public long getId() {
		return _token.getId();
	}

	/**
	* Returns the primary key of this token.
	*
	* @return the primary key of this token
	*/
	@Override
	public long getPrimaryKey() {
		return _token.getPrimaryKey();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_token.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_token.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_token.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_token.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the ID of this token.
	*
	* @param id the ID of this token
	*/
	@Override
	public void setId(long id) {
		_token.setId(id);
	}

	@Override
	public void setNew(boolean n) {
		_token.setNew(n);
	}

	/**
	* Sets the primary key of this token.
	*
	* @param primaryKey the primary key of this token
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_token.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_token.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the uuid of this token.
	*
	* @param uuid the uuid of this token
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_token.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TokenWrapper)) {
			return false;
		}

		TokenWrapper tokenWrapper = (TokenWrapper)obj;

		if (Objects.equals(_token, tokenWrapper._token)) {
			return true;
		}

		return false;
	}

	@Override
	public Token getWrappedModel() {
		return _token;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _token.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _token.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_token.resetOriginalValues();
	}

	private final Token _token;
}