package contest.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

public class ProblemServlet extends BaseVelocityServlet
{
	private static final long serialVersionUID = 1L;
	private String sVelocityTemplate = "./velocity/problem1.vm";

	@Override
	protected String getVelocityTemplate()
	{
		return sVelocityTemplate;
	}

	@Override
	protected void initVelocityContext(HttpServletRequest req,
			HttpServletResponse res, VelocityContext context)
	{
		String sProbNum = req.getParameter("num");
		sVelocityTemplate = "./velocity/problem" + sProbNum + ".vm";
	}

}
