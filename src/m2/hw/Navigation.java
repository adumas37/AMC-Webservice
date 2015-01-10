package m2.hw;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("navigation")
public class Navigation {

	@Path("ouverture")
	@GET
	public void setOuverture(){
		Utilisateurs.getCurrentUser().setAction("ouverture");
		System.out.println("Action: ouverture");
	}
	@Path("correction")
	@GET
	public void setCorrection(){
		Utilisateurs.getCurrentUser().setAction("correction");
		System.out.println("Action: correction");
	}
	@Path("creation")
	@GET
	public void setCreation(){
		Utilisateurs.getCurrentUser().setAction("creation");
	}
	@Path("getAction")
	@GET
	public String getAction(){
		System.out.println("getAction: "+Utilisateurs.getCurrentUser().getAction());
		return Utilisateurs.getCurrentUser().getAction();
	}
}
