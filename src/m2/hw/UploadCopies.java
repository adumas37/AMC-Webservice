package m2.hw;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	 * Permet de telecharger les copies, les enregistrer, de recuperer le fichier CSV de la classe
	 * et de lancer la correction des copies.
	 * @param formParams
	 * @param context
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response Correction(
		FormDataMultiPart formParams,
		@Context UriInfo context) {

		String projectPath = Utilisateurs.getCurrentUser().getProjectPath();
		
	    List<FormDataBodyPart> files = formParams.getFields("file");
	    int i=0;
	    
	    for (FormDataBodyPart file : files){	//Recuperer et enregistrer les fichiers de copies
	    	
	    	String fileName =  file.getContentDisposition().getFileName();
	    	if (!fileName.equals("") && /*!classe.equals("") &&*/ fileName.contains(".pdf")){
		    	InputStream fileInputStream = file.getValueAs(InputStream.class);
		    	i++;
		    	String uploadedFileLocation = projectPath + "/copies/copies"+i+".pdf";
				CreationProjet.saveFile(fileInputStream, uploadedFileLocation);
				
	    	}
	    	
	    }
	    
	    try{	//Ecriture du fichier contenant la liste des fichiers des copies
	    	File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+"copies/listeCopies.txt");
		    FileWriter fw = new FileWriter(file);
	        //PrintWriter out  = new PrintWriter(new FileWriter("copies/listeCopies.txt"));
	        for (int j = 1; j < i; j++){
	        	fw.write("copies"+j+".pdf\n");
	        }
	        fw.write("copies"+i+".pdf");
	        fw.close();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	    			
		//TODO recupererClasseCSV(classe);
		
		CommandesAMC.creationLayout(projectPath);
		CommandesAMC.generationImagesCopies(projectPath);
		CommandesAMC.analyseReponses(projectPath);
		CommandesAMC.notation(projectPath);
		//CommandesAMC.associationAuto(projectPath);
		//CommandesAMC.extractionNotesEleves(projectPath);
		

		String url = context.getBaseUri().toString();
		url = url.substring(0,url.length()-5); //Supression du "rest/" a la fin de l'url
		URI uri = UriBuilder.fromUri(url)
				.path("{a}")
				.build("Correction.html");
		
		return Response.seeOther(uri).build();
	}	
	
}