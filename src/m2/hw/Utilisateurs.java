package m2.hw;

import java.net.URI;
import java.util.HashMap;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.spi.resource.Singleton;

@Path("utilisateurs")
@Singleton
public class Utilisateurs {

	private static HashMap<String,Utilisateur> utilisateurs = null;
	private static Utilisateur currentUser = null;
	
	@Path("load")
    @POST
    public void loadConfiguration() {
    	if (utilisateurs==null){
    		utilisateurs = new HashMap<String,Utilisateur>();
    	}
    }

	@Path("add")
	@POST
    public Response addUtilisateur(String username){

    	Utilisateur u = new Utilisateur(username);
    	utilisateurs.put(username,u);
    	Utilisateurs.currentUser=u;
    	
    	URI uri = UriBuilder.fromUri("http://localhost:8080/REST.Test/")
				.path("{a}")
				.build("index.html");
		
		return Response.seeOther(uri).build();
    }
    
	public static Utilisateur getUtilisateur(String username){
		return utilisateurs.get(username);
	}
    
	public static Utilisateur getCurrentUser(){
		return currentUser;
	}    
    
}
