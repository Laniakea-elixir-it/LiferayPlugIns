<%--
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
--%>
<%@ include file="/com.liferay.portal.settings.web/init.jsp" %>


<%
IAMConfiguration iamConfiguration = ConfigurationProviderUtil.getConfiguration(IAMConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), "iam--", new CompanyServiceSettingsLocator(company.getCompanyId(), IAMConstants.SERVICE_NAME)));

boolean authEnabled = iamConfiguration.enabled();
boolean verifiedAccountRequired = iamConfiguration.verifiedAccountRequired();
String appId = iamConfiguration.appId();

String appSecret = iamConfiguration.appSecret();

if (Validator.isNotNull(appSecret)) {
	appSecret = Portal.TEMP_OBFUSCATION_VALUE;
}

String configurationURL = iamConfiguration.configurationURL();
String oauthAuthURL = iamConfiguration.oauthAuthURL();
String oauthTokenURL = iamConfiguration.oauthTokenURL();
String openidUserinfoURL = iamConfiguration.openidUserinfoURL();
String openidJwkURL = iamConfiguration.openidJwkURL();
String openidIssuer = iamConfiguration.openidIssuer();
String oauthExtraScopes = iamConfiguration.oauthExtraScopes();
%>

<liferay-ui:error key="iamConfigurationURLInvalid" message="the-iam-configuration-url-is-invalid" />
<liferay-ui:error key="iamOauthAuthURLInvalid" message="the-iam-oauth-auth-url-is-invalid" />
<liferay-ui:error key="iamOauthTokenURLInvalid" message="the-iam-oauth-token-url-is-invalid" />
<liferay-ui:error key="iamOpenidUserinfoURLInvalid" message="the-iam-openid-userinfo-url-is-invalid" />
<liferay-ui:error key="iamOpenidJWKURLInvalid" message="the-iam-openid-jwk-url-is-invalid" />

<aui:fieldset>
	<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/iam_connect" />

	<aui:input label="enabled" name="iam--enabled" type="checkbox" value="<%= authEnabled %>" />

	<aui:input label="require-verified-account" name="iam--verifiedAccountRequired" type="checkbox" value="<%= verifiedAccountRequired %>" />

	<aui:input cssClass="lfr-input-text-container" label="application-id" name="iam--appId" type="text" value="<%= appId %>" />

	<aui:input cssClass="lfr-input-text-container" label="application-secret" name="iam--appSecret" type="password" value="<%= appSecret %>" />

	<aui:input cssClass="lfr-input-text-container" label="the-iam-configuration-url" name="iam--configurationURL" type="text" value="<%= configurationURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="oauth-authentication-url" name="iam--oauthAuthURL" type="text" value="<%= oauthAuthURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="oauth-token-url" name="iam--oauthTokenURL" type="text" value="<%= oauthTokenURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="the-iam-openid-userinfo-url" name="iam--openidUserinfoURL" type="text" value="<%= openidUserinfoURL %>" />
	
	<aui:input cssClass="lfr-input-text-container" label="the-iam-openid-jwk-url" name="iam--openidJwkURL" type="text" value="<%= openidJwkURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="the-iam-openid-issuer" name="iam--openidIssuer" type="text" value="<%= openidIssuer %>" />

	<aui:input cssClass="lfr-input-text-container" label="the-iam-oauth-extra-scopes" name="iam--oauthExtraScopes" type="text" value="<%= oauthExtraScopes %>" />
</aui:fieldset>