package contest.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config
{
	private Map<String, String> hmConfigValues = new HashMap<String, String>();
	private static Config INSTANCE = null;
	
	public Config()
	{
		loadFromFile("./config.txt");
	}
	
	public static Config getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new Config();
		}
		return INSTANCE;
	}
	
	public int getIntValue(String key)
	{
		return Integer.parseInt(hmConfigValues.get(key));
	}
	
	public long getLongValue(String key)
	{
		return Long.parseLong(hmConfigValues.get(key));
	}
	
	public String getStringValue(String key)
	{
		return hmConfigValues.get(key);
	}
	
	public void loadFromFile(String sFilename)
	{
		File file = new File(sFilename);
		if (file.exists())
		{
		    try {
		    	BufferedReader in = new BufferedReader(new FileReader(file));
		        String line;
		        while ((line = in.readLine()) != null)
		        {
		        	String[] items = line.split(" ");
		        	if (items.length == 2)
		        	{
		        		hmConfigValues.put(items[0], items[1]);
		        	}
		        }
		        in.close();
		    } catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
