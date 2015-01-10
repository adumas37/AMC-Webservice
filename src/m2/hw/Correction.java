package m2.hw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


@Path("correction")
public class Correction {

	private static String filePath = Utilisateurs.getCurrentUser().getProjectPath()+"/exports/notes.csv";
	@Path("download")
	@GET
    @Produces("text/plain")
    public Response getTextFile() {
		
		File file = new File(filePath);
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
		File file = new File(filePath);
		String notesHTML = new String("<table>");
		if (file.exists()){
			try{
				BufferedReader buffer = new BufferedReader(new FileReader(filePath));
				
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
	
}
