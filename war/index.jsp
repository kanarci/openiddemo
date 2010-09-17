<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="cz.cvut.felk.via.examples.openid.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Set"%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>OpenID demo example</title>
</head>
<body>

<%
	final Map<String, String> openIdProviders;
	openIdProviders = new HashMap<String, String>();
	openIdProviders.put("Google", "google.com/accounts/o8/id");
	openIdProviders.put("Yahoo", "yahoo.com");
	openIdProviders.put("MySpace", "myspace.com");
	openIdProviders.put("AOL", "aol.com");
	openIdProviders.put("MyOpenId.com", "myopenid.com");

	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser(); // or req.getUserPrincipal()
	Set<String> attributes = new HashSet();
%>
<h1 align="center">OpenID demo</h1>
<h6 align="center">jsp</h6>
<table align="center">
	<tr>
		<td>
		<%
			if (user != null) {
		%> Hello <i> <%=user.getNickname()%></i>! [<a
			href="<%=userService.createLogoutURL("/openid")%>">sign out</a>] <br>
		Your auth. domain is : <%=user.getAuthDomain()%> <br>
		Your federal identity is : <%=user.getFederatedIdentity()%> <br>
		Your user id is : <%=user.getUserId()%> <br>
		Your mail is : <%=user.getEmail()%> <%
 	} else {
 %> Hello user, sign in at: <%
 	for (String providerName : openIdProviders.keySet()) {
 			String providerUrl = openIdProviders.get(providerName);
 			String loginUrl = userService.createLoginURL("/openid",
 					null, providerUrl, attributes);
 %>[<a href="<%=loginUrl.toString()%>"><%=providerName.toString()%></a>]
		<%
 	}
 	}
 %>
		</td>
	</tr>
</table>




</body>
</html>
