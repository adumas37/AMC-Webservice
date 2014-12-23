package m2.hw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("first")
public class hw {
	
	
	 public hw(){
		 /*
		 //String cmd1 = "gnome-terminal -e /path/application"; 
		 String cmd1 = "xterm";
		 String cmd2 = "-e"; 
		 String cmd3 = "ls";
		 String[] cmd = {cmd1,cmd2};
		 Runtime rt = Runtime.getRuntime();
		 try {
			Process p = new ProcessBuilder(cmd1).start();
			System.out.println("coucou");
			p.destroy();
			
			Process p2 = new ProcessBuilder("ls","-l").start();System.out.println("coucou2");
			BufferedReader in = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			System.out.println(in.readLine());
			System.out.println("coucou3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		
	 }
	 // This method is called if TEXT_PLAIN is request
	  @GET
	  @Path("plain")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String sayPlainTextHello() {
	    return "Hello Jersey";
	  }
	  

	  // This method is called if XML is request
	  @GET
	  @Path("xml")
	  @Produces(MediaType.TEXT_XML)
	  public String sayXMLHello() {
	    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	  }

	  // This method is called if HTML is request
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public void sayHtmlHello() {
		  System.out.println("acces html");
	    /*return "<html> " + "<title>" + "Hello Jersey" + "</title>"
	        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";//*/
	  }

}
