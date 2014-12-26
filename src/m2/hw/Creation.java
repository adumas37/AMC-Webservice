package m2.hw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("creation")
public class Creation {

	/**
	 * Fonction appellee par le serveur lors de la creation d'un questionnaire
	 * Permet de creer un fichier latex sur le serveur.
	 * @param data
	 */
	@POST
	public void creation(String data){
		System.out.println(data);
		/* Exemple de data pour un questionnaire à 2question, 2 rep pour la première et une pour la deuxieme question:
		 * matiere=maths&date=26%2F12%2F2014&question=1%2B1%3D&reponse=1&reponse=2&bonne=on&question=La+question+facile&reponse=oui&bonne=on&submit=Creer+questionnaire
		 */
		ecrireFichier(data);
	}
	
	/**
	 * Permet d'ecrire le fichier latex a partir des donnees recues
	 * @param data
	 */
	private void ecrireFichier(String data){
		File file = new File("MATHS.txt");
	    FileWriter fw;
			
	    try {
	      fw = new FileWriter(file);
	      fw.write(formerImports());
	      fw.write("\\begin{document}\n\n");
	      fw.write(formerEntete("maths","12/12/12"));
	      fw.write("\\end{document}");
	      fw.close();

	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	/**
	 * Permet de former la liste des imports pour le fichier latex
	 * @return
	 */
	private String formerImports(){
		String imports = new String();
		
		imports= "\\documentclass[a4paper]{article}\n\n"
				
				+"\\usepackage[utf8x]{inputenc}\n"
				+"\\usepackage{fontenc}\n"
				+"\\usepackage{multicol}\n"
				+"\\usepackage[francais,bloc,completemulti]{automultiplechoice}\n\n";
		
		return imports;
	}
	/**
	 * Permet de creer et renvoyer l'entete du fichier latex, a partir de la date du DS et la matiere
	 * @param matiere
	 * @param date
	 * @return
	 */
	private String formerEntete(String matiere, String date){
		String entete= new String();
		
		entete = "\t%%% En-tête des copies \n" 
				+"\t\\noindent{\\bf QCM  \\hfill TEST}\n\n"
			
				+"\t\\vspace*{.5cm}\n"
				+"\t\\begin{minipage}{.4\\linewidth}\n"
				+"\t\\centering\\large\\bf "+ matiere +"\\ Examen du " +date +"\\end{minipage}\n"
				+"\t\\champnom{\\fbox{\n"
					            +"\t\t\\begin{minipage}{.5\\linewidth}\n"
					            +"\t\tNom et prénom :\n\n"
			
					            +"\t\t\\vspace*{.5cm}\\dotfill\n"
					            +"\t\t\\vspace*{1mm}\n"
					            +"\t\t\\end{minipage}\n"
				+"\t}}\n\n"
			
				+"\t\\begin{center}\\em\n"
				+"\tDurée : 10 minutes.\n\n"
			
				+"\tAucun document n'est autorisé.\n"
				+"\tL'usage de la calculatrice est interdit.\n\n"
			
				+"\tLes questions faisant apparaître le symbole \\multiSymbole{} peuvent\n"
				+"\tprésenter zéro, une ou plusieurs bonnes réponses. Les autres ont\n"
				+"\tune unique bonne réponse.\n\n"
			
				+"\tDes points négatifs pourront être affectés à de \\emph{très\n"
				+"\tmauvaises} réponses.\n"
				+"\t\\end{center}\n"
				+"\t\\vspace{1ex}\n";
		
		
		return entete;
	}
	
	/**
	 * Permet de former le questionnaire en latex a partir des donnees recue depuis le webservice
	 * @param data
	 * @return
	 */
	private String formerQuestionnaire(String data){
		String questionnaire = new String();
		
		return questionnaire;
	}
}
