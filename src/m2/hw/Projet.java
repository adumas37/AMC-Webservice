package m2.hw;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("projet")
public class Projet {

	private static final String PROJECTS_PATH = "Projets-QCM/";
	private static final String PROJECT = "test4/";
	
	@Path("copies")
	@GET
	@Produces({"application/pdf"})
	public Response getCopies() {
		 System.out.println("HERE!!");
		File file = new File(PROJECTS_PATH+PROJECT+"DOC-sujet.pdf");
 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=Copies.pdf");
		return response.build();
 
	}
	
	@Path("catalog")
	@GET
	@Produces({"application/pdf"})
	public Response getCatalog() {
		 System.out.println("HERE!!");
		File file = new File(PROJECTS_PATH+PROJECT+"DOC-catalog.pdf");
 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=Catalogue.pdf");
		return response.build();
 
	}
	
	@Path("corrige")
	@GET
	@Produces({"application/pdf"})
	public Response getCorrige() {
		 System.out.println("HERE!!");
		File file = new File(PROJECTS_PATH+PROJECT+"DOC-corrige.pdf");
 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=Corrige.pdf");
		return response.build();
 
	}
}
