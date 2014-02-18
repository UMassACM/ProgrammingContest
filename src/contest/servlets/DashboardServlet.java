package contest.servlets;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;

import contest.config.Config;
import contest.user.User;

public class DashboardServlet extends BaseVelocityServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getVelocityTemplate()
	{
		return "./velocity/dashboard.vm";
	}

	@Override
	protected void initVelocityContext(HttpServletRequest req,
			HttpServletResponse res, VelocityContext context)
	{
		long nCurTime = System.currentTimeMillis();
		long nEndTime = Config.getInstance().getLongValue("end_time");
		String sRemainingTime = String.format("%d minutes, %d seconds", 
			    TimeUnit.MILLISECONDS.toMinutes(nEndTime-nCurTime),
			    TimeUnit.MILLISECONDS.toSeconds(nEndTime-nCurTime) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(nEndTime-nCurTime))
			);
		context.put("contestOpen", (nEndTime-nCurTime) >= 0);
		context.put("remainingTime", "Time remaining " + sRemainingTime);
		
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("User");
		if (user != null)
		{
			context.put("user", user);
		}
		else
		{
			try
			{
				res.sendRedirect("/");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
