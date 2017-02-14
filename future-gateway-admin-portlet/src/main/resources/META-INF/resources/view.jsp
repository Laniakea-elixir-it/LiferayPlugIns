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

<%
String toolbarItem = ParamUtil.getString(renderRequest, "toolbarItem", "view-all-tasks");
%>

<%@ include file="/fragments/toolbar.jspf" %>

<div id="<portlet:namespace />ResourcesTable">
</div>

<c:choose>
    <c:when test="${param.toolbarItem eq 'view-all-apps'}">
        <liferay-util:include page="/applications.jsp" servletContext="<%= application %>" />
        <script>
            var resource = 'applications';
            var columns = ['id', 'name', 'enabled', 'outcome'];
        </script>
    </c:when>
    <c:when test="${param.toolbarItem eq 'view-all-infras'}">
        <liferay-util:include page="/infrastructures.jsp" servletContext="<%= application %>" />
        <script>
            var resource = 'infrastructures';
            var columns = ['id', 'name', 'enabled', 'virtual'];
        </script>
    </c:when>
    <c:otherwise>
        <liferay-util:include page="/tasks.jsp" servletContext="<%= application %>" />
        <script>
            var resource = 'tasks';
            var columns = ['id', 'date', 'status', 'description'];
        </script>
    </c:otherwise>
</c:choose>

<div id="<portlet:namespace />waitLoad" class="loading"><h2>Loading .....</h2></div>

<aui:script require="future-gateway-admin-portlet/js/fgTable.es">
    var Table = futureGatewayAdminPortletJsFgTableEs.default;
    var table = null;

    Liferay.Service(
            '/iam.token/get-token',
            function(obj) {
                table = new Table('${FGURL}', document.getElementById('<portlet:namespace />ResourcesTable'), obj.token);
                table.render(resource, columns, '<portlet:namespace />resourceDetails', document.getElementById('<portlet:namespace />waitLoad'));
            }
    );

    Liferay.provide(
        window,
        '<portlet:namespace />resourceDetails',
        function (id, resource) {
            table.showDetails(id, resource, '<portlet:namespace />resourceDelete');
        },
        []);

    Liferay.provide(
        window,
        '<portlet:namespace />resourceDelete',
        function (id, resource) {
            table.delete(id, resource, '<portlet:namespace />resourceDelete');
        },
        []);
        
</aui:script>
