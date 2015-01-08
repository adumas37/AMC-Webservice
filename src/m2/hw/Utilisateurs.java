package m2.hw;

import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

@Path("utilisateurs")
@Singleton
public class Utilisateurs {

	private static HashMap<String,Utilisateur> utilisateurs = null;
	
	
	@Path("load")
    @POST
    public void loadConfiguration() {
    	if (utilisateurs==null){
    		utilisateurs = new HashMap<String,Utilisateur>();
    	}
    	System.out.println("loaded");
    }

	@Path("add")
	@POST
	//@Consumes(MediaType.TEXT_PLAIN)
    public Response addUtilisateur(){
    		//@FormDataParam("username") String username
    		//@FormDataParam("password") String password
		String username="adumas";
		System.out.println(username);
    	Utilisateur u = new Utilisateur(username);
    	System.out.println("new user");
    	utilisateurs.put(username,u);
    	System.out.println("done");//*/
    	
    	URI uri = UriBuilder.fromUri("http://localhost:8080/REST.Test/")
				.path("{a}")
				.build("index.html");
		
		return Response.seeOther(uri).build();
    }
    
	public static Utilisateur getUtilisateur(String username){
		return utilisateurs.get(username);
	}
    
    /*@Lock(LockType.READ) // To allow multiple threads to invoke this method
                         // simultaneusly
    public String getValue(String key) {
    }//*/
    
    
    
}
