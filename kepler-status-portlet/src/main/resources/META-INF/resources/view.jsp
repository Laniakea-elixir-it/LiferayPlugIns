<%@ include file="/init.jsp" %>

<%= renderRequest.getAttribute("GREETER_MESSAGE") %>

<liferay-ui:message key="blade_portlet_actioncommand_GreeterPortlet.caption"/>

<liferay-portlet:actionURL name="greet" var="greetURL"/>

<aui:form action="<%= greetURL %>" method="post" name="fm">

    <aui:input name="name" type="text"/>

    <aui:button-row>

        <aui:button type="submit">

        </aui:button>

    </aui:button-row>

</aui:form>
