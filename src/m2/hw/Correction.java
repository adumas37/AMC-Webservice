package m2.hw;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


@Path("correction")
public class Correction {

	private static String filePath = "exports/notes.csv";
	
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
}
