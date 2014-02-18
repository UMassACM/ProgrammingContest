package contest.problem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import contest.user.User;

public class PythonCompiler implements Compiler
{
	@Override
	public void compileProblemEntry(ProblemEntry problemEntry)
	{
		User user = problemEntry.getUser();
        int nProbNum = problemEntry.getProbNum() + 1;
        String sSource = problemEntry.getCodeSource();
        String sFileDir = "./users/" + user.getUsername() + "/" + String.valueOf(nProbNum) + "/";
        String sProbName = "prob" + String.valueOf(nProbNum);
        String sFilename = sFileDir + sProbName + ".py";
		try
		{
			File file = new File(sFilename);
			if (file.exists())
			{
				file.delete();
			}
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(sFilename));
			out.write(sSource);
			out.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			// Generate output from input files
			ProblemOutput correctOutput = ProblemManager.getInstance().getProblemOutput();
			int nNumInput = correctOutput.getProblemOutput(nProbNum-1).size();
			ProblemOutput compiledOutput = new ProblemOutput();
			for (int i=0; i<nNumInput; i++)
			{
				String sInputPath = "./problems/" + String.valueOf(nProbNum) + "/input" + String.valueOf(i+1) + ".txt";
				String[] sCommand = {
						"/bin/sh",
						"-c",
						"cat " + sInputPath + " | python "+ sFilename
				};
				Runtime r = Runtime.getRuntime();
				Process pRun = r.exec(sCommand);
				if (!ProcessRunner.waitForOrKill(pRun, 1000*60*2))
				{
					user.setSolveStatus(nProbNum-1, 2);
					user.setErrorMessage(nProbNum-1, "Timeout");
					return;
				}
				BufferedReader b = new BufferedReader(new InputStreamReader(pRun.getInputStream()));
				StringBuilder programOutput = new StringBuilder();
				
				String line = "";
				while ((line = b.readLine()) != null) {
					programOutput.append(line + "\n");
				}
				String sOutput = programOutput.toString();
				compiledOutput.addProblemOutput(nProbNum-1, sOutput);
				if (programOutput.toString().length() == 0)
				{
					user.setErrorMessage(nProbNum-1, "Syntax Error (No Output)");
				}
				else
				{
					user.setErrorMessage(nProbNum-1, "Incorrect Output");
				}
			}
			if (compiledOutput.compareProblem(nProbNum-1, correctOutput))
			{
				user.setSolveStatus(nProbNum-1, 3);
			}
			else
			{
				user.setSolveStatus(nProbNum-1, 2);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
