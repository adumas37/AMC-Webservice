package m2.hw;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import com.sun.istack.internal.logging.Logger;

public class CommandesAMC {

	/**
	 * Fonction permettant de mettre en forme la commande d'AMC afin de creer les Layouts.
	 * La chaine d'entrée doit etre sous la forme "username/project/path/"
	 * @param projectPath
	 */
	public static void creationLayout(String projectPath){
		
		String username = projectPath.split("/")[0];

		String[] cmd = { "meptex", "--src", "/media/"+projectPath+"questionnaire-calage.xy",
						 "--data", "/media/"+projectPath+"data/" };
		
		executerCommande(cmd, username);
		
	}
	
	/**
	 * Permet d'executer la commande fournie en entrée, dans le dossier de l'utilisateur specifié
	 * @param amcCmd
	 * @param username
	 */
	private static void executerCommande(String[] amcCmd , String username){
		try{
			ProcessBuilder pb = null;
	        Process p = null;

	        String[] dockerCmd = { "sudo", "docker", "run", "-d", "-v", 
	        					   "/home/user/Projects:/media/"+username, "dockeramc:v1"};
	        String[] completeCmd = new String[dockerCmd.length+amcCmd.length];
	        System.arraycopy(dockerCmd, 0, completeCmd, 0, dockerCmd.length);
	        System.arraycopy(amcCmd, 0, completeCmd, dockerCmd.length, amcCmd.length);
	        
	        pb = new ProcessBuilder(completeCmd);
	        pb.directory(new File("/media"));
	
	        try {
	            p = pb.start();
	        } catch (IOException ex) {
	            Logger.getLogger(Process.class.getName(), null).log(Level.SEVERE, null, ex);
	        }
	        
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	
	        // read the output from the command
	        System.out.println("Here is the standard output of the command:\n");
	
	        String s = null;
	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(s);
	        }
	
	        // read any errors from the attempted command
	        System.out.println("Here is the standard error of the command (if any):\n");
	        while ((s = stdError.readLine()) != null) {
	            System.out.println(s);
	        }
	    } catch (IOException ex) {
	        Logger.getLogger(Process.class.getName(), null).log(Level.SEVERE, null, ex);
	    }
	}
}
