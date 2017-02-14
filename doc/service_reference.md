---
description: This section provides the "Service Reference Card".
---

# Liferay IAM Connector - Service Reference Card


**Functional description:**
   Integrate OpenId Connect based authentication and authorisation in [Liferay 7.0 service][life]. The token has to be compliant with the specifications defined in INDIGO-Datacloud project and implemented in the IAM service.
   Allow the validation and distribution of token to other service (e.g. the FutureGateway API service)

**Services running:**
   * tomcat8: (Java application) needed to run Liferay


**Configuration:**
   * The module introduce a new panel in Liferay Configuration. This allow to provide the information for the OpenId provider. The more important are:
      * *User credentials:* ``id`` and ``secret`` provided during the registration of the service in the provider
      * *Well known OpenId Connect configuration:* as an example for iam-test instace it is `https://iam-test.indigo-datacloud.eu/.well-known/openid-configuration`
   * Registration in IAM:
      * *Return url:* `http(s)://<your_domain_name>/c/portal/iam_openidconnect`

**Logfile locations (and management) and other useful audit information:**
   * *Liferay log:* LiferayIAM will log in the Liferay log files. As default they are in the log folder of tomcat and in a log folder outside of the `CATALINA_HOME`

**Open ports:**
   * Liferay Server:
      * 80 and 443


**Where is service state held (and can it be rebuilt):**
   Configuration information are managed by Liferay which is responsible to keep the values across restart and/or during update of the module

**Cron jobs:**
   None

**Security information**
   * Secure the token: the token should never be sent in un-secure connection so all the Liferay page involved with the authentication should be accessed with `https`, including the the communication involving the OpenId Connect provider
   * User can be managed using the Liferay control panel

**Location of reference documentation:**
   [LiferayIAM on Gitbook](https://www.gitbook.com/book/indigo-dc/liferay-iam-connector/details)


[life]: http://www.liferay.com
