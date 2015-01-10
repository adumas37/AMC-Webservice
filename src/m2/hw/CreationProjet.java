package m2.hw;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;

import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.sun.istack.internal.logging.Logger;
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

	//TODO changer ca!!!
	private static final String PROJECTS_PATH = "Projets-QCM";

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
			String uploadedFileLocation = PROJECTS_PATH + "/" + nom + "/" + fileName;
			creationRepertoire(nom);
			Utilisateurs.getCurrentUser().setProject(nom);
			System.out.println("user: "+Utilisateurs.getCurrentUser().getUserName()+", project: "+Utilisateurs.getCurrentUser().getProject());
			
			if (!fileName.equals("") && fileName.contains(".tex")){
				saveFile(uploadedInputStream, uploadedFileLocation);
				prepareProject (nom, fileName);
		
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
	
	/**
	 * Fonction permettant de creer les dossiers pour le nouveau projet
	 * @param data
	 */
	private void creationRepertoire(String nom){
		try{
			
			ProcessBuilder pb = null;
	        Process p = null;
	        
	        String workingDir = System.getProperty("user.dir");
	        String scriptloc=workingDir+"/createProject.sh";
	        String cmd[] = {"/bin/bash",scriptloc ,nom, PROJECTS_PATH};

	        pb = new ProcessBuilder(cmd);
	        pb.directory(new File(workingDir));
	
	        try {
	            p = pb.start();
	        } catch (IOException ex) {
	            Logger.getLogger(Process.class.getName(), null).log(Level.SEVERE, null, ex);
	        }
	        
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	
	        // read the output from the command
	        System.out.println("Here is the standard output of the command:\n");
	
	        String s = null;
	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(s);
	        }
	
	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	        }
	    } catch (IOException ex) {
	        Logger.getLogger(Process.class.getName(), null).log(Level.SEVERE, null, ex);
	    }
	}
	
	/**
	 * Fonction permettant de lancer la phase de preparation d'AMC pour le projet
	 * @param nom
	 * @param fileName
	 */
	public static void prepareProject(String nom, String fileName){
		try{
			String filePath = PROJECTS_PATH +"/" + nom + "/" + fileName;
			String projectPath = PROJECTS_PATH + nom;
			String workingDir = System.getProperty("user.dir");
			
			ProcessBuilder pb = null;
	        Process p = null;
	        
	        String cmd[] = {"auto-multiple-choice", "prepare", "--mode", "s", "--prefix", projectPath, filePath};

	        pb = new ProcessBuilder(cmd);
	        pb.directory(new File(workingDir));
	
	        try {
	            p = pb.start();
	        } catch (IOException ex) {
	            Logger.getLogger(Process.class.getName(), null).log(Level.SEVERE, null, ex);
	        }
	        
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	
	        // read the output from the command
	        System.out.println("Here is the standard output of the command:\n");
	
	        String s = null;
	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(s);
	        }
	
	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	        }
	    } catch (IOException ex) {
	        Logger.getLogger(Process.class.getName(), null).log(Level.SEVERE, null, ex);
	    }
	
	}
	
	
}
