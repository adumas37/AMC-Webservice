package m2.hw;

import java.io.File;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("suppressionProjet")
public class SuppressionProjet {

	/**
	 * Permet de supprimer un projet
	 * @param project
	 */
	@POST
	public void supprimerProjet(String project){
		Utilisateurs.getCurrentUser().setProject(project);
		File dir = new File(Utilisateurs.getCurrentUser().getProjectPath());
		deleteDir(dir);
	}
	
	/**
	 * permet de supprimer un dossier en supprimant recursivement les sous-dossiers et fichiers
	 * @param dir
	 * @return
	 */
	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++){
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) 
				return false;	
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}
	
	
}
