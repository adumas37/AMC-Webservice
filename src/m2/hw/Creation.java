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
		 * matiere=maths&date=26%2F12%2F2014&question=1%2B1%3D&reponse=1&reponse=2&bonne=on&question=La+question+facile&reponse=oui&bonne=on&submit=Creer+questionnaire
		 */
		System.out.println(formerEntete("maths","12/12/12"));
		ecrireFichier();
	}
	
	private void ecrireFichier(){
		
	}
	
	
	private String formerEntete(String matiere, String date){
		String entete= new String();
		
		entete = "%%% En-tête des copies \n" 
				+"\\noindent{\\bf QCM  \\hfill TEST}\n\n"
			
				+"\\vspace*{.5cm}\n"
				+"\\begin{minipage}{.4\\linewidth}\n"
				+"\\centering\\large\\bf "+ matiere +"\\ Examen du " +date +"\\end{minipage}\n"
				+"\\champnom{\\fbox{\n"
					            +"\t\\begin{minipage}{.5\\linewidth}\n"
					            +"\tNom et prénom :\n\n"
			
					            +"\t\\vspace*{.5cm}\\dotfill\n"
					            +"\t\\vspace*{1mm}\n"
					            +"\t\\end{minipage}\n"
					            +"}}\n\n"
			
				+"\\begin{center}\\em\n"
				+"Durée : 10 minutes.\n\n"
			
				  +"Aucun document n'est autorisé.\n"
				  +"L'usage de la calculatrice est interdit.\n\n"
			
				  +"Les questions faisant apparaître le symbole \\multiSymbole{} peuvent\n"
				  +"présenter zéro, une ou plusieurs bonnes réponses. Les autres ont\n"
				  +"une unique bonne réponse.\n\n"
			
				  +"Des points négatifs pourront être affectés à de \\emph{très\n"
					+"mauvaises} réponses.\n"
				+"\\end{center}\n"
				+"\\vspace{1ex}\n";
		
		
		return entete;
	}
}
