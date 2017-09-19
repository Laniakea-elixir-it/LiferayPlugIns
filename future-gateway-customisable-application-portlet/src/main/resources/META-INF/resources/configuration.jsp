<%@ include file="/init.jsp" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayPortletURL" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />
<%
LiferayPortletURL resourceURL = (LiferayPortletURL) renderResponse.createResourceURL();
resourceURL.setPortletId(ParamUtil.getString(request, "portletResource"));
resourceURL.setResourceID("/yaml/convert");
%>
        <script type="text/javascript">
            /*
             * All the web app needs to configure are the following
             */
            var defaultApps;
            var application;
            
            var token = null;
            var jobLimit = 5;
            var Jdiv = 0;
            var LI="LI_0";
            var infoMap = new Object();
            var jsonArr; 

            var webapp_settings = {
                apiserver_url: '',
                apiserver_path: '/apis',
                apiserver_ver: 'v1.0',
                app_id: 0,
                apiserver_endpoint: '${FGEndPoint}'
            };

           function changeApp(app_name, app_id) {
                $('#<portlet:namespace />requestButton').removeClass('disabled');
                $('#<portlet:namespace />requestButton').prop('disabled', false);
                $('#jsonButton').prop('disabled', false);
                $('#appButton').html(app_name + ' <span class="caret"></span>');
                for(var i=0; i<defaultApps.applications.length; i++) {
                    if(defaultApps.applications[i].id == app_id) {
                        application = defaultApps.applications[i];
                        webapp_settings.app_id = defaultApps.applications[i].id;
                        $('#<portlet:namespace />applicationId').val(app_id);
                        generateApplicationJson(defaultApps.applications[i].id, defaultApps.applications[i].infrastructures,
                            function(json){
                              var json1 = JSON.stringify(ans, null, 2);
                              $('#jsonArea1').val(json1);
                        }, '<%= resourceURL.toString() %>');                        
                    }
                }
            }

            function welcome(apps) {
                defaultApps = apps;
                $('#jobsDiv').html('');
                var content = '';
                for(var i=0; i<defaultApps.applications.length; i++) {
                    content += '<li><a href="javascript:void(0)" onClick="changeApp(\'';
                    content += defaultApps.applications[i].name+'\',\''+defaultApps.applications[i].id+'\')">';
                    content += defaultApps.applications[i].name+'</a></li>';
                    if (defaultApps.applications[i].id == '<%= appId %>') {
                      $('#appButton').html(defaultApps.applications[i].name + ' <span class="caret"></span>');
                      $('#jsonButton').prop('disabled', false);
                      generateApplicationJson(defaultApps.applications[i].id, defaultApps.applications[i].infrastructures,
                          function(json){
                            var json1 = JSON.stringify(json, null, 2);
                            $('#jsonArea1').val(json1);
                      }, '<%= resourceURL.toString() %>');
                      
                    }
                }
                $('#dropmenu').html(content);
                if(defaultApps.applications.length > 0) {
                    application = defaultApps.applications[0];
                }
            }


            function changeJson() {
                var ans = $('input[name="optradio"]:checked').val();
                $('#<portlet:namespace />requestButton').removeClass('disabled');
                $('#<portlet:namespace />requestButton').prop('disabled', false);
                switch(ans) {
                    case "old":
                        $('#<portlet:namespace />jsonApp').val('');
                        break;
                    case "new":
                        var newJson = $('#jsonArea2').val();
                        try {
                          var myJson = JSON.parse(newJson);
                        } catch( e ) {
                          alert("<liferay-ui:message key="customisable.application.portlet.jsonError"/>. " + e);
                        }
                        
                        $('#<portlet:namespace />jsonApp').val(newJson);
                        break;
                    default:
                        break;
                }
            }
        </script>
        <div class="panel panel-default">
        <div class="panel-body">
        <div align="left">
            <div class="btn-group">
                <button type="button" id="appButton" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                    <liferay-ui:message key="customisable.application.portlet.appSelect"/>
                    <span class="caret"></span>
                </button>
                <ul id="dropmenu" class="dropdown-menu" role="menu">
                </ul>
            </div>
            <button type="button" id="jsonButton" class="btn btn-primary" data-toggle="modal" data-target="#jsonConfig" disabled>
                <liferay-ui:message key="customisable.application.portlet.jsonConfig"/>
            </button>
        </div>

		<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
        	<center>
            	<aui:button type="submit" name="configSave" value="customisable.application.portlet.configSave" id="requestButton" class="btn btn-primary btn-lg" disabled="true"/>
	        </center>
	        <aui:input name="applicationId" id="applicationId" type="hidden" value="<%= appId==null ? "" : appId %>" />
	        <aui:input name="jsonApp" id="jsonApp" type="hidden" value="" />
	        <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
            <aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	        
		</aui:form>
        <!-- Submit record table (begin) -->    
        <div id="jobsDiv" data-modify="false"> 
        </div>        
        
        <!-- Submit record table (end) -->
        </div>        
        </div> 
        <div class="panel-footer"></div>

        <!-- JSON Config (begin) -->                       
        <div class="modal fade modal-hidden" id="jsonConfig" role="dialog" aria-labelledby="json configuration dialog" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">JSON Config</h4>
              </div>
              <div class="modal-body" style="max-height: calc(100vh - 210px); overflow-y: auto;">
                  <form>
                      <div class="radio">
                          <label><input type="radio" name="optradio" value="old" <%= isDefaultJson ?  "checked" : "" %>>
                              <button type="button" class="btn btn-default btn-xs" data-toggle="collapse" data-target="#jsonTextArea1">default json</button>
                              <liferay-ui:message key="customisable.application.portlet.defaultJson"/>
                          </label>
                      </div>
                      <div id="jsonTextArea1" class="collapse">
                          <div class="form-group">
                              <textarea class="form-control" rows="50" id="jsonArea1" readonly="readonly">
                              </textarea>
                          </div>
                      </div>
                      <div class="radio">
                          <label><input type="radio" name="optradio" value="new" <%= isDefaultJson ?  "" : "checked" %>>
                              <button type="button" class="btn btn-default btn-xs" data-toggle="collapse" data-target="#jsonTextArea2">new json</button>
                              <liferay-ui:message key="customisable.application.portlet.customJson"/>
                          </label>
                      </div>
                      <div id=jsonTextArea2 class="collapse">
                          <div class="form-group">
                              <textarea class="form-control" rows="50" id="jsonArea2">
                              </textarea>
                          </div>
                      </div>
                  </form>
              </div>                
              <div class="modal-footer">
                  <center>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onClick="changeJson()">OK</button>
                </center>
              </div>
              </div>
            </div>
          </div>
        </div>

          <div class="modal fade modal-hidden" id="information" role="dialog" aria-labelledby="information dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Information</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <center>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </center>
            </div>
            </div>
        </div>
    </div>

      <script>
            Liferay.Service(
                '/iam.token/get-token',
                function(obj) {
                    token = obj;
                    if(obj.token != undefined) {
                        token = obj.token;
                    }
                    getApplicationsJson(welcome);
                }
            );
          $('#jsonArea2').val('<%= jsonApp %>');
      </script>
