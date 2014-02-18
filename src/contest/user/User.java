package contest.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import contest.config.Config;

public class User implements Comparable<Object>
{
	private String m_sUsername;
	private String m_sEmail;
	private long[] m_nSolveTime;
	private int[] m_nSolveStatus;
	private String[] m_sErrorMessage;
	private int m_nPrefferedLang = 0;
	
	public User(String sUsername, String sEmail)
	{
		m_sUsername = sUsername;
		m_sEmail = sEmail;
		int nNumProblems = Config.getInstance().getIntValue("num_problems");
		m_nSolveTime = new long[nNumProblems];
		m_nSolveStatus = new int[nNumProblems];
		m_sErrorMessage = new String[nNumProblems];
		for (int i=0; i<nNumProblems; i++)
		{
			m_nSolveTime[i] = 0;
			m_nSolveStatus[i] = 0;
			m_sErrorMessage[i] = "";
		}
	}
	
	public User(String sUsername)
	{
		loadFromFile(sUsername);
	}
	
	public String getUsername()
	{
		return m_sUsername;
	}

	public String getEmail()
	{
		return m_sEmail;
	}
	
	public long getSolveTime(int nProbNum)
	{
		return m_nSolveTime[nProbNum];
	}
	
	public String getSolveTimeString(int nProbNum)
	{
		long nTotalMiliseconds = m_nSolveTime[nProbNum];
		int nHours = (int) (nTotalMiliseconds / 1000 / 60 / 60);
		nTotalMiliseconds %= 1000 * 60 * 60;
		int nMinutes = (int) (nTotalMiliseconds / 1000 / 60);
		nTotalMiliseconds %= 1000 * 60;
		int nSeconds = (int) (nTotalMiliseconds / 1000);
		return String.valueOf(nHours) + ":" + String.valueOf(nMinutes) + ":" + String.valueOf(nSeconds);
	}
	
	public void setSolveTime(int nProbNum, long nTime)
	{
		m_nSolveTime[nProbNum] = nTime;
	}
	
	public int getSolveStatus(int nProbNum)
	{
		return m_nSolveStatus[nProbNum];
	}
	
	public void setSolveStatus(int nProbNum, int nStatus)
	{
		if (m_nSolveStatus[nProbNum] != 3)
		{
			m_nSolveStatus[nProbNum] = nStatus;
			if (nStatus == 3)
			{
				long nTime = System.currentTimeMillis();
				long nStartTime = Config.getInstance().getLongValue("start_time");
				setSolveTime(nProbNum, nTime - nStartTime);
			}
		}
		saveToFile();
	}
	
	public long getTotalTime()
	{
		long nTotal = 0;
		for (int i=0; i<m_nSolveTime.length; i++)
		{
			nTotal += m_nSolveTime[i];
		}
		return nTotal;
	}
	
	public String getTotalTimeString()
	{
		long nTotalMiliseconds = getTotalTime();
		int nHours = (int) (nTotalMiliseconds / 1000 / 60 / 60);
		nTotalMiliseconds %= 1000 * 60 * 60;
		int nMinutes = (int) (nTotalMiliseconds / 1000 / 60);
		nTotalMiliseconds %= 1000 * 60;
		int nSeconds = (int) (nTotalMiliseconds / 1000);
		return String.valueOf(nHours) + ":" + String.valueOf(nMinutes) + ":" + String.valueOf(nSeconds);
	}
	
	public int getNumSolvedProblems()
	{
		int nTotalSolved = 0;
		for (int i=0; i<m_nSolveStatus.length; i++)
		{
			if (m_nSolveStatus[i] == 3)
			{
				nTotalSolved++;
			}
		}
		return nTotalSolved;
	}
	
	@Override
	public int compareTo(Object other)
	{
		User otherUser = (User)other;
		if (this.getNumSolvedProblems() > otherUser.getNumSolvedProblems())
		{
			return 1;
		}
		else if (this.getNumSolvedProblems() < otherUser.getNumSolvedProblems())
		{
			return -1;
		}
		else
		{
			return (int) (otherUser.getTotalTime() - this.getTotalTime());
		}
	}

	public String getErrorMessage(int nProbNum)
	{
		if (m_sErrorMessage[nProbNum].length() > 0)
		{
			return m_sErrorMessage[nProbNum];
		}
		else
		{
			return "Incorrect";
		}
	}

	public void setErrorMessage(int nProbNum, String sErrorMessage)
	{
		m_sErrorMessage[nProbNum] = sErrorMessage;
	}
	
	public void saveToFile()
	{
		String sFilePath = "./users/" + m_sUsername + "/info.txt";
		File file = new File(sFilePath);
		if (file.exists())
		{
			file.delete();
		}
		try{ 
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			if (m_sEmail.length() > 0)
			{
				out.write(m_sEmail + "\n");
			}
			else
			{
				out.write("noemail\n");
			}
			int nNumProblems = Config.getInstance().getIntValue("num_problems");
			for (int i=0; i<nNumProblems; i++)
			{
				out.write(String.valueOf(m_nSolveTime[i]) + "\n" + String.valueOf(m_nSolveStatus[i]) + "\n");
			}
			out.close();
		}catch (Exception e){
		}

	}
	
	private void loadFromFile(String sUsername)
	{
		m_sUsername = sUsername;
		String sFilePath = "./users/" + sUsername + "/info.txt";
		File file = new File(sFilePath);
		if (file.exists())
		{
		    try {
		    	BufferedReader in = new BufferedReader(new FileReader(file));
		        String line = in.readLine();
		        if (line.length() > 0)
		        {
		        	m_sEmail = line;
		        	int nNumProblems = Config.getInstance().getIntValue("num_problems");
		    		m_nSolveTime = new long[nNumProblems];
		    		m_nSolveStatus = new int[nNumProblems];
		    		m_sErrorMessage = new String[nNumProblems];
					for (int i=0; i<nNumProblems; i++)
					{
						m_nSolveTime[i] = Long.valueOf(in.readLine());
						m_nSolveStatus[i] = Integer.valueOf(in.readLine());
						m_sErrorMessage[i] = "";
					}
		        }
		        in.close();
		    } catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public int getPrefferedLang()
	{
		return m_nPrefferedLang;
	}

	public void setPrefferedLang(int nPrefferedLang)
	{
		this.m_nPrefferedLang = nPrefferedLang;
	}
}
