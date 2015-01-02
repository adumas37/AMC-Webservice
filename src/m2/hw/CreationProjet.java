package m2.hw;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
		if (!fileName.equals("") && !nom.equals("")){
			
			String uploadedFileLocation = PROJECTS_PATH + "/" + nom + "/" + fileName;
			creationRepertoire(nom);
			saveFile(uploadedInputStream, uploadedFileLocation);
			prepareProject (nom, fileName);
			
			String output = "File \"" + fileName +"\" uploaded to \"" + uploadedFileLocation+"\"";
			
			return Response.status(200).entity(output).build();
		}
		else {
			if (fileName.equals("") && nom.equals("")){
				return Response.status(204).entity("Aucun fichier selectionne ou nom donne. " +
						"La creation d'un projet requiert un nom et un questionnaire").build();
			}
			else if (nom.equals("")) {
				return Response.status(204).entity("Aucun nom specifie. " +
						"La creation d'un projet requiert un nom").build();
			}
			else if (fileName.equals("")){
				return Response.status(204).entity("Aucun fichier selectionne. " +
						"La creation d'un projet requiert un nom").build();
			}
		}
		
		return Response.status(406).entity("Not reacheable").build();
 
	}
	
	
	/**
	 * Fonction de sauvegarde du fichier latex upload par l'utilisateur
	 * @param uploadedInputStream
	 * @param serverLocation
	 */
	private void saveFile(InputStream uploadedInputStream,
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
	
	        p = null;
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
	private void prepareProject(String nom, String fileName){
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
