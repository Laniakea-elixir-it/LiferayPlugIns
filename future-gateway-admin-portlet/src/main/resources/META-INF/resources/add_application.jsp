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

if(request.getAttribute("app_id") == null ) {
    renderResponse.setTitle(LanguageUtil.get(request, "fg-add-app"));
} else {
    renderResponse.setTitle(LanguageUtil.get(request, "fg-edit-app"));
}

Map<String, String> infras = (Map<String, String>) request.getAttribute(FGServerConstants.INFRASTRUCTURE_COLLECTION);
%>

<portlet:actionURL name="/fg/addApp" var="addAppActionURL" />

<liferay-ui:error exception="<%= IOException.class %>" message="fg-connection-error"/>
<aui:form action="<%= addAppActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" enctype="multipart/form-data">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
    <aui:input name="redirect" type="hidden" value="<%= redirect %>" />
    <aui:input name="fg-app-id" type="hidden" value="${app_id}" />
    <aui:fieldset-group markupView="lexicon">
        <div class="row">
            <aui:fieldset cssClass="col-md-6">
                <aui:input name="fg-app-name" value="${app_name}">
                    <aui:validator name="required" />
                </aui:input>
                <aui:input name="fg-app-enabled" type="checkbox" checked="${app_enabled}"/>
                <aui:input name="fg-app-description" type="textarea" value="${app_description}"/>
                <aui:select name="fg-app-outcome">
                    <aui:option selected="${app_outcome eq 'RESOURCE'}" value="RESOURCE">
                        RESOURCE
                    </aui:option> 
                    <aui:option selected="${app_outcome eq 'JOB'}" value="JOB">
                        JOB
                    </aui:option>
                </aui:select>
                <aui:select name="fg-app-infrastructure"  multiple="true">
                <%
                if (infras!=null && !infras.isEmpty()) {
                    Set<String> infraApp = (Set) request.getAttribute("app_infras");
                    for (String infraId: infras.keySet()) {
                        boolean supported = false;
                        if (infraApp != null){
                            supported = infraApp.contains(infraId);
                        }
                        
                %>
                    <aui:option selected="<%= supported %>" value="<%= infraId %>">
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
                <div id="<portlet:namespace />fileContainer">
                    <c:forEach items="${app_file_names}" var="aFile" varStatus="theCount">
                        <div id="<portlet:namespace/>fileName${theCount.index}">
                            <hr/>
                            <p class="fileReplace">Previous file: <i>${aFile}</i></p>
                            <aui:input name="fg-app-file-old" type="hidden" value="${aFile}" />
                            <aui:input name="fg-app-file-update" type="file"/>
                            <liferay-ui:message key="alternative-option"/>
                            <aui:input name="fg-app-file-url"/>
                            <aui:button cssClass="btn-danger" name="remove_file" value="-" onClick="${renderResponse.getNamespace()}removeElement('fileName${theCount.index}')" />
                            <hr/>
                        </div>
                    </c:forEach>
                </div>
                <aui:button name="add_file" value="+" onClick="${renderResponse.getNamespace()}addFile()" />
            </aui:fieldset>
            <aui:fieldset cssClass="col-md-6" label="fg-app-parameters">
                <div id="<portlet:namespace />paramContainer">
                    <c:forEach items="${app_parameters_values}" var="aParam">
                        <div id="${renderResponse.getNamespace()}paramkey${aParam.key}" >
                            <hr/>
                            <aui:input name="fg-app-parameter-name" value="${aParam.key}"/>
                            <aui:input name="fg-app-parameter-value" value="${aParam.value}"/>
                            <aui:input name="fg-app-parameter-description" type="textarea" value="${app_parameters_descriptions[aParam.key]}"/>
                            <aui:button cssClass="btn-danger" name="add_parameter" value="-" onClick="${renderResponse.getNamespace()}removeElement('paramkey${aParam.key}')" />
                            <hr/>
                        </div>
                    </c:forEach>
                </div>
                <aui:button name="add_parameter" value="+" onClick="${renderResponse.getNamespace()}addParameter()" />
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
                <aui:input name="fg-app-file-old" type="hidden" value="N/A" />
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
