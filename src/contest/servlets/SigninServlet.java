package contest.servlets;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;

import contest.config.Config;
import contest.user.User;

public class SigninServlet extends BaseVelocityServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getVelocityTemplate()
	{
		return "./velocity/signin.vm";
	}

	@Override
	protected void initVelocityContext(HttpServletRequest req, HttpServletResponse res, VelocityContext context)
	{
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("User");
		if (user != null)
		{
			try
			{
				res.sendRedirect("/dashboard");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			long nCurTime = System.currentTimeMillis();
			long nOpenTime = Config.getInstance().getLongValue("start_time");
			long nEndTime = Config.getInstance().getLongValue("end_time");
			String sRemainingTime = String.format("%d minutes, %d seconds", 
				    TimeUnit.MILLISECONDS.toMinutes(nOpenTime-nCurTime),
				    TimeUnit.MILLISECONDS.toSeconds(nOpenTime-nCurTime) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(nOpenTime-nCurTime))
				);
			context.put("contestOpen", (nCurTime-nOpenTime) >= 0);
			context.put("contestOver", (nCurTime-nEndTime) >= 0);
			context.put("remainingTime", "Time remaining " + sRemainingTime);
		}
	}

}
