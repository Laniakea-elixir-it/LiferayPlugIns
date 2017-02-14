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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.security.sso.iam.service.http.TokenServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.security.sso.iam.service.http.TokenServiceSoap
 * @generated
 */
@ProviderType
public class TokenSoap implements Serializable {
	public static TokenSoap toSoapModel(Token model) {
		TokenSoap soapModel = new TokenSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setId(model.getId());

		return soapModel;
	}

	public static TokenSoap[] toSoapModels(Token[] models) {
		TokenSoap[] soapModels = new TokenSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TokenSoap[][] toSoapModels(Token[][] models) {
		TokenSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TokenSoap[models.length][models[0].length];
		}
		else {
			soapModels = new TokenSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TokenSoap[] toSoapModels(List<Token> models) {
		List<TokenSoap> soapModels = new ArrayList<TokenSoap>(models.size());

		for (Token model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TokenSoap[soapModels.size()]);
	}

	public TokenSoap() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	private String _uuid;
	private long _id;
}