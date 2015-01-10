package m2.hw;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("navigation")
public class Navigation {

	@Path("ouverture")
	@GET
	public void setOuverture(){
		Utilisateurs.getCurrentUser().setAction("ouverture");
	}
	@Path("correction")
	@GET
	public void setCorrection(){
		Utilisateurs.getCurrentUser().setAction("correction");
	}
	@Path("creation")
	@GET
	public void setCreation(){
		Utilisateurs.getCurrentUser().setAction("creation");
	}
	@Path("getAction")
	@GET
	public String getAction(){
		return Utilisateurs.getCurrentUser().getAction();
	}
	@Path("setProject")
	@POST
	public void setProject(String project){
		Utilisateurs.getCurrentUser().setProject(project);
	}
}
