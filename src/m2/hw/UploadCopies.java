package m2.hw;

import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("uploadCopies")
public class UploadCopies {

	private static final String PROJECTS_PATH = "Projets-QCM";
	private static final String PROJECT = "projetTestWebservice";
	
	/**
	 * Permet de telecharger les copies, les enregistrer, de recuperer le fichier CSV de la classe
	 * et de lancer la correction des copies.
	 * @param classe
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response Correction(
		@FormDataParam("classe") String classe,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
 
		String fileName = fileDetail.getFileName();
		
		System.out.println("classe: "+classe+", file: "+fileName);
		
		if (!fileName.equals("") && !classe.equals("") && fileName.contains(".pdf")){
			
			String uploadedFileLocation = PROJECTS_PATH + "/" + PROJECT + "/copies.pdf";
			CreationProjet.saveFile(uploadedInputStream, uploadedFileLocation);
			
			//TODO recupererClasseCSV(classe);
			
			//TODO lancer correction des copies
	
			//TODO Changer le lien ci-dessous pour ne plus avoir de chemin fixé
			URI uri = UriBuilder.fromUri("http://localhost:8080/REST.Test/")
					.path("{a}")
					.build("Correction.html");
			
			return Response.seeOther(uri).build();
		}
		else {
			if (!fileName.contains(".pdf") && !fileName.equals("")){
				return Response.status(204).entity("Erreur d'extension du fichier" +
						"Le fichier doit etre un fichier PDF (.pdf)").build();
			}
			if (fileName.equals("") && classe.equals("")){
				return Response.status(204).entity("Aucun fichier selectionne ou classe selectionne. " +
						"La correction d'un projet requiert une classe et des copies.").build();
			}
			else if (classe.equals("")) {
				return Response.status(204).entity("Aucun nom specifie. " +
						"La correction d'un projet requiert une classe.").build();
			}
			else if (fileName.equals("")){
				return Response.status(204).entity("Aucun fichier selectionne. " +
						"La correction d'un projet requiert des copies.").build();
			}
			
		}
		
		return Response.status(406).entity("Not reacheable").build();
 
	}
	
	
}