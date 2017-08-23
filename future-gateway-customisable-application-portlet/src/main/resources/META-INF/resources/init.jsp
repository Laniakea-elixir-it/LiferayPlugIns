<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page isELIgnored="false" %>
<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    String appId = portletPreferences.getValue("applicationId", null);
    String fConverter = portletPreferences.getValue("fileConverter", null);
    String jsonApp = portletPreferences.getValue("jsonApp", "").replace(System.getProperty("line.separator"), "");
    String parameterFile = "parameters.json";
%>

<portlet:resourceURL var="resourceURL"/>
