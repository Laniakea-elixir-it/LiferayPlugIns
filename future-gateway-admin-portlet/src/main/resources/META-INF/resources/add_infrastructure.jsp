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
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "fg-add-infra"));
%>

<portlet:actionURL name="/fg/addInfra" var="addInfraActionURL" />

<liferay-ui:error exception="<%= IOException.class %>" message="fg-connection-error"/>
<aui:form action="<%= addInfraActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
    <aui:input name="redirect" type="hidden" value="<%= redirect %>" />
    <aui:fieldset-group markupView="lexicon">
        <div class="row">
            <aui:fieldset cssClass="col-md-6">
                <aui:input name="fg-infra-name">
                    <aui:validator name="required" />
                </aui:input>
                <aui:input name="fg-infra-enabled" type="checkbox" checked="true"/>
                <aui:input name="fg-infra-virtual" type="checkbox" checked="false"/>
                <aui:input name="fg-infra-description" type="textarea">
                </aui:input>
            </aui:fieldset>
            <aui:fieldset cssClass="col-md-5" label="fg-infra-parameters">
                <div id="<portlet:namespace />paramContainer"></div>
                <aui:button name="add_parameter" value="+" onClick="<%= renderResponse.getNamespace() + "addParameter()" %>" />
            </aui:fieldset>
        </div>
    </aui:fieldset-group>
    <aui:button type="submit" />
</aui:form>

<aui:script>
    Liferay.provide(
        window,
        '<portlet:namespace />addParameter',
        function () {
            var generatedId = (+new Date).toString(36).slice(-5);
            var newParam = `<div id="<portlet:namespace/>generatedId" style="display: none;"><hr/>
                <aui:input name="fg-infra-parameter-name">
                </aui:input>
                <aui:input name="fg-infra-parameter-value">
                </aui:input>
                <aui:input name="fg-infra-parameter-description" type="textarea">
                </aui:input>
                <aui:button cssClass="btn-danger" name="add_parameter" value="-" onClick="<%= renderResponse.getNamespace() + "removeParameter('generatedId');" %>" />
                <hr/></div>`;
            newParam = newParam.replace(/generatedId/gi, generatedId);
            jQuery('#<portlet:namespace />paramContainer').append(newParam);
            jQuery('#<portlet:namespace />'+generatedId).fadeIn(800);
            
        },
        []);
        
    Liferay.provide(
        window,
        '<portlet:namespace />removeParameter',
        function (id) {
            jQuery('#<portlet:namespace />' + id).fadeOut(800, function(){
                jQuery('#<portlet:namespace />' + id).remove();
            });
        },
        []);
</aui:script>
