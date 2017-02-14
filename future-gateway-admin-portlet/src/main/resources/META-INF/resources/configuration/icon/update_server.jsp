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

<liferay-ui:icon
    message="updpate-fg-server"
    onClick='<%= renderResponse.getNamespace() + "updateServerURLView()" %>'
    url="javascript:;"
/>

<aui:script>
    function <portlet:namespace />updateServerURLView() {
        Liferay.Util.openWindow(
            {
                dialog: {
                    destroyOnHide: true,
                    destroyOnClose: true,
                    on: {
                        destroy: function() {
                            parent.location.reload();
                        }
                    }
                },
                id: '<portlet:namespace />updateServerURLView',
                title: '<%= UnicodeLanguageUtil.get(request, "updpate-fg-server") %>',
                uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/fg/setServer" /><portlet:param name="redirect" value="<%= String.valueOf(renderResponse.createRenderURL()) %>" /></liferay-portlet:renderURL>'
            }
        );
    }
</aui:script>