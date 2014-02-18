package contest.servlets;

import java.io.*;
import java.util.Scanner;

import javax.servlet.*;
import javax.servlet.http.*;

public class DefaultServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String resource = req.getRequestURI().toString();
		File file = new File("." + resource);
		Scanner fileScanner = new Scanner(file);
		String content = fileScanner.useDelimiter("\\Z").next();
		fileScanner.close();
		resp.getWriter().println(content);
	}
}
