---
description: This section provides the "Service Reference Card".
---

# LiferayPlugins  - Service Reference Card


**Functional description:**
   Provide support to several INDIGO-DataCloud services:
   * *IAM*: the modules integrate OpenId Connect based authentication and authorisation in [Liferay 7.0 service][life] compliant with the specifications defined in INDIGO-Datacloud project and implemented by the IAM service. Additionally, the modules allow the validation and distribution of token to other service (e.g. the FutureGateway API service)
   * *FutureGateway*: a new administration panel is created in the Liferay control panel to manage the resources stored in the FutureGateway service

**Services running:**
   * tomcat8: (Java application) needed to run Liferay


**Configuration:**
   * The modules introduce a new panel in Liferay Configuration. This allow to provide the information for the OpenId provider. The more important are:
      * *User credentials:* ``id`` and ``secret`` provided during the registration of the service in the provider
      * *Well known OpenId Connect configuration:* as an example for iam-test instace it is `https://iam-test.indigo-datacloud.eu/.well-known/openid-configuration`
   * Registration in IAM:
      * *Return url:* `http(s)://<your_domain_name>/c/portal/iam_openidconnect`
   * A new panel is create in the control panel for the FutureGateway administration. The url of the FutureGateway has to be provided. Additionally, right to access the new panel should be provided to selected users


**Logfile locations (and management) and other useful audit information:**
   * *Liferay log:* LiferayPlugins will log in the Liferay log files. As default they are in the log folder of tomcat and in a log folder outside of the `CATALINA_HOME`

**Open ports:**
   * Liferay Server:
      * 80 and 443


**Where is service state held (and can it be rebuilt):**
   Configuration information are managed by Liferay which is responsible to keep the values across restart and/or during update of the module

**Cron jobs:**
   None

**Security information**
   * Secure the token: the token should never be sent in un-secure connection so all the Liferay page involved with the authentication should be accessed with `https`, including the communication involving the OpenId Connect provider
   * User can be managed using the Liferay control panel

**Location of reference documentation:**
   [LiferayPlugins on Gitbook](https://www.gitbook.com/book/indigo-dc/liferay-plugins/details)


[life]: http://www.liferay.com
