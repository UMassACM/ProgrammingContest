package contest.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;

import contest.config.Config;
import contest.user.*;


public class SigninVerifierServlet extends BaseVelocityServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getVelocityTemplate()
	{
		return "";
	}

	@Override
	protected void initVelocityContext(HttpServletRequest req,
			HttpServletResponse res, VelocityContext context)
	{
	}
	
	@Override
	protected void doRequest(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		String sUsername = req.getParameter("username");
		String sEmail = req.getParameter("email");
		
		User user = UserManager.getInstance().getUser(sUsername);
		if (user == null)
		{
			user = new User(sUsername, sEmail);
			UserManager.getInstance().addUser(sUsername, user);
			File userFolder = new File("./users/" + sUsername); 
			if (!userFolder.exists() && !userFolder.mkdir())
			{
				System.out.println("Failed to create directory, check permissions");
			}
			userFolder.setExecutable(true, false);
			userFolder.setReadable(true, false);
			userFolder.setWritable(true, false);
			int nNumProbs = Config.getInstance().getIntValue("num_problems");
			for (int i=1; i<=nNumProbs; i++)
			{
				File problemFolder = new File("./users/" + sUsername + "/" + String.valueOf(i)); 
				problemFolder.mkdir();
				problemFolder.setExecutable(true, false);
				problemFolder.setReadable(true, false);
				problemFolder.setWritable(true, false);
			}
		}
		
		HttpSession session = req.getSession(true);
		session.setAttribute("User", user);
		
		user.saveToFile();
		
		try
		{
			res.sendRedirect("/");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
