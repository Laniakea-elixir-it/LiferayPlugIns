---
description: This section describes the element added to Liferay User Interface from the point of view of the user.
---

# User Guide

When the LiferayIAM modules are deployed and properly configured the user interface change in few component. The login portlet
will include, among the other enabled protocols, the new *INDIGO AAI* authentication method, as shown in the following picture:


![Login portlet with INDIGO AAI authentication](img/LogIn.png)


If a user clicks on *INDIGO AAI* a pop-up window is created with the IAM login page. After the login the pop-up page
is automatically closed and the user is authenticated in the portal.

When a new user try to access the portal, he/she has to provide the consensus to release the tokens to the Liferay
based portal. This request is performed twice: once for the access token and the other for the refresh token.
Following authentications will not require the user consensus. This will be requested again if different scopes are requested.

Finally, the user can verify the tokens released by IAM going to his/her personal account page. In the *My Account* page,
under the tab *Miscellaneous* there are the *Custom Fields* attributes. IAM tokens nad user subject are among the custom fields
as shown in the following picture:

![INDIGO AAI tokens](img/CustField.png)

