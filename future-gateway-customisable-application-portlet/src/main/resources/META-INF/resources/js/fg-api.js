/**
 * 
 */
/*
 * Adds the i-th job record
 */
function openNewWindow(www_site) {
  window.open(www_site);
}
function predicatBy(prop) {
  return function(a, b) {
    if (a[prop] > b[prop]) {
      return -1;
    } else if (a[prop] < b[prop]) {
      return 1;
    }
    return 0;
  }
}
function getFile(file_url) {
  var res = null;
  if(file_url == null) {
    return res;
  }
  $.ajax({                     
    type: "GET",
    async: false,
    headers: {
      'Authorization':'Bearer ' + token
    },
    url: webapp_settings.apiserver_url
      +webapp_settings.apiserver_path +'/'
      +webapp_settings.apiserver_ver +'/'
      +file_url,
    success: function(data) {
      res=data;
    }, 
  });
  return res;
}
function getApplicationsJson(successCallback) {
  $.ajax({
        type : "GET",
        headers : {
          'Authorization' : 'Bearer ' + token
        },
        url : webapp_settings.apiserver_endpoint ? webapp_settings.apiserver_endpoint
            + '/' + 'applications'
            : webapp_settings.apiserver_url + webapp_settings.apiserver_path
                + '/' + webapp_settings.apiserver_ver + '/' + 'applications',
        dataType : "json",
        success : function(data) {
          if (typeof successCallback === "function") {
            successCallback(data);
          };
        },
      });
}

function updateIP(job_output_url, id) {
  $.ajax({
    type : "GET",
    headers : {
      'Authorization' : 'Bearer ' + token
    },
    url : webapp_settings.apiserver_url + webapp_settings.apiserver_path + '/'
        + webapp_settings.apiserver_ver + '/' + job_output_url,
    dataType : "json",
    success : function(data) {
      if (data.cluster_creds) {
        infoMap[id] = 'user: ' + data.cluster_creds.user + '</br></br>' + data.cluster_creds.token;
      }
      ${"#ip_" + id}.html(data.galaxy_url);
      ${"#ip_" + id}.bind("click", function(){
        openNewWindow(data.galaxy_url)
      })
    },
  });
}
function clusterInfo(id) {
  var repl = infoMap[id].replace(/\n/g, "</br>");
  var data = '<center><p style="font-family:\'Courier New\'">' + repl
      + '</p></center>';
  $('#information').find('.modal-body').html(data);
  $('#information').modal();
}
function appendJobRecord(jobIndex, jrec, container) {
  var job_id = jrec.id;
  var job_status = jrec.status;
  var job_date = jrec.date;
  var job_lastchange = jrec.last_change;
  var job_description = jrec.description;
  var out_files = jrec.output_files;
  var outFiles = '';
  if (job_status == 'DONE') {
    del_btn = '<button id="cln_btn' + job_id + '"'
        + '        class="btn btn-xs btn-danger"' + '        type="button"'
        + '        data-toggle="modal"'
        + '        data-target="#confirmDelete"'
        + '        data-title="Delete job"'
        + '        data-message="Are you sure you want to delete job?"'
        + '        data-data="' + job_id + '">'
        + '<i class="glyphicon glyphicon-trash"></i> Delete' + '</button>';
    for (var j = 0; j < out_files.length; j++)
      outFiles += '<div class="row">' + '  <div class="col-sm-3">'
          + '  <a href="' + webapp_settings.apiserver_url
          + webapp_settings.apiserver_path + '/'
          + webapp_settings.apiserver_ver + '/' + out_files[j].url + '">'
          + out_files[j].name + '</a>' + '  </div>'
          + '  <div class="col-sm-3">' + '  </div>'
          + '  <div class="col-sm-3">' + '  </div>' + '</div>';
  } else {
    del_btn = '';
    outFiles = '  <div class="col-sm-3"><small>No output available yet</small>'
        + '  </div>' + '  <div class="col-sm-3">' + '  </div>'
        + '  <div class="col-sm-3">' + '  </div>';
  }
  if (job_status != 'CANCELLED'){
    container.append('<tr id="' + job_id + '">' + '   <td rowspan="2">'
        + '        <button id="job_btn' + job_id + '"'
        + '        class="btn btn-default btn-xs toggle" onClick="cleanJob('
        + job_id + ')">'
        + '        <span class="glyphicon glyphicon-cloud-download"></span>'
        + '        </button>' + '	</td>' + '  <td>' + job_date + '</td>'
        + '  <td>' + job_lastchange + '</td>' + '  <td>' + job_status + '</td>'
        + '  <td>' + job_description + '</td>'
        + '  <td><button type="button" class="btn btn-default btn-sm" id="ip_' + job_id + '">'
        + '   N/A'
        + '</button>' + '      <button id="job_info_' + job_id
        + '" type="button" class="btn btn-default btn-sm"'
        + '      style="display:none;" onClick=clusterInfo(' + job_id + ')>'
        + '      <span class="glyphicon glyphicon-info-sign"></span>'
        + '      </button>' + '  </td>' + '</tr>'
        + '<tr class="tablesorter-childRow">' + '<td colspan="4">'
        + '<div class="row">' + '  <div class="col-sm-3"><b>Output</b></div>'
        + '  <div class="col-sm-3"></div>' + '  <div class="col-sm-3">'
        + del_btn + '</div>' + '</div>' + outFiles + '</td>' + '</tr>');
    ;
    if (job_status == 'DONE') {
      for (var i = 0; i < out_files.length; i++) {
        if (out_files[i].name == 'stdout.txt') {
          updateIP(out_files[i].url, job_id);
        }
      }
    }
  }
}
/*
 * Clean the specified job             
 */
