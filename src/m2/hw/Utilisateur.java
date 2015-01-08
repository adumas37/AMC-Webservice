package m2.hw;

public class Utilisateur {

	private static String userName;
	private static String project;

    public Utilisateur(String username) {
    	userName=username;
    }

    public static String getUserName(){
    	return userName;
    }
    
    public static String getProject(){
    	return project;
    }
    
}