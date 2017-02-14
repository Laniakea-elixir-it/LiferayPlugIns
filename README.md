# LiferayIAM

LiferayIAM is a set of Liferay modules for authentication using the INDIGO-DATACLOUD IAM service.
In the INDIGO-DATACLOUD infrastructure model, IAM is the central authentication and authorisation
service and it has been developed inside the project to support the requirements of the supported communities.
It uses OpenID connect protocol for the authentication and OAuth for the authorisation.
More details are available in the [official documentation][1]. Therefore, all the software tools deployed for
the project must accept the IAM token directly or after a translation performed using the
[Token Translation Service][2] developed in the project.


Liferay is a web application framework and it is the main technology selected inside the
Work Package 6 to build the community portals. The functionalities provided by Liferay
make easier to build complex portals integrating different applications. These
allow users to exploit the services provided by INDIGO-DATACLOUD for their activities.
Since the portal will be the access point toward the developed services, users have to get the IAM authorisation
tokens on the portal and use it to communicate with other services.

The modules described in this guide add in Liferay the authentication, using OpenID Connect protocol,
and the management of the additional information provided by IAM such as
the user groups and others. Additionally, the modules made available the access
token for application running in the portal, or external to the portal, using the
Liferay remote APIs.


The code of the modules is based on some of the modules provided with Liferay 7.0
for the authentication with Facebook and Google and their configuration and management
is very similar.

[1]: https://www.gitbook.com/book/indigo-dc/iam/details
[2]: https://www.gitbook.com/book/indigo-dc/token-translation-service/details
