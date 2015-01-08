package m2.hw;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

@Path("utilisateurs")
@Singleton
public class Utilisateurs {

	private static HashMap<String,Utilisateur> utilisateurs;
	
	
	@Path("load")
    @PostConstruct
    public void loadConfiguration() {
    	utilisateurs = new HashMap<String,Utilisateur>();
    }

	@Path("add")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addUtilisateur(@FormDataParam("username") String username){
    	Utilisateur u = new Utilisateur(username);
    	utilisateurs.put(username,u);
    }
    
	public static Utilisateur getUtilisateur(String username){
		return utilisateurs.get(username);
	}
    
    /*@Lock(LockType.READ) // To allow multiple threads to invoke this method
                         // simultaneusly
    public String getValue(String key) {
    }*/
    
    
    
}
