package m2.hw;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class Projet {

	@Path("pdf")
	@GET
	@Produces({"application/pdf"})
	public Response getFile() {
		 
		File file = new File("/Projets-QCM/test4/DOC-sujet.pdf");
 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=new-excel-file.xls");
		return response.build();
 
	}
	
}
