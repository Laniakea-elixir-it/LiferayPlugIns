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
<%@ include file="/init.jsp" %>
    <liferay-frontend:add-menu>
        <portlet:renderURL var="viewInfrasURL">
            <portlet:param name="toolbarItem" value="<%= "view-all-infras" %>" />
        </portlet:renderURL>

        <portlet:renderURL var="addInfraURL">
            <portlet:param name="mvcRenderCommandName" value="/fg/addInfra" />
            <portlet:param name="redirect" value="<%= viewInfrasURL %>" />
        </portlet:renderURL>

        <liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "fg-infrastructures") %>' url="<%= addInfraURL.toString() %>" />
    </liferay-frontend:add-menu>
