package m2.hw;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

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

	public CreationProjet(@CookieParam ("AMC_Webservice") String username){
		Utilisateur u =Utilisateurs.getUtilisateur(username);
		projectsPath=u.getProjectsPath();
		
	}
	
	 /**
     * Fonction permettant de creer les dossiers pour le nouveau projet
     * @param data
     */
    public static void creationRepertoire(String nom,String username){
    	Utilisateur u=Utilisateurs.getUtilisateur(username);
        String projectsPath = u.getProjectsPath().substring(0,
        u.getProjectsPath().length()-1)+"/"+nom;
        File dir = new File(projectsPath);
        boolean isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/cr");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/cr/corrections");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/cr/corrections/jpg");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/cr/corrections/pdf");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/cr/diagnostic");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/cr/zooms");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/data");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/exports");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/scans");
        isCreated = dir.mkdirs();
        dir = new File(projectsPath+"/copies");
        isCreated = dir.mkdirs();
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
		@FormDataParam("file") FormDataContentDisposition fileDetail,
		@Context UriInfo context,
		@CookieParam("AMC_Webservice") String username) {
		Utilisateur u =Utilisateurs.getUtilisateur(username);
		String fileName = fileDetail.getFileName();
		String url = context.getBaseUri().toString();
		url=url.substring(0,url.length()-5); //Supression du "rest/" a la fin de l'url
		
		if (!nom.equals("")){
			
			//TODO gestion des caracteres speciaux: ;/\{"'` etc.
			if (nom.contains(" ")){
				nom = QuestionnaireTools.replace(nom, " ", "_");
			}

			String uploadedFileLocation = projectsPath + "/" + nom + "/questionnaire.tex";
			creationRepertoire(nom,username);
			u.setProject(nom);
			
			if (!fileName.equals("") && fileName.contains(".tex")){
				saveFile(uploadedInputStream, uploadedFileLocation);
				CommandesAMC.prepareProject ("questionnaire.tex", username);
				
				QuestionnaireTools.importFichier(username);
				
				URI uri = UriBuilder.fromUri(url)
						.path("{a}")
						.build("Projet.php");
				
				return Response.seeOther(uri).build();
			}
			else if (fileName.equals("")){
				URI uri = UriBuilder.fromUri(url)
						.path("{a}")
						.build("CreationQuestionnaire.php");
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
