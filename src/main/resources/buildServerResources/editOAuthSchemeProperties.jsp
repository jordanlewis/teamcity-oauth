<%@ page import="jetbrains.buildServer.auth.oauth.ConfigKey" %>
<%@ include file="/include-internal.jsp"%>
<%@ taglib prefix="prop" tagdir="/WEB-INF/tags/props"%>
<div><jsp:include page="/admin/allowCreatingNewUsersByLogin.jsp"/></div>
<br/>
<div>
    <label width="100%" for="<%=ConfigKey.authorizeEndpoint%>">OAuth 2.0 authorization endpoint:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.authorizeEndpoint.toString()%>"/><br/>
    <span class="grayNote">Endpoint at which TeamCity server can obtain an authorization code using OAuth 2.0.</span>
</div>
<div>
    <label width="100%" for="<%=ConfigKey.tokenEndpoint%>">OAuth 2.0 token endpoint:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.tokenEndpoint.toString()%>"/><br/>
    <span class="grayNote">Endpoint at which TeamCity server can obtain an token using OAuth 2.0.</span>
</div>
<div>
    <label width="100%" for="<%=ConfigKey.userEndpoint%>">OAuth 2.0 user endpoint:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.userEndpoint.toString()%>"/><br/>
    <span class="grayNote">Endpoint at which TeamCity server can obtain information about user</span>
</div>
<div>
    <label width="100%" for="<%=ConfigKey.allowMatchingUsersByEmail%>">Allow matching users by email:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.allowMatchingUsersByEmail.toString()%>"/><br/>
    <span class="grayNote">Allow matching OAuth users by email address</span>
</div>
<div>
    <label width="100%" for="<%=ConfigKey.emailsEndpoint%>">OAuth 2.0 emails endpoint:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.emailsEndpoint.toString()%>"/><br/>
    <span class="grayNote">Endpoint at which TeamCity server obtain all email addresses for user</span>
</div>
<div>
    <label width="100%" for="<%=ConfigKey.newUserEmailSuffix%>">Allowed email suffixes for new OAuth users:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.newUserEmailSuffix.toString()%>"/><br/>
    <span class="grayNote">Optional email address suffix to limit automatic account creation to</span>
</div>
<br/>
<div>
    <label width="100%" for="<%=ConfigKey.clientId%>">Client ID:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.clientId.toString()%>"/><br/>
    <span class="grayNote">OAuth client identifier of this TeamCity server.</span>
</div>
<div>
    <label width="100%" for="<%=ConfigKey.clientSecret%>">Client Secret:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.clientSecret.toString()%>"/><br/>
    <span class="grayNote">OAuth client secret of this TeamCity server.</span>
</div>
<div>
    <label width="100%" for="<%=ConfigKey.scope%>">Scope:</label><br/>
    <prop:textProperty style="width: 100%;" name="<%=ConfigKey.scope.toString()%>"/><br/>
    <span class="grayNote">OAuth scope of this TeamCity server.</span>
</div>