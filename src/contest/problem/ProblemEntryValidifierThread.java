package contest.problem;

import contest.config.Config;
import contest.user.*;

public class ProblemEntryValidifierThread extends Thread
{
	ProblemEntry m_problemEntry;
	
	public ProblemEntryValidifierThread(ProblemEntry problemEntry) {
    	m_problemEntry = problemEntry;
    }

    public void run() {
		long nCurTime = System.currentTimeMillis();
		long nEndTime = Config.getInstance().getLongValue("end_time");
		if (nCurTime > nEndTime)
		{
			return;
		}
        User user = m_problemEntry.getUser();
        int nProbNum = m_problemEntry.getProbNum();
        String sLang = m_problemEntry.getLanguage();
        
        user.setSolveStatus(nProbNum, 1);
        
        // Compile program and test it
        Compiler compiler;
        if (sLang.equals("C++"))
        {
        	compiler = new CPPCompiler();
        	user.setPrefferedLang(1);
        }
        else if (sLang.equals("Python"))
        {
        	compiler = new PythonCompiler();
        	user.setPrefferedLang(2);
        }
        else
        {
        	compiler = new JavaCompiler();
        	user.setPrefferedLang(0);
        }
        compiler.compileProblemEntry(m_problemEntry);
    }
}
