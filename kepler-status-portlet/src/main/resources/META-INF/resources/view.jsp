<%--suppress LongLine --%>
<%@ include file="init.jsp" %>

<%@ page import="pl.psnc.indigo.fg.api.restful.jaxb.Task" %>
<%@ page import="pl.psnc.indigo.fg.api.restful.jaxb.TaskStatus" %>
<%@ page import="java.util.Collection" %>

<c:choose>

    <c:when test="${renderRequest.getAttribute('error') == null}">

        <%--@elvariable id="tasks" type="java.util.Collection<Task>"--%>

        <%

            final Collection<Task> tasks = (Collection<Task>) renderRequest.getAttribute("tasks");

        %>

        You have <c:out value="${tasks.size()}"/> tasks with runtime data.

        <div class="panel-group" id="accordion" role="tablist">

            <c:forEach var="task" items="${tasks}">

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="heading<c:out value="${task.id}"/>">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#task<c:out value="${task.id}"/>" aria-controls="task<c:out value="${task.id}"/>"><span class="glyphicon
                            <c:choose>
                                <c:when test="${task.status == 'DONE'}">
                                    glyphicon-ok
                                </c:when>
                                <c:when test="${task.status == 'ABORTED'}">
                                    glyphicon-remove
                                </c:when>
                                <c:otherwise>
                                    glyphicon-refresh
                                </c:otherwise>
                            </c:choose>"></span> ${task.lastChange} - <c:out value="${task.id}"/> - <c:out value="${task.status}"/><c:if test="${task.description.length() > 0}">
                                <br/>
                                <c:out value="${task.description}"/></c:if> </a>
                        </h4>
                    </div>

                    <div id="task<c:out value="${task.id}"/>" class="collapse panel-collapse" role="tabpanel" aria-labelledby="header<c:out value="${task.id}"/>">
                        <div class="panel-body">
                            <table class="table table-striped" style="width: 100%; table-layout: fixed">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Created</th>
                                    <th>Last changed</th>
                                    <th>Value</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach var="runtimeData" items="${task.runtimeData}">

                                    <tr>
                                        <td><c:out value="${runtimeData.name}"/></td>
                                        <td><c:out value="${runtimeData.creation}"/></td>
                                        <td><c:out value="${runtimeData.lastChange}"/></td>
                                        <td style="max-width: 25%;"><span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: inline-block; max-width: 100%;"><c:out value="${runtimeData.value}"/></span></td>
                                    </tr>

                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </c:forEach>

        </div>

    </c:when>

    <c:otherwise>

        <c:out value="${renderRequest.getAttribute('error')}"/>

    </c:otherwise>

</c:choose>
