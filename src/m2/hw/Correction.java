package m2.hw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.Gson;


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
	
}
