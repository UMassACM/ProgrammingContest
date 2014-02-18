package contest.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import contest.user.UserManager;

public class ScoreboardServlet extends BaseVelocityServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getVelocityTemplate()
	{
		return "./velocity/scoreboard.vm";
	}

	@Override
	protected void initVelocityContext(HttpServletRequest req,
			HttpServletResponse res, VelocityContext context)
	{
		context.put("users", UserManager.getInstance().getAllUsers());
	}

}
