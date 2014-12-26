package m2.hw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

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
	    int nbCopies = 1;
			
	    try {
	      fw = new FileWriter(file);
	      fw.write(formerImports());
	      fw.write("\\begin{document}\n\n");
	      fw.write("\\exemplaire{"+nbCopies+"}{\n\n");
	      fw.write(formerEntete(data));
	      fw.write(formerQuestionnaire(data));
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
	 * Permet de creer et renvoyer l'entete du fichier latex, a partir des donnes recues
	 * @param data
	 * @return
	 */
	private String formerEntete(String data){
		String entete= new String();
		String donnees = null;
		String matiere = "VVVVVVV";
		String date = "JJ/MM/AAAA";
		
		//matiere=sd%3Cf&matiere=sqdf&nbCopies=1&question=que
		
		if (data.contains("&nbCopies=")){
			donnees = data.split("&nbCopies=")[0];
			matiere = donnees.split("&date=")[0];
			matiere = matiere.split("=")[1];
			date = donnees.split("&date=")[1];
		}
		
		
		entete = "\t%%% En-tête des copies \n\n" 
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
				+"\t\\vspace{1ex}\n\n"
				+"\t%%% fin de l'en-tête des copies\n\n\n";
		
		
		return entete;
	}
	
	/**
	 * Permet de former le questionnaire en latex a partir des donnees recue depuis le webservice
	 * @param data
	 * @return
	 */
	private String formerQuestionnaire(String data){	
		String questionnaire = "\t%%% Debut du questionnaire\n\n";
		String[] chaines = null;
		String[] questions = null;
		String[] questionsReponses = null;
		String typeQuestion = null;
		
		if (data.contains("&submit=")){
			chaines = data.split("&submit=");
			//chaines[0] contient les questions/reponse et donnees sur la matiere, date et nb de copies
			//chaines[1] contient l'information sur le submit
			
			if (data.contains("&question=")){
				
				questions = chaines[0].split("&question=");
				//chaque questions[i] contient la question et ses reponses
				//questions[0] contient les donnees sur la matière, la date et le nombre de copies
				
				for (int i=1 ; i<questions.length ; i++){//pour chaque question et ses reponses
					//System.out.println("question "+i+": "+questions[i]);
					
					String[] testMulti = questions[i].split("&bonne=");
					if (testMulti.length > 2 ){//s'il y a plusieurs bonnes reponses
						typeQuestion = "questionmult";
					}
					else {
						typeQuestion = "question";
					}
							
							
					questionsReponses = questions[i].split("&reponse=");
					
					questionnaire += "\t\\begin{"+typeQuestion+"}{Q"+i+"}\n";
					questionnaire += "\t\t"+questionsReponses[0]+"\n";
					questionnaire += "\t\t\\begin{reponses}\n";
					
					for (int j=1 ; j<questionsReponses.length ; j++){
						if (questionsReponses[j].contains("&bonne=")){//si la reponse est bonne
							questionnaire += "\t\t\t\\bonne{"+questionsReponses[j].split("&bonne=")[0]
									+"}\n";
						}
						else {
							questionnaire += "\t\t\t\\mauvaise{"+questionsReponses[j]+"}\n";
						}
						
						
					}
					questionnaire +="\t\t\\end{reponses}\n";
					questionnaire += "\t\\end{"+typeQuestion+"}\n\n";
					
					
					
				}
				
			}
			else {//ne contient pas "&question="
				return null;
			}
		}
		else {//ne contient pas "&submit="
			return null;
		}
		questionnaire += "\t%%% Fin du questionnaire\n\n";
		
		return questionnaire;
	}
}
