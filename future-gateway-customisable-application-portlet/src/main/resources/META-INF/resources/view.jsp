<%@ include file="/init.jsp" %>
        <portlet:resourceURL var="oneDataURL" id="/oneData/resources"/>
        <portlet:resourceURL var="oneDataTokenURL" id="/oneData/token"/>
        <portlet:resourceURL var="yamlConvertURL" id="/yaml/convert"/>
    <script>
         define._amd = define.amd;
         define.amd = false;
    </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jstree.min.js">
    </script>
    <script>
       define.amd = define._amd;
    </script>

        <script type="text/javascript">
            /*
             * All the web app needs to configure are the following
             */
            var webapp_settings = {
                apiserver_url: ''
               ,apiserver_path : '/apis'
               ,apiserver_ver  : 'v1.0'
               ,app_id         : '<%= appId %>'
               ,apiserver_endpoint: '${FGEndPoint}'
            };
            var myJson;
            <c:if test="<%= !isDefaultJson %>">
	            myJson = JSON.parse('<%= jsonApp.replaceAll("'", "\\\\'") %>') ;
            </c:if>
            var parameterFile = '<%= parameterFile %>';
            var defaultJson = myJson;
            var isDefaultJson = <%= isDefaultJson %>;
            var token = null;
            var jobLimit = 5;
            var Jdiv = 0;
            var LI="LI_0";
            var infoMap = new Object();
            var jsonArr;

            /*
             * Change variable below to change delay of check status loop
             */
            var TimerDelay = 15000;

            /*
             * Page initialization
             */
            $(document).ready(function() {
                $('#confirmDelete').on('show.bs.modal', function (e) {
                    $message = $(e.relatedTarget).attr('data-message');
                    $(this).find('.modal-body p').text($message);
                    $title = $(e.relatedTarget).attr('data-title');
                    $job_id = $(e.relatedTarget).attr('data-data');
                    $(this).find('.modal-title').text($title);
                    $('#job_id').attr('data-value', $job_id)
                    $('#confirmJobDel').show();
                    $('#cancelJobDel').text('Cancel');
                });
                // Form confirm (yes/ok) handler, submits form 
                $('#confirmDelete').find('.modal-footer #confirmJobDel').on('click', function(e){
                    $job_id = $('#job_id').attr('data-value');
                    cleanJob($job_id);              
                });
                setTimeout(checkJobs, TimerDelay); // Initialize the job check loop
            });     
            function printDefault() {
                out = '<p><b>job identifier </b></br>';
                out += '<input type="text" maxlength="50" id="jobDescription" class="form-control"></p>';
                document.getElementById("<portlet:namespace/>appSubmitForm").innerHTML = out;
            }
            function printJsonArray() {
                jsonArr = {};
                jsonTab = {};
                if(myJson) {
                    jsonArr = myJson.parameters;
                    jsonTab = myJson.tabs;
                }
                var i;
                var k;
                printDefault();
                tabBegin = '<ul class="nav nav-tabs">'; 
                var tabs = null;
                var makeTabs = false;
                var maxTab = 0;
                if(jsonTab != null) {
                    maxTab = jsonTab.length;
                    tabs = new Array(maxTab);
                    makeTabs = true;
                    for(var i=0; i<maxTab; i++) {
                        if(i == 0) {
                            tabBegin += '<li class="active"><a data-toggle="tab" href="#menu'+i+'">'+jsonTab[i]+'</a></li>';
                        }
                        else {
                            tabBegin += '<li><a data-toggle="tab" href="#menu'+i+'">'+jsonTab[i]+'</a></li>';
                        }
                        tabs[i] = '';
                    }
                }
                tabBegin += '</ul>'; 
                var out;
                var globalOut='';
                for(var i = 0; i < jsonArr.length; i++) {
                    out = '';
                    if(jsonArr[i].hasOwnProperty('display')){
                        out += '<p><b>' + jsonArr[i].display + '</b>';
                    }
                    else {
                        out += '<p><b>' + jsonArr[i].name + '</b>';
                    }
                    switch(jsonArr[i].type) {
                        case "password":
                            out += '<input type="password" maxlength="50" id="param_'+jsonArr[i].name
                                +'" class="form-control" value="' + jsonArr[i].value + '"/></br>';
                            break;
                        case "radio":    
                            out += '<div id="param_'+jsonArr[i].name+'" class="radio">';
                            for(k = 0; k < jsonArr[i].value.length; k++) {
                                out += '<label><input type="radio" name="'+jsonArr[i].name 
                                    +'" value="'+jsonArr[i].value[k]+'"';
                                if(k == 0) {
                                    out += ' checked';
                                }
                                out += '>'+jsonArr[i].value[k]+'</label></br>';
                            }
                            out += '</div>';
                            break;
                        case "list":
                            out += '<div class="form-group">';
                            out += '<select class="form-control" id="param_'+jsonArr[i].name+'">'
                                for(k = 0; k < jsonArr[i].value.length; k++) {
                                    out += '<option>'+jsonArr[i].value[k]+'</option>'
                                }
                            out += '</select></div>';
                            break;
                        case "onedata":
                          out += '<div class="form-group">';
                          out += '<select id="param_'+jsonArr[i].name
                              +'" onchange="<portlet:namespace />updateOneDataTree(\'param_'+jsonArr[i].name+'\', \'param_tree_'+jsonArr[i].name+'\')" class="form-control">';
                          out += '<option value="">Select the OneZone</option>';
                          var onezone;
                          for(onezone in jsonArr[i].value) {
                            out += '<option value="' + jsonArr[i].value[onezone] + '">' + jsonArr[i].value[onezone] + '</option>';
                          } 
                          out += '</select></br>';
                          out += '<div id="param_tree_'+jsonArr[i].name+'"></div>';
                          out += '</div>';
                          break;
                        case "text":
                        default:
                            out += '<input type="text" id="param_'+jsonArr[i].name
                                +'" class="form-control" value="' + jsonArr[i].value + '"/></br>';
                            break;
                    }
                    out += '</p>';
                    if((jsonArr[i].tab != null) && makeTabs) {
                        var index = jsonArr[i].tab;
                        if(index < maxTab) {
                            tabs[index] += out;
                        }
                        else {
                            globalOut += out;
                        }
                    }
                    else {
                        globalOut += out;
                    }
                }
                if(jsonTab != null) {
                    out = '<div id="params-modal">';
                    out += globalOut;
                    out += tabBegin;
                    out += '<div class="tab-content">';
                    for(var i=0; i < jsonTab.length; i++) {
                        if(i == 0) {
                            out += '<div id="menu'+i+'" class="tab-pane fade in active">';
                        }
                        else {
                            out += '<div id="menu'+i+'" class="tab-pane fade">';
                        }
                        out += tabs[i];
                        out += '</div>';
                    }
                    out += '</div>';
                    out += '</div>';
                }
                else {
                    out = '<div id="params-modal">';
                    out += globalOut;
                    out += '</div>';
                }
                var myDiv = document.getElementById("<portlet:namespace/>appSubmitForm");
                myDiv.innerHTML = myDiv.innerHTML + out;
                for(var i = 0; i < jsonArr.length; i++) {
                  switch(jsonArr[i].type) {
                    case "password":
                      if(jsonArr[i].maxlength) {
                        $("#param_"+jsonArr[i].name).prop("maxLength",jsonArr[i].maxlength);
                      }
                      break;
                    case "password":
                      if(jsonArr[i].maxlength) {
                        $("#param_"+jsonArr[i].name).prop("maxLength",jsonArr[i].maxlength);
                      }
                      break;
                    case "list":
                      if(jsonArr[i].choosen) {
                        $("#param_"+jsonArr[i].name).val(jsonArr[i].choosen);
                      }
                      break;
                    case "radio":
                      if(jsonArr[i].choosen) {
                        var radio_length = $('input[name='+jsonArr[i].name+']').length;
                        for(var j=0; j<radio_length; j++) {
                          if($('input[name='+jsonArr[i].name+']')[j].defaultValue == jsonArr[i].choosen) {
                            $('input[name='+jsonArr[i].name+']')[j].checked=true;
                            break;
                          }
                        }
                      }
                      break;
                  }
                }
            }
            function getParams() {
                var paramJson = { parameters: {} };
                if(myJson != null) {
                    var jsonArr = myJson.parameters;
                }
                var token_to_update  = [];

                for(var i=0; i<jsonArr.length; i++) {
                    switch(jsonArr[i].type) {
                        case "password":
                            var out = $('#param_'+jsonArr[i].name).val();
                            paramJson.parameters[jsonArr[i].name] = out;
                            break;
                        case "radio":
                            var out = $('input[name='+jsonArr[i].name+']:checked').val();
                            paramJson.parameters[jsonArr[i].name] = out;
                            break;
                        case "onedata":
                            var onezonedata = $('#param_tree_'+jsonArr[i].name).jstree().get_selected();
                            if (onezonedata.length < 1) {
                              break;
                            }
                            if (jsonArr[i].component_map) {
                              var onezoneid = onezonedata[0];
                              if (jsonArr[i].component_map.space) {
                                paramJson.parameters[jsonArr[i].component_map.space] = $('#param_tree_'+jsonArr[i].name).jstree().get_text(onezoneid.substring(0, onezoneid.indexOf('/')));
                              }
                              if (jsonArr[i].component_map.path) {
                                paramJson.parameters[jsonArr[i].component_map.path] = onezoneid.substring(onezoneid.indexOf('/') + 1, onezoneid.lastIndexOf('/'));
                              }
                              if (jsonArr[i].component_map.file) {
                                paramJson.parameters[jsonArr[i].component_map.file] = onezoneid.substring(onezoneid.lastIndexOf('/') + 1);
                              }
                              if (jsonArr[i].component_map.provider) {
                                paramJson.parameters[jsonArr[i].component_map.provider] = $('li[id="' + onezoneid + '"]').attr('provider');
                              }
                              if (jsonArr[i].component_map.token) {
                                token_to_update.push({param: jsonArr[i].component_map.token, element: jsonArr[i].name});
                              }
                            } else {
                              paramJson.parameters[jsonArr[i].name] = onezonedata[0];
                            }
                            break;
                        case "list":
                            var out = $('#param_'+jsonArr[i].name).val();
                            paramJson.parameters[jsonArr[i].name] = out;
                            break;
                        case "text":
                        default:
                            var out = $('#param_'+jsonArr[i].name).val();
                            paramJson.parameters[jsonArr[i].name] = out;
                    }
                }
                var deferred = [];
                for (var key in token_to_update) {
                  let itemKey = key;
                  deferred.push($.ajax({
                    url: '${oneDataTokenURL}',
                    dataType: 'json',
                    data: {
                      <portlet:namespace />oneZone: $('#param_'+token_to_update[itemKey].element).val()
                    },
                    success: function(data) {
                      paramJson.parameters[token_to_update[itemKey].param] = data.token;
                    }
                  }));                  
                }
                return {params: paramJson, defers: deferred};
            }
           function <portlet:namespace />updateOneDataTree(oneZone, tree) {
              $('#'+tree).jstree({
                  'core': {
                     'multiple': false,
                     'data' : {
                       'url' : '${oneDataURL}',
                       'dataType' : 'json',
                       'data' : function (node) {
                         return {
                           '<portlet:namespace />path' : node.id,
                           '<portlet:namespace />oneZone' : $('#'+oneZone).val(),
                           };
                       }
                     }
                   }
              });
              $('#'+tree).jstree().refresh(false, true);
            }
        </script>
        <div class="panel panel-default">
        <div class="panel-body">
		<c:choose>
		<c:when test="<%= appId == null || appId.isEmpty() %>" >
            <h3>
                <div align="center" id="mainTitle">
                    <liferay-ui:message key="customisable.application.portlet.configReq" />
                </div>
	        </h3>
        </c:when>
        <c:otherwise>
            <div id="<portlet:namespace/>appSubmitForm"></div>
            <center>                
                <button type="button" class="btn btn-primary" onClick="submitJob()" id="submitBigButton">Submit</button>
            </center>
        </c:otherwise>
        </c:choose>

        <!-- Submit record table (begin) -->    
        <div id="jobsDiv" data-modify="false"> 
        </div>        
        
        <!-- Submit record table (end) -->
        </div>        
        </div> 
        <div class="panel-footer"></div>


        <!-- Confirm Modal Dialog (begin) -->                       
        <div class="modal fade modal-hidden" id="confirmDelete" role="dialog" aria-labelledby="confirmDeleteLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Delete Parmanently</h4>
              </div>
              <div class="modal-body">
                  <p></p>
              </div>                
                <div id="job_id" class='job_id' data-name='job_id' data-value=''/>
                  <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal" id="cancelJobDel">Cancel</button>
                  <button type="button" class="btn btn-danger" id="confirmJobDel">Delete</button>
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

                    if(document.getElementById("<portlet:namespace/>appSubmitForm")) {
                      if(isDefaultJson) {
                        getApplicationInfra('<%= appId %>', function(infra){
                          generateApplicationJson('<%= appId %>', infra,
                              function(json){
                                myJson = json;
                                defaultJson = myJson;
                                printJsonArray();
                              }, '<%= yamlConvertURL.toString() %>');
                          
                        });
                      } else {
	                    printJsonArray();
                      }
                    }
                    <c:if test="<%= appId != null && !appId.isEmpty() %>" >
                    prepareJobTable();
                    </c:if>
                }
            );
        
          var json2 = JSON.stringify(defaultJson, null, 2);
          $('#jsonArea2').val(json2);


    </script>
