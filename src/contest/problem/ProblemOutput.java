package contest.problem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import contest.config.Config;

public class ProblemOutput
{
	private List<List<String>> m_problemOutput = new ArrayList<List<String>>();
	
	public ProblemOutput()
	{
		int nNumProblems = Config.getInstance().getIntValue("num_problems");
		for (int i=0; i<nNumProblems; i++)
		{
			m_problemOutput.add(new ArrayList<String>());
		}
	}
	
	public void addProblemOutput(int nProblemId, String sOutput)
	{
		m_problemOutput.get(nProblemId).add(sOutput);
	}
	
	public List<String> getProblemOutput(int nProblemId)
	{
		return m_problemOutput.get(nProblemId);
	}
	
	public void initProblemsFromFiles()
	{
		int nNumProblems = Config.getInstance().getIntValue("num_problems");
		for (int i=0; i<nNumProblems; i++)
		{
			String sFileDirectory = "./problems/" + String.valueOf(i+1) + "/";
			int nFileNum = 1;
			while(true)
			{
				String sFilename = "output" + String.valueOf(nFileNum) + ".txt";
				File file = new File(sFileDirectory + sFilename);
				if (file.exists())
				{
					try
					{
						Scanner fileScanner = new Scanner(file);
						String output = fileScanner.useDelimiter("\\Z").next();
						addProblemOutput(i, output);
						fileScanner.close();
					} catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					break;
				}
				nFileNum++;
			}
		}
	}
	
	public boolean compareProblem(int nProbNum, ProblemOutput otherProblem)
	{
		List<String> sSource = this.getProblemOutput(nProbNum);
		List<String> sOtherSource = otherProblem.getProblemOutput(nProbNum);
		for (int i=0; i<sSource.size(); i++)
		{
			if (!sSource.get(i).trim().equals(sOtherSource.get(i).trim()))
			{
				return false;
			}
		}
		return true;
	}
}
