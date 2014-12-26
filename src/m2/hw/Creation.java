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
		 * question=LA+question+1&reponse=rep1.1&bonne=on&reponse=rep1.2&question=LA+question+2&reponse=rep2.1&bonne=on&submit=Creer+questionnaire
		 */
	}
}
