package cz.cvut.felk.via.examples.openid;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class OpenIDServlet extends HttpServlet {

	private static final Map<String, String> openIdProviders;
	static {
		openIdProviders = new HashMap<String, String>();
		openIdProviders.put("Google", "google.com/accounts/o8/id");
		openIdProviders.put("Yahoo", "yahoo.com");
		openIdProviders.put("MySpace", "myspace.com");
		openIdProviders.put("AOL", "aol.com");
		openIdProviders.put("MyOpenId.com", "myopenid.com");
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser(); // or req.getUserPrincipal()
		Set<String> attributes = new HashSet();

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		out.write("<h1 align=\"center\">OpenID demo</h1>");
		out.write("<h6 align=\"center\">servlet</h6>");
		out.write("<table align=\"center\"><tr><td>");

		if (user != null) {
			out.print("Hello <i>" + user.getNickname() + "</i>! ");
			out.print("[<a href=\""
					+ userService.createLogoutURL(req.getRequestURI())
					+ "\">sign out</a>]");
			out.print("<br> Your auth. domain is : " + user.getAuthDomain());
			out.print("<br> Your federal identity is : "
					+ user.getFederatedIdentity());
			out.print("<br> Your user id is : " + user.getUserId());
			out.print("<br> Your mail is : " + user.getEmail());

		} else {
			out.println("Hello world! Sign in at: ");
			for (String providerName : openIdProviders.keySet()) {
				String providerUrl = openIdProviders.get(providerName);
				String loginUrl = userService.createLoginURL(req
						.getRequestURI(), null, providerUrl, attributes);
				out.println("[<a href=\"" + loginUrl + "\">" + providerName
						+ "</a>] ");
			}
		}
		out.write("</td></tr></table>");

	}
}
