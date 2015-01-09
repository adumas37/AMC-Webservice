package m2.hw;

public class Utilisateur {

	private final String PATH="Projets-QCM/";
	private String userName;
	private String project;
	private String projectPath;

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
    	this.projectPath=PATH+project+"/";
    }
    public String getProjectPath(){
    	return this.projectPath;
    }
    
}