function cleanJob(job_id) {
  $.ajax({
    type : "DELETE",
    headers : {
      'Authorization' : 'Bearer ' + token
    },
    url : webapp_settings.apiserver_url + webapp_settings.apiserver_path + '/'
        + webapp_settings.apiserver_ver + '/tasks/' + job_id,
    dataType : "json",
    success : function(data) {
      $('#confirmJobDel').hide();
      $('#cancelJobDel').text('Continue');
      $('#confirmDelete').find('.modal-body p')
          .text('Successfully removed job');
      $('#jobTable').find('#' + job_id).next().remove();
      if (getNumJobs() > 0)
        $('#jobTable').find('#' + job_id).remove();
      else
        emptyJobTable();
      prepareJobTable();
    },
    error : function(jqXHR, textStatus, errorThrown) {
      alert(jqXHR.status);
    }
  });
}
/*
 * Fills the job table from incoming JSON data
 */
function fillJobTable(data, current) {
  $('#jobsDiv').html('');
  jobsTable = '<table id="jobTable" class="table table-responsive tablesorter">'
      + '	<colgroup>'
      + '		<col/>'
      + '		<col/>'
      + '		<col/>'
      + '		<col/>'
      + '		<col/>'
      + '		<col/>'
      + '	</colgroup>'
      + '	<thead>'
      + '           <tr>'
      + '               <th>Delete</th>'
      + '                <th>Submitted</th>'
      + '                <th>Modified</th>'
      + '                <th>Status</th>'
      + '                <th>Description</th>'
      + '                <th>Output</th>'
      + '            </tr>'
      + '	</thead>'
      + '      <tbody id="jobRecords">'
      + '      </tbody>'
      + '<tfoot style="size:0px">' + '</tfoot>' + '</table>';

  jobsTable += '<div id="pagination" align="center"><ul class="pagination pagination-sm">';
  for (var i = 0; i < (Jdiv + 1); i++) {
    jobsTable += '<li id="LI_' + i
        + '"><a href="javascript:void(0)" onClick=fillJobTable(jobsAll,' + i
        + ')>' + (i + 1) + '</a></li>';
  }
  jobsTable += '</ul></div>';

  // Add table
  $('#jobsDiv').append(jobsTable);
  for (var i = 0; i < data.length; i++) {
    if ((i >= current * jobLimit) && (i < (current + 1) * jobLimit)) {
      appendJobRecord(i, data[i], $('#jobRecords'));
    }
  }
  newLI = "LI_" + current;
  document.getElementById(LI).className = "";
  document.getElementById(newLI).className = "active";
  LI = newLI;
  // Compress childs
  $('.tablesorter-childRow td').hide();
  for ( var m in infoMap) {
    $('#job_info_' + m).toggle(true);
  }
  // Sort table
  /*
  $("#jobTable").tablesorter(
  {
      theme : 'blue',
      sortList: [[1,1]],
      cssChildRow: "tablesorter-childRow"
  }        
  ); 
  $('.tablesorter').delegate('.toggle', 'click' ,function(){
      $(this).closest('tr').nextUntil('tr:not(.tablesorter-childRow)').find('td').toggle();
      return false;
  });
   */
}
/*
 * Set empty job table             
 */
function emptyJobTable() {
  $('#jobsDiv').html('<small>No jobs available yet</small>');
}
/*
 * Calls the API Server to generate the Jobs table
 */
function prepareJobTable() {
  $('#jobsDiv').html('');
  $('#jobsDiv').attr('data-modify', 'false');
  $.ajax({
    type : "GET",
    headers : {
      'Authorization' : 'Bearer ' + token
    },
    url : webapp_settings.apiserver_url + webapp_settings.apiserver_path + '/'
        + webapp_settings.apiserver_ver + '/tasks?application='
        + webapp_settings.app_id,
    dataType : "json",
    success : function(data) {
      if (data.tasks.length > 0) {
        jobsAll = data.tasks;
        jobsListLength = data.tasks.length;
        Jdiv = Math.floor((jobsListLength - 1) / jobLimit);
        fillJobTable(data.tasks.sort(predicatBy("date")), 0);
      } else
        emptyJobTable();
    },
    error : function(jqXHR, textStatus, errorThrown) {
      alert(jqXHR.status);
    }
  });
}
/*
 * Helper function return in the number of jobs
 */
