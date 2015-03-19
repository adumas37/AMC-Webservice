package m2.hw;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("navigation")
public class Navigation {

	/**
	 * Permet de changer la valeur de l'action pour l'utilisateur actuel
	 */
	@Path("ouverture")
	@POST
	public void setOuverture(@CookieParam("AMC_Webservice") String username){
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		u.setAction("ouverture");
	}
	/**
	 * Permet de changer la valeur de l'action pour l'utilisateur actuel
	 */
	@Path("correction")
	@POST
	public void setCorrection(@CookieParam("AMC_Webservice") String username){
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		u.setAction("correction");
	}
	/**
	 * Permet de changer la valeur de l'action pour l'utilisateur actuel
	 */
	@Path("creation")
	@POST
	public void setCreation(@CookieParam("AMC_Webservice") String username){
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		u.setAction("creation");
	}
	/**
	 * Permet de changer la valeur de l'action pour l'utilisateur actuel
	 */
	@Path("suppression")
	@POST
	public void setSuppression(@CookieParam("AMC_Webservice") String username){
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		u.setAction("suppression");
	}
	/**
	 * Renvois la valeur de l'action de l'utilisateur actuel
	 * @return
	 */
	@Path("getAction")
	@GET
	public String getAction(@CookieParam("AMC_Webservice") String username){
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		return u.getAction();
	}
	/**
	 * Permet de changer le projet actuel de l'utilisateur actuel
	 * @param project
	 */
	@Path("setProject")
	@POST
	public void setProject(String project,@CookieParam("AMC_Webservice") String username){
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		u.setProject(project);
	}
	
	@Path("correctionLien/{name}")
	@GET
	public Response setCorrection(@PathParam("name") String name,@Context UriInfo context,@CookieParam("AMC_Webservice") String username){
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		String url = context.getBaseUri().toString();
		url=url.substring(0,url.length()-5);
		URI uri = UriBuilder.fromUri(url)
				.path("{a}")
				.build("UploadCopies.php");;
		try{ //Ecriture du fichier contenant la liste des fichiers des copies
			File file = new File(u.getProjectPath()+"copies/listeCopies.txt");
			FileWriter fw = new FileWriter(file,true);
			if (file.exists()){
				if(file.length()>3)
					uri = UriBuilder.fromUri(url)
						.path("{a}")
						.build("Correction.php");
			}
			fw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return Response.seeOther(uri).build();
	}
}
