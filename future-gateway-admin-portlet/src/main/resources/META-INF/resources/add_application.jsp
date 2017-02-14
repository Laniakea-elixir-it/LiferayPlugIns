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

renderResponse.setTitle(LanguageUtil.get(request, "fg-add-app"));
Map<String, String> infras = (Map<String, String>) request.getAttribute(FGServerConstants.INFRASTRUCTURE_COLLECTION);
%>

<portlet:actionURL name="/fg/addApp" var="addAppActionURL" />

<liferay-ui:error exception="<%= IOException.class %>" message="fg-connection-error"/>
<aui:form action="<%= addAppActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" enctype="multipart/form-data">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
    <aui:input name="redirect" type="hidden" value="<%= redirect %>" />
    <aui:fieldset-group markupView="lexicon">
        <div class="row">
            <aui:fieldset cssClass="col-md-6">
                <aui:input name="fg-app-name">
                    <aui:validator name="required" />
                </aui:input>
                <aui:input name="fg-app-enabled" type="checkbox" checked="true"/>
                <aui:input name="fg-app-description" type="textarea">
                </aui:input>
                <aui:select name="fg-app-outcome">
                    <aui:option selected="true" value="RESOURCE">
                        RESOURCE
                    </aui:option> 
                    <aui:option value="JOB">
                        JOB
                    </aui:option>
                </aui:select>
                <aui:select name="fg-app-infrastructure"  multiple="true">
                <%
                if (infras!=null && !infras.isEmpty()) {
                    for (String infraId: infras.keySet()) {
                %>
                    <aui:option value="<%= infraId %>">
                        <%= infras.get(infraId) %>
                    </aui:option>
                <%
                    }
                }
                %>
                </aui:select>
                <aui:spacer/>
            </aui:fieldset>
            <aui:fieldset cssClass="col-md-6" label="fg-app-files">
                <div id="<portlet:namespace />fileContainer"></div>
                <aui:button name="add_file" value="+" onClick="<%= renderResponse.getNamespace() + "addFile()" %>" />
            </aui:fieldset>
            <aui:fieldset cssClass="col-md-6" label="fg-app-parameters">
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
            var generatedId = 'Par_' + (+new Date).toString(36).slice(-5);
            var newParam = `<div id="<portlet:namespace/>generatedId" style="display: none;"><hr/>
                <aui:input name="fg-app-parameter-name">
                </aui:input>
                <aui:input name="fg-app-parameter-value">
                </aui:input>
                <aui:input name="fg-app-parameter-description" type="textarea">
                </aui:input>
                <aui:button cssClass="btn-danger" name="remove_parameter" value="-" onClick="<%= renderResponse.getNamespace() + "removeElement('generatedId');" %>" />
                <hr/></div>`;
            newParam = newParam.replace(/generatedId/gi, generatedId);
            jQuery('#<portlet:namespace />paramContainer').append(newParam);
            jQuery('#<portlet:namespace />'+generatedId).fadeIn(800);
            
        },
        []);
        
    Liferay.provide(
        window,
        '<portlet:namespace />addFile',
        function () {
            var generatedId = 'File' + (+new Date).toString(36).slice(-5);
            var newFile = `<div id="<portlet:namespace/>generatedId" style="display: none;"><hr/>
                <aui:input name="fg-app-file-update" type="file">
                </aui:input>
                <liferay-ui:message key="alternative-option"/>
                <aui:input name="fg-app-file-url"/>
                <aui:button cssClass="btn-danger" name="remove_file" value="-" onClick="<%= renderResponse.getNamespace() + "removeElement('generatedId');" %>" />
                <hr/></div>`;
            newFile = newFile.replace(/generatedId/gi, generatedId);
            jQuery('#<portlet:namespace />fileContainer').append(newFile);
            jQuery('#<portlet:namespace />'+generatedId).fadeIn(800);
            
        },
        []);

    Liferay.provide(
        window,
        '<portlet:namespace />removeElement',
        function (id) {
            jQuery('#<portlet:namespace />' + id).fadeOut(800, function(){
                jQuery('#<portlet:namespace />' + id).remove();
            });
        },
        []);
</aui:script>