function getNumJobs() {
  return Math.floor(($('#jobTable tr').size() - 1) / 2);
}
/*
 * Function responsible of job submission
 */
function submit(job_desc, paramJson) {
  $('#submitButton').hide();
  job_failed = '<div class="alert alert-danger">'
      + '<strong>ERROR!</strong> Failed to submit job.' + '</div>';
  job_success = '<div class="alert alert-success">'
      + '<strong>Success!</strong> Job successfully sent.' + '</div>';
  job_warning = '<div class="alert alert-warning">'
      + '<strong>WARNING!</strong> Unable to get job details.' + '</div>';
  job_description = $('#jobDescription').val();
  $.ajax({
    url : webapp_settings.apiserver_url + webapp_settings.apiserver_path + '/'
        + webapp_settings.apiserver_ver + '/tasks',
    type : "POST",
    headers : {
      'Authorization' : 'Bearer ' + token
    },
    cache : false,
    dataType : "json",
    contentType : "application/json; charset=utf-8",
    data : JSON.stringify(job_desc),
    success : function(data) {
      // 2nd call to provide the parameter file and start submission
      var boundary = "---------------------------FG_Random_" + Math.random().toString(36).substr(2, 8);
      var body = '--' + boundary + '\r\n'
      + 'Content-Disposition: form-data; name="file[]";'
      + 'filename="' + parameterFile + '"\r\n'
      + 'Content-type: application/json\r\n\r\n'
      + JSON.stringify(paramJson) + '\r\n'
      + '--'+ boundary + '--';

      $.ajax({
        url : webapp_settings.apiserver_url + webapp_settings.apiserver_path
            + '/' + webapp_settings.apiserver_ver + '/tasks/' + data.id
            + '/input',
        method : 'POST',
        headers : {
          'Authorization' : 'Bearer ' + token
        },
        cache : false,
        contentType: "multipart/form-data; boundary="+boundary,
        data : body,
        success : function(data) {
          $('#jobTable').remove();
          prepareJobTable();
        },
        error : function(jqXHR, textStatus, errorThrown) {
          alert(jqXHR.status);
        }
      });
    },
    error : function(jqXHR, textStatus, errorThrown) {
      alert(jqXHR.status);
    }
  });
}
/*
 * Function that checks for job status change
 */
function checkJobs() {
  if(token) {
    $('#jobTable tr').each(
      function(i, row) {
        if (i > 0 // Starting after thead
            && i % 2 != 0 // Consider only odd rows (no childs)
        ) { // Consider only active states                        
          jstatus = row.cells[3].innerHTML;
          if (jstatus != 'DONE' && jstatus != 'FAILED' && jstatus != 'ABORT')
            $.ajax({
              url : webapp_settings.apiserver_url
                  + webapp_settings.apiserver_path + '/'
                  + webapp_settings.apiserver_ver + '/tasks/' + row.id,
              type : "GET",
              headers : {
                'Authorization' : 'Bearer ' + token
              },
              cache : false,
              contentType : "application/json; charset=utf-8",
              success : function(data) {
                jstatus = $('#' + data.id).find("td").eq(3).html();
                if (jstatus != data.jstatus) {
                  if (data.status == 'DONE')
                    prepareJobTable();
                  else
                    $('#' + data.id).find("td").eq(3).html(data.status);
                  $('#jobsDiv').attr('data-modify', 'true');
                }
              },
              error : function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status == 404) {
                  prepareJobTable();
                }
              }
            });
        }
      });
  }
  // Set timeout again for the next loop
  setTimeout(checkJobs, TimerDelay);
}
/*
 * Function that opens the submit modal frame
 */
function openModal() {
  var currentdate = new Date();
  var datetime = currentdate.getDate() + "/" + (currentdate.getMonth() + 1)
      + "/" + currentdate.getFullYear() + " @ " + currentdate.getHours() + ":"
      + currentdate.getMinutes() + ":" + currentdate.getSeconds();
  $('#submitButton').show();
  $('#modal-content').html('');
  $('#jobDescription').val('Job desc ' + datetime);
  $("#helloTesterModal").modal();
}
/*
 * App specific job submission call;
 * Just prepare the job_desc and call the submit() function             
 */
function submitJob() {
  var job_usrdesc = $('#jobDescription').val();
  var job_desc = {
    application : webapp_settings.app_id,
    description : job_usrdesc,
    output_files : [],
    input_files : [ {
      name : parameterFile
    } ]
  };
  var parameters = getParams();
  $.when.apply($, parameters.defers).done(function() {
    submit(job_desc, parameters.params);
  });
}
