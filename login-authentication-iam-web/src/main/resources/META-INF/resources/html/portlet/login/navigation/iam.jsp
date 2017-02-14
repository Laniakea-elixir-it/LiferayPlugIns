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

<%@ include file="/html/portlet/login/navigation/init.jsp" %>

<%
String iamAuthURL = PortalUtil.getPathContext() + "/c/portal/iam_openidconnect?cmd=login";

String taglibOpenIamLoginWindow = "javascript:var iamLoginWindow = window.open('" + iamAuthURL.toString() + "', 'iam', 'align=center,directories=no,height=560,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=1000'); void(''); iamLoginWindow.focus();";
%>

<liferay-ui:icon
	iconCssClass="icon-iam-sign"
	message="iam"
	url="<%= taglibOpenIamLoginWindow %>"
/>