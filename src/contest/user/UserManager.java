package contest.user;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

public class UserManager
{
	private Map<String, User> hmUsers = new HashMap<String, User>();
	private static UserManager INSTANCE = null;
	
	public UserManager()
	{
		loadUsersFromSaves();
	}
	
	public static UserManager getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new UserManager();
		}
		return INSTANCE;
	}
	
	public User getUser(String sUsername)
	{
		if (hmUsers.containsKey(sUsername))
		{
			return hmUsers.get(sUsername);
		}
		else
		{
			return null;
		}
	}
	
	public void addUser(String sUsername, User user)
	{
		hmUsers.put(sUsername, user);
	}
	
	public User[] getAllUsers()
	{
		User[] users = new User[hmUsers.size()];
		int index = 0;
		for (Entry<String, User> entries : hmUsers.entrySet())
		{
			users[index++] = entries.getValue();
		}
		Arrays.sort(users);
		ArrayUtils.reverse(users);
		return users;
	}
	
	private void loadUsersFromSaves()
	{
		String sUserDir = "./users/";
		File userDir = new File(sUserDir);
		for (File user : userDir.listFiles())
		{
			String sUsername = user.getName();
			addUser(sUsername, new User(sUsername));
		}
	}
}
