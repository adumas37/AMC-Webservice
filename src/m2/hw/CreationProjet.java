package m2.hw;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


/** TODO
 * 		- Gestions des erreurs de dossier/fichier (doublons, noms incorrects ou dangereux...)
 * 		- Envois de données utiles apres creation
 * 		- 
 * 
 */

@Path("creationProjet")
public class CreationProjet {

	private String projectsPath = "Projets-QCM";

	public CreationProjet(){
		projectsPath=Utilisateurs.getCurrentUser().getProjectsPath();
		
	}
	/**
	 * Fonction permettant de recuperer le nom du projet a creer les dossiers ainsi que le fichier 
	 * latex.
	 * @param nom
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("nom") String nom,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String fileName = fileDetail.getFileName();
		// save it
		if (!nom.equals("")){
			
			//TODO gestion des caracteres speciaux: ;/\{"'` etc.
			if (nom.contains(" ")){
				nom = CreationQuestionnaire.replace(nom, " ", "_");
			}
			//TODO verifier que le repertoire n'existe pas deja
			String uploadedFileLocation = projectsPath + "/" + nom + "/" + fileName;
			CommandesAMC.creationRepertoire(nom);
			Utilisateurs.getCurrentUser().setProject(nom);
			
			if (!fileName.equals("") && fileName.contains(".tex")){
				saveFile(uploadedInputStream, uploadedFileLocation);
				CommandesAMC.prepareProject (nom, fileName);
		
				//TODO Changer le lien ci-dessous pour ne plus avoir de chemin fixé
				URI uri = UriBuilder.fromUri("http://localhost:8080/REST.Test/")
						.path("{a}")
						.build("Projet.html");
				return Response.seeOther(uri).build();
			}
			else if (fileName.equals("")){
				//TODO Changer le lien ci-dessous pour ne plus avoir de chemin fixé
				URI uri = UriBuilder.fromUri("http://localhost:8080/REST.Test/")
						.path("{a}")
						.build("CreationQuestionnaire.html");
				return Response.seeOther(uri).build();
			}
			else{
				if (!fileName.contains(".tex")){
					return Response.status(204).entity("Erreur d'extension du fichier" +
							"Le fichier doit etre un fichier Latex (.tex)").build();
				}
			}
			
			
		}
		else {
			return Response.status(204).entity("Aucun nom specifie. " +
					"La creation d'un projet requiert un nom").build();
			
		}
		
		return Response.status(406).entity("Not reacheable").build();
 
	}
	
	
	/**
	 * Fonction de sauvegarde du fichier latex upload par l'utilisateur
	 * @param uploadedInputStream
	 * @param serverLocation
	 */
	public static void saveFile(InputStream uploadedInputStream,
            String serverLocation) {
 
        try {
            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
 
            outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }

}
