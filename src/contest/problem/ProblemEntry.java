package contest.problem;

import contest.user.*;

public class ProblemEntry
{
	private final User m_user;
	private final int m_nProbNum;
	private final String m_sLanguage;
	private final String m_sCodeSource;
	
	public ProblemEntry(User user, int nProbNum, String sLanguage, String sCodeSource)
	{
		m_user = user;
		m_nProbNum = nProbNum;
		m_sLanguage = sLanguage;
		m_sCodeSource = sCodeSource;
	}

	public User getUser()
	{
		return m_user;
	}

	public int getProbNum()
	{
		return m_nProbNum;
	}

	public String getLanguage()
	{
		return m_sLanguage;
	}

	public String getCodeSource()
	{
		return m_sCodeSource;
	}
	
}
