package m2.hw;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("navigation")
public class Navigation {

	/**
	 * Permet de changer la valeur de l'action pour l'utilisateur actuel
	 */
	@Path("ouverture")
	@POST
	public void setOuverture(){
		Utilisateurs.getCurrentUser().setAction("ouverture");
	}
	/**
	 * Permet de changer la valeur de l'action pour l'utilisateur actuel
	 */
	@Path("correction")
	@POST
	public void setCorrection(){
		Utilisateurs.getCurrentUser().setAction("correction");
	}
	/**
	 * Permet de changer la valeur de l'action pour l'utilisateur actuel
	 */
	@Path("creation")
	@POST
	public void setCreation(){
		Utilisateurs.getCurrentUser().setAction("creation");
	}
	/**
	 * Permet de changer la valeur de l'action pour l'utilisateur actuel
	 */
	@Path("suppression")
	@POST
	public void setSuppression(){
		Utilisateurs.getCurrentUser().setAction("suppression");
	}
	/**
	 * Renvois la valeur de l'action de l'utilisateur actuel
	 * @return
	 */
	@Path("getAction")
	@GET
	public String getAction(){
		return Utilisateurs.getCurrentUser().getAction();
	}
	/**
	 * Permet de changer le projet actuel de l'utilisateur actuel
	 * @param project
	 */
	@Path("setProject")
	@POST
	public void setProject(String project){
		Utilisateurs.getCurrentUser().setProject(project);
	}
}
