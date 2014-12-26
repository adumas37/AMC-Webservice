package m2.hw;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("creation")
public class Creation {

	@POST
	public void creation(String data){
		System.out.println(data);
		/* Exemple de data pour un questionnaire à 2question, 2 rep pour la première et une pour la deuxieme question:
		 * q1=LA+question+1&r1.1=LA+reponse+1.1&c1=on&r1.1=LA+reponse+1.2&c1=on&q1=LA%2Bquestion+2&r1.1=LA+reponse+2.1&c1=on&submit=Creer+questionnaire
		 */
	}
}
