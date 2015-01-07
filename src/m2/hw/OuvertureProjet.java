package m2.hw;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

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
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public static void main(String[] args) throws IOException {
		File directory = new File(".");
		File[] subdirs = directory.listFiles();
		for (File dir : subdirs) {
			System.out.println("Directory: " + dir.getName());
			if (dir.isDirectory()){
				System.out.println("directory");
			}
		}
		 
	}	
	
}
