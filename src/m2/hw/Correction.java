package m2.hw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.Gson;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;


@Path("correction")
public class Correction {

	@Path("download")
	@GET
    @Produces("text/plain")
    public Response getTextFile() {
		
		File file = new File(Utilisateurs.getCurrentUser().getProjectPath() +
							 "/exports/notes.csv");
		if (file.exists()){
			ResponseBuilder response = Response.ok((Object) file);
	        response.header("Content-Disposition", "attachment; filename=\"notes.csv\"");
	        return response.build();
		}
		else {
			return null;
		}

    }
	
	@Path("notes")
	@POST
    @Produces("text/plain")
    public Response getNotes() throws IOException {
		File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+"/exports/notes.csv");
		String notesHTML = new String("<table>");
		if (file.exists()){
			try{
				BufferedReader buffer = new BufferedReader(
											new FileReader(file));
				
				try{
					String line;
					while((line = buffer.readLine()) != null){
						
						notesHTML = notesHTML + "<tr>";
						String[] tokens = line.split(",");
						for(int i = 1;i<4;i++){
							notesHTML = notesHTML + "<td>"+tokens[i]+"</td>";
						}
						notesHTML = notesHTML + "</tr>";
						
					}
					
				} finally {
					buffer.close();
				}
				
				
			} catch (IOException ioex){
				System.out.println(ioex);
			}
			
			

		}
		notesHTML = notesHTML.replaceAll("\"","");
		notesHTML = notesHTML + "</table>";
		return Response.ok(notesHTML, MediaType.TEXT_PLAIN).build();
    }
	
	@Path("getFilesNames")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public String getCopies() {
		
		File file = new File(Utilisateurs.getCurrentUser().getProjectPath() +
							 "/copies/listeCopies.txt");
		ArrayList<String> listFiles = new ArrayList<String>();
		String json = null;
		
		if (file.exists()){
			try{
				BufferedReader buffer = new BufferedReader(
											new FileReader(file));
				
				try{
					String line;
					while((line = buffer.readLine()) != null){
						listFiles.add(line);
					}
					
				} finally {
					buffer.close();
					Gson gson = new Gson();
					json = gson.toJson(listFiles);
					System.out.println("JSON: "+json);
					
				}
				return json;
				
			} catch (IOException ioex){
				System.out.println(ioex);
				return null;
			}

			
		}
		else {
			return null;
		}
		
    }
	
	@Path("ajouterCopies")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response ajouterCopies(
		FormDataMultiPart formParams,
		@Context UriInfo context) {

		String projectPath = Utilisateurs.getCurrentUser().getProjectPath();
		
	    List<FormDataBodyPart> files = formParams.getFields("file");
	    
	    for (FormDataBodyPart file : files){	//Recuperer et enregistrer les fichiers de copies
	    	
	    	String fileName =  file.getContentDisposition().getFileName();
	    	if (!fileName.equals("") && fileName.contains(".pdf")){
		    	InputStream fileInputStream = file.getValueAs(InputStream.class);
		    	String uploadedFileLocation = projectPath + "/copies/"+fileName;
				CreationProjet.saveFile(fileInputStream, uploadedFileLocation);
				
	    	}
	    	
	    }
	    
	    try{	//Ecriture du fichier contenant la liste des fichiers des copies
	    	File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+"copies/listeCopies.txt");
		    FileWriter fw = new FileWriter(file,true);
		    String fileName="";
	        for (int i = 0; i < files.size(); i++){
	        	fileName=files.get(i).getContentDisposition().getFileName();
	        	if (!fileName.equals("") && fileName.contains(".pdf")){
	        		fw.write("\n"+files.get(i).getContentDisposition().getFileName());
	        	}
	        }
	        fw.close();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	    			
		//TODO recupererClasseCSV(classe);
		/*System.out.println("Layout");
		CommandesAMC.creationLayout(projectPath);
		System.out.println("generationImagesCopies");
		CommandesAMC.generationImagesCopies(projectPath);
		System.out.println("analyseReponses");
		CommandesAMC.analyseReponses(projectPath);
		System.out.println("notation");
		CommandesAMC.notation(projectPath);
		//System.out.println("associationAuto");
		//CommandesAMC.associationAuto(projectPath);
		System.out.println("extractionNotesEleves");
		CommandesAMC.extractionNotesEleves(projectPath);*/
		

		String url = context.getBaseUri().toString();
		url = url.substring(0,url.length()-5); //Supression du "rest/" a la fin de l'url
		URI uri = UriBuilder.fromUri(url)
				.path("{a}")
				.build("Correction.html");
		
		return Response.seeOther(uri).build();
	}	
	
}
