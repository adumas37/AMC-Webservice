package m2.hw;

public class Utilisateur {

	private String userName;
	private String project;

    public Utilisateur(String username) {
    	userName=username;
    }

    public String getUserName(){
    	return userName;
    }
    
    public String getProject(){
    	return project;
    }
    public void setProject(String project){
    	this.project=project;
    }
    
}