package contest.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;

import contest.user.User;

public class HelloServlet extends BaseVelocityServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getVelocityTemplate() {
		return "./velocity/testtemplate.vm";
	}

	@Override
	protected void initVelocityContext(HttpServletRequest req, HttpServletResponse res, VelocityContext context)
	{
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("User");
		if (user != null)
		{
			context.put("name", user.getUsername());
			context.put("email", user.getEmail());
		}
	}
}