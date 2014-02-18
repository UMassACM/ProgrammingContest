package contest;
 
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
 
import org.apache.velocity.app.Velocity;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.servlet.ServletContextHandler;

import contest.config.*;
import contest.problem.ProblemManager;
import contest.servlets.*;

public class ProgrammingContest{
	private static final int N_PORT_ID = Config.getInstance().getIntValue("port");
	
	public static void main(String[] args) throws Exception
	{
		Velocity.init();
		ProblemManager.getInstance();
		Map<String, String> hmClassToRoute = new HashMap<String, String>();
		hmClassToRoute.put(HelloServlet.class.getName(), "/test");
		hmClassToRoute.put(SigninServlet.class.getName(), "/");
		hmClassToRoute.put(SigninVerifierServlet.class.getName(), "/signin");
		hmClassToRoute.put(DashboardServlet.class.getName(), "/dashboard");
		hmClassToRoute.put(ScoreboardServlet.class.getName(), "/scoreboard");
		hmClassToRoute.put(CompileProgramServlet.class.getName(), "/compileprogram");
		hmClassToRoute.put(ProblemServlet.class.getName(), "/problem");
		hmClassToRoute.put(DefaultServlet.class.getName(), "/resource/*");
		try
		{
			Server server = new Server(N_PORT_ID);
			ServletContextHandler servlets = new ServletContextHandler(ServletContextHandler.SESSIONS);
			
			for (Entry<String, String> entry : hmClassToRoute.entrySet()) {
				servlets.addServlet(entry.getKey(), entry.getValue());
			}
		    
		    server.setHandler(servlets);
		    HashSessionManager sessionManager = new HashSessionManager();
		    server.setSessionIdManager(sessionManager.getSessionIdManager());
		    
			server.start();
			server.join();
		}
		catch (BindException e)
		{
			System.out.println("Server Already Up");
		}
	}
}
