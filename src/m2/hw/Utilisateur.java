package m2.hw;

public class Utilisateur {

	private final String PATH=System.getProperty("user.home")+"/Projets-QCM/";
	private String userName;
	private String project;
	private String projectPath;
	private String action;
	
	/**
	 * Constructeur 
	 * @param username
	 */
    public Utilisateur(String username) {
    	userName=username;
    	//TODO intialiser les autres variables avec un projet existant au cas ou l'utilisateur fasse
    	//n'importe quoi...
    }
    /**
     * Getter du username
     * @return
     */
    public String getUserName(){
    	return userName;
    }
    /**
     * Getter du projet en cours
     * @return
     */
    public String getProject(){
    	return project;
    }
    /**
     * Setter du projet
     * @param project
     */
    public void setProject(String project){
    	this.project=project;
    	this.projectPath=PATH+userName+"/"+project+"/";
    }
    /**
     * Getter du chemin du projet sous la forme: "chemin/du/projet/"
     * @return
     */
    public String getProjectPath(){
    	return this.projectPath;
    }
    /**
     * Getter de l'action en cours
     * @return
     */
    public String getAction(){
    	return this.action;
    }
    /**
     * Setter de l'action en cours
     * @param action
     */
    public void setAction(String action){
    	this.action=action;
    }
    /**
     * Getter du chemin du dossier contenant les projet de l'utilisateur
     * @return
     */
    public String getProjectsPath(){
    	return this.PATH+userName+"/";
    }
    
}