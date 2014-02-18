package contest.problem;

public class ProblemManager
{
	private static ProblemManager INSTANCE = null;
	private ProblemOutput m_correctOutput = new ProblemOutput();
	
	public ProblemManager()
	{
		m_correctOutput.initProblemsFromFiles();
	}
	
	public static ProblemManager getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new ProblemManager();
		}
		return INSTANCE;
	}
	
	public ProblemOutput getProblemOutput()
	{
		return m_correctOutput;
	}
}
