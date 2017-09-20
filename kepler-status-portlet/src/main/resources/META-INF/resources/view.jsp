<%--suppress LongLine --%>
<%@ include file="init.jsp" %>

<c:choose>

    <c:when test="${renderRequest.getAttribute('error') == null}">

        <div class="input-group">
            <input id="applicationName" class="form-control" name="applicationName" placeholder="Application Name"/>
            <div class="input-group-btn">
                <button id="applicationNameButton" class="btn btn-default">
                    <i class="glyphicon glyphicon-search"></i>
                </button>
            </div>
        </div>

        <div id="infoBox" class="alert alert-info" hidden="hidden"><span id="infoMessage"></span></div>
        <div id="dangerBox" class="alert alert-danger" hidden="hidden"><span id="dangerMessage"></span></div>

        <div class="panel-group" id="accordion" role="tablist"></div>

        <script type="application/javascript">
          $('#applicationNameButton').click(function() {
            var applicationName = $('#applicationName').val();
            var token = "<c:out value="${renderRequest.getAttribute('token')}"/>";
            var futuregatewayUri = "<c:out value="${renderRequest.getAttribute('futuregatewayUri')}"/>";
            var applicationId = null;

            $('#accordion').empty();
            $('#applicationName').prop('disabled', true);
            $('#applicationNameButton').prop('disabled', true);

            $.ajax({
              method: 'GET',
              url: futuregatewayUri + '/applications',
              headers: {
                Authorization: 'Bearer ' + token
              },
              cache: true,
              success: function(data) {
                $.each(data.applications, function() {
                  if (this.name === applicationName) {
                    applicationId = this.id;
                  }
                });

                if (applicationId === null) {
                  $('#dangerMessage').text('Failed to find application: ' + applicationName);
                  $('#infoBox').attr('hidden', 'hidden');
                  $('#dangerBox').removeAttr('hidden');
                  return;
                }

                $('#infoMessage').text('Found application, retrieving task list...');
                $('#dangerBox').attr('hidden', 'hidden');
                $('#infoBox').removeAttr('hidden');

                $.ajax({
                  method: 'GET',
                  url: futuregatewayUri + '/tasks?application=' + applicationId,
                  headers: {
                    Authorization: 'Bearer ' + token
                  },
                  cache: true,
                  success: function(data) {
                    $.each(data.tasks, function() {
                      var tbody = $('<tbody/>');

                      $.ajax({
                        method: 'GET',
                        url: futuregatewayUri + '/tasks/' + this.id,
                        headers: {
                          Authorization: 'Bearer ' + token
                        },
                        cache: true,
                        success: function(task) {
                          $.each(task.runtime_data, function() {
                            tbody.append($('<tr/>')
                              .append($('<td>' + this.name + '</td>'))
                              .append($('<td>' + this.creation + '</td>'))
                              .append($('<td>' + this.lastChange + '</td>'))
                              .append($('<td style="max-width: 25%"/>')
                                .append($('<span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: inline-block; max-width: 100%">' + this.value + '</span>"'))));
                          });
                        }
                      });

                      var icon;
                      if (this.status === 'DONE') {
                        icon = 'glyphicon-ok';
                      } else if (this.status === 'ABORTED') {
                        icon = 'glyphicon-remove';
                      } else {
                        icon = 'glyphicon-refresh';
                      }

                      var description = this['last_change'] + ' - ' + this.id;
                      if (this.description !== '') {
                        description += ' - ' + this.description;
                      }

                      $('#accordion')
                        .append($('<div class="panel panel-default"/>')
                          .append($('<div class="panel-heading"/>')
                            .append($('<h4 class="panel-title"/>')
                              .append($('<a data-toggle="collapse" href="#task' + this.id + '"/>')
                                .append($('<span class="glyphicon ' + icon + '"/>'))
                                .append($('<span> ' + description + '</span>')))))
                          .append($('<div id="task' + this.id + '" class="collapse panel-collapse"/>')
                            .append($('<div class="panel-body"/>')
                              .append($('<table class="table table-striped" style="width: 100%; table-layout: fixed"/>')
                                .append($('<thead/>')
                                  .append($('<tr/>')
                                    .append($('<th>Name</th>'))
                                    .append($('<th>Created</th>'))
                                    .append($('<th>Last changed</th>'))
                                    .append($('<th>Value</th>'))))
                                .append(tbody)))));
                    });

                    $('#infoMessage').text('Task list successfully retrieved');
                    $('#dangerBox').attr('hidden', 'hidden');
                    $('#infoBox').removeAttr('hidden');
                  }
                });
              },
              complete: function() {
                $('#applicationName').prop('disabled', false);
                $('#applicationNameButton').prop('disabled', false);
              }
            });
          });
        </script>

    </c:when>

    <c:otherwise>

        <c:out value="${renderRequest.getAttribute('error')}"/>

    </c:otherwise>

</c:choose>
