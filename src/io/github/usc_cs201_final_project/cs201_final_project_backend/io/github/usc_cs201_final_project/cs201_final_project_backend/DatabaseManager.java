package io.github.usc_cs201_final_project.cs201_final_project_backend;


public class DatabaseManager {
	
	public DatabaseManager() {}
	
	/**
	 * Returns a randomly generated word from the word list
	 * @return
	 */
	//added these functions if you could implement
	public String getWord() {
		return "hello";
	}
	
	/**
	 * Returns the costume ID saved by the user
	 * 
	 * @param username	player's username
	 * @return			the costumeID stored in the database for username
	 */
	public Integer getCostumeID(String username) {
		return 0;
	}
	
	public void changeCostumeID(String username, int costumeID)
	{
		
	}
	
	public boolean userExists(String user)
	{
		return false;
	}
	public boolean authenticateUser(String user, String password)
	{
		
		return false;
	}
	public void createUser(String user, String password)
	{
		
	}

}