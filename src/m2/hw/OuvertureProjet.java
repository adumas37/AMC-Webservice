package m2.hw;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ouvertureProjet")
public class OuvertureProjet {

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
	public Response getDirectory() throws IOException {
		File directory = new File(PROJECTS_PATH+"/");
		File[] subdirs = directory.listFiles();
		String directoryList = new String();
		
		for (File dir : subdirs) {
			if (dir.isDirectory()){
				directoryList +=" "+dir.getName();
			}
		}
		
		return Response.ok(directoryList, MediaType.TEXT_PLAIN).build();
	}	
	
}
