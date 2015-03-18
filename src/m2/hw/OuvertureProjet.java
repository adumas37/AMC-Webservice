package m2.hw;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ouvertureProjet")
public class OuvertureProjet {
	
	/**
	 * Fonction permettant de renvoyer tous les projets de l'utilisateur 
	 * sous la forme "projet1/projet2/projet3/.../projetN"
	 * @return
	 * @throws IOException
	 */
	@POST
	public Response getDirectory(@CookieParam("AMC_Webservice") String username) throws IOException {
		Utilisateur u = Utilisateurs.getUtilisateur(username);
		File directory = new File(u.getProjectsPath());
		File[] subdirs = directory.listFiles();
		String directoryList = new String();
		
		for (File dir : subdirs) {
			if (dir.isDirectory()){
				directoryList +="/"+dir.getName();
			}
		}
		directoryList=directoryList.replaceFirst("/", "");
		
		return Response.ok(directoryList, MediaType.TEXT_PLAIN).build();
	}	
	
}
