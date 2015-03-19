package m2.hw;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

@Path("uploadCopies")
public class UploadCopies {

	/**
	 * Permet de telecharger les copies, les enregistrer, de recuperer le fichier CSV de la classe.
	 * @param formParams
	 * @param context
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/plain")
	public String Correction(FormDataMultiPart formParams,@Context UriInfo context,@CookieParam("AMC_Webservice") String username) {
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		String projectPath = u.getProjectPath();
		
	    List<FormDataBodyPart> files = formParams.getFields("file");
	    List<FormDataBodyPart> classes = formParams.getFields("classe");
	    
	    for (FormDataBodyPart file : files){	//Recuperer et enregistrer les fichiers de copies
	    	
	    	String fileName =  file.getContentDisposition().getFileName();
	    	if (!fileName.equals("") && /*!classe.equals("") &&*/ fileName.contains(".pdf")){
		    	InputStream fileInputStream = file.getValueAs(InputStream.class);
		    	String uploadedFileLocation = projectPath + "/copies/"+fileName;
				CreationProjet.saveFile(fileInputStream, uploadedFileLocation);
				
	    	}
	    	
	    }
	    
	    try{	//Ecriture du fichier contenant la liste des fichiers des copies
	    	File file = new File(u.getProjectPath()+"copies/listeCopies.txt");
		    FileWriter fw = new FileWriter(file);
		    PrintWriter pw = new PrintWriter(fw);
	        for (int i = 0; i < files.size(); i++){
	        	pw.println(files.get(i).getContentDisposition().getFileName());
	        }
	        pw.close();
	        fw.close();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	    
	    try{	//Ecriture du fichier contenant la liste des classes
	    	File file = new File(u.getProjectPath()+"copies/classes.txt");
		    FileWriter fw = new FileWriter(file);
		    PrintWriter pw = new PrintWriter(fw);
	        for (int i = 0; i < classes.size(); i++){
	        	pw.println(classes.get(i).getValue());
	        }
	        pw.close();
	        fw.close();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	    			
	    /*CommandesAMC.lancerCorrection(projectPath);
		String url = context.getBaseUri().toString();
		url = url.substring(0,url.length()-5); //Supression du "rest/" a la fin de l'url
		URI uri = UriBuilder.fromUri(url)
				.path("{a}")
				.build("Correction.html");
		
		return Response.seeOther(uri).build();*/
	    
	    //Ici on peut envoyer un code d'erreur pour l'upload
		return "1";
	}	
	
}
