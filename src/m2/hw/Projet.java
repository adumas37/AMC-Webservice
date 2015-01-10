package m2.hw;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("projet")
public class Projet {
	
	/**
	 * Permet de renvoyer les copies a imprimer
	 * @return
	 */
	@Path("copies")
	@GET
	@Produces({"application/pdf"})
	public Response getCopies() {
		File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+Utilisateurs.getCurrentUser().getProjectPath().replace("/", "")+"sujet.pdf");
 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=Copies.pdf");
		return response.build();
 
	}
	
	/**
	 * Permet de renvoyer le catalogue des questions
	 * @return
	 */
	@Path("catalog")
	@GET
	@Produces({"application/pdf"})
	public Response getCatalog() {
		File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+Utilisateurs.getCurrentUser().getProjectPath().replace("/", "")+"catalog.pdf");
 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=Catalogue.pdf");
		return response.build();
 
	}
	
	/**
	 * Permet de renvoyer la correction des questions
	 * @return
	 */
	@Path("corrige")
	@GET
	@Produces({"application/pdf"})
	public Response getCorrige() {
		File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+Utilisateurs.getCurrentUser().getProjectPath().replace("/", "")+"corrige.pdf");
 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=Corrige.pdf");
		return response.build();
 
	}
	
	/**
	 * Permet de renvoyer le questionnaire en format Latex
	 * @return
	 */
	@Path("questionnaire")
	@GET
    @Produces("text/plain")
    public Response getTextFile() {

		File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.tex");
		ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"questionnaire.tex\"");
        return response.build();


    }
	
}
