package contest.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;

import contest.problem.*;
import contest.user.*;

public class CompileProgramServlet extends BaseVelocityServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getVelocityTemplate()
	{
		return "./velocity/empty.vm";
	}

	@Override
	protected void initVelocityContext(HttpServletRequest req,
			HttpServletResponse res, VelocityContext context)
	{
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("User");
		if (user != null)
		{
			ProblemEntry problemEntry = new ProblemEntry(user, 
					Integer.valueOf(req.getParameter("prob")),
					req.getParameter("lang"),
					req.getParameter("code"));
			ProblemEntryValidifierThread problemThread = new ProblemEntryValidifierThread(problemEntry);
			problemThread.start();
			try
			{
				res.sendRedirect("/dashboard");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
