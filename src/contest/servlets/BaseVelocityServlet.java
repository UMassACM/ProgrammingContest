package contest.servlets;
import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import contest.config.Config;


public abstract class BaseVelocityServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected abstract String getVelocityTemplate();
	
	protected abstract void initVelocityContext(HttpServletRequest req, HttpServletResponse res, VelocityContext context);
	
	protected void doRequest(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		VelocityContext context = new VelocityContext();
		context.put("config", Config.getInstance());
		initVelocityContext(req, res, context);
		StringWriter w = new StringWriter();
		Velocity.mergeTemplate(getVelocityTemplate(), "utf-8", context, w);

		res.getWriter().print(w);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		doRequest(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doRequest(req, resp);
	}
}
