package m2.hw;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("uploadCopies")
public class UploadCopies {

	private static final String PROJECTS_PATH = "Projets-QCM";
	private static final String PROJECT = "projetTestWebservice";
	

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response Correction(
		@FormDataParam("classe") String classe,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
		File file = new File(PROJECTS_PATH +"/"+PROJECT+"/"+PROJECTS_PATH +PROJECT+"sujet.pdf");
 
		String fileName = fileDetail.getFileName();
		
		System.out.println("classe: "+classe+", file: "+fileName);
		

		//TODO Changer le lien ci-dessous pour ne plus avoir de chemin fixé
		URI uri = UriBuilder.fromUri("http://localhost:8080/REST.Test/")
				.path("{a}")
				.build("Correction.html");
		
		return Response.seeOther(uri).build();
 
	}
	
	
}