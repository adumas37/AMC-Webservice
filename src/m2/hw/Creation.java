package m2.hw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/*TODO
 *		- Ajout de photos dans les question
 *		- Ajout d'autres types de questions
 *		- Ammeliorer la robustesse
 *		- Mieux prendre en charge les caracteres speciaux
 */

@Path("creation")
public class Creation {

	private static String filePath = "questionnaire.tex";
	/**
	 * Fonction appellee par le serveur lors de la creation d'un questionnaire
	 * Permet de creer un fichier latex sur le serveur.
	 * @param data
	 */
	@POST
	public void creation(String data){
		ecrireFichier(data);
	}
	
	/**
	 * Permet de telecharger le questionnaire créé. Si le questionnaire n'a pas été créé auparavant,
	 * on ne renvois rien.
	 * @return
	 */
	@GET
    @Produces("text/plain")
    public Response getTextFile() {

		File file = new File(filePath);
		if (file.exists()){
			ResponseBuilder response = Response.ok((Object) file);
	        response.header("Content-Disposition", "attachment; filename=\"questionnaire.tex\"");
	        return response.build();
		}
		else {
			return null;
		}

    }
	
	/**
	 * Permet d'ecrire le fichier latex a partir des donnees recues
	 * @param data
	 */
	private void ecrireFichier(String data){
		File file = new File(filePath);
	    FileWriter fw;
	    int nbCopies = 1;
	    
	    if (data.contains("nbCopies=")){
			nbCopies = Integer.parseInt(decode(data.split("nbCopies=")[1].split("&")[0]));
		}
			
	    try {
	      fw = new FileWriter(file);
	      fw.write(formerImports());
	      fw.write("\\begin{document}\n\n");
	      fw.write("\\exemplaire{"+nbCopies+"}{\n\n");
	      fw.write(formerEntete(data));
	      fw.write(formerQuestionnaire(data));
	      fw.write("}\n\n\\end{document}");
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
		String matiere = "VVVVVVV";
		String date = "JJ/MM/AAAA";
				
		if (data.contains("date=")){
			date = decode(data.split("date=")[1].split("&")[0]);
		}
		if (data.contains("matiere=")){
			matiere = decode(data.split("matiere=")[1].split("&")[0]);
		}
		
		
		entete = "\t%%% En-tête des copies \n\n" 
				+"\t\\noindent{\\bf QCM  \\hfill TEST}\n\n"
			
				+"\t\\vspace*{.5cm}\n"
				+"\t\\begin{minipage}{.4\\linewidth}\n"
				+"\t\\centering\\large\\bf "+ matiere +"\\ Examen du " +date +"\\end{minipage}\n"
				+"\t\\champnom{\\fbox{\n"
					            +"\t\t\t\\begin{minipage}{.5\\linewidth}\n"
					            +"\t\t\tNom et prénom :\n\n"
			
					            +"\t\t\t\\vspace*{.5cm}\\dotfill\n"
					            +"\t\t\t\\vspace*{1mm}\n"
					            +"\t\t\t\\end{minipage}\n"
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
		questionnaire += "\t\\clearpage\n\n";
		questionnaire += "\t%%% Fin du questionnaire\n\n";
		
		return decode(questionnaire);
	}

	/**
	 * Renvois une chaine en ayant remplacé les caracteres pseciaux par ceux attendus par l'utilisateur
	 * @param s
	 * @return
	 */
	private String decode(String s){
		String decoded = new String(s);
		
		if (decoded.contains("+")){
			decoded = replace(decoded,"+"," ");
		}
		if (decoded.contains("%21")){
			decoded = replace(decoded,"%21","!");
		}
		if (decoded.contains("%22")){
			decoded = replace(decoded,"%22","\"");
		}
		if (decoded.contains("%23")){
			decoded = replace(decoded,"%23","#");
		}
		if (decoded.contains("%24")){
			decoded = replace(decoded,"%24","$");
		}
		if (decoded.contains("%25")){
			decoded = replace(decoded,"%25","\\%");
		}
		if (decoded.contains("%26")){
			decoded = replace(decoded,"%26","&");
		}
		if (decoded.contains("%27")){
			decoded = replace(decoded,"%27","'");
		}
		if (decoded.contains("%28")){
			decoded = replace(decoded,"%28","(");
		}
		if (decoded.contains("%29")){
			decoded = replace(decoded,"%29",")");
		}
		if (decoded.contains("%2A")){
			decoded = replace(decoded,"%2A","*");
		}
		if (decoded.contains("%2B")){
			decoded = replace(decoded,"%2B","+");
		}
		if (decoded.contains("%2C")){
			decoded = replace(decoded,"%2C",",");
		}
		if (decoded.contains("%2D")){
			decoded = replace(decoded,"%2D","-");
		}
		if (decoded.contains("%2E")){
			decoded = replace(decoded,"%2E",".");
		}
		if (decoded.contains("%2F")){
			decoded = replace(decoded,"%2F","/");
		}
		if (decoded.contains("%3A")){
			decoded = replace(decoded,"%3A",":");
		}
		if (decoded.contains("%3B")){
			decoded = replace(decoded,"%3B",";");
		}
		if (decoded.contains("%3C")){
			decoded = replace(decoded,"%3C","<");
		}
		if (decoded.contains("%3D")){
			decoded = replace(decoded,"%3D","=");
		}
		if (decoded.contains("%3E")){
			decoded = replace(decoded,"%3E",">");
		}
		if (decoded.contains("%3F")){
			decoded = replace(decoded,"%3F","?");
		}
		if (decoded.contains("%40")){
			decoded = replace(decoded,"%40","@");
		}
		if (decoded.contains("%5B")){
			decoded = replace(decoded,"%5B","[");
		}
		if (decoded.contains("%5C")){
			decoded = replace(decoded,"%5C","\\");
		}
		if (decoded.contains("%5D")){
			decoded = replace(decoded,"%5D","]");
		}
		if (decoded.contains("%5E")){
			decoded = replace(decoded,"%5E","^");
		}
		if (decoded.contains("%5F")){
			decoded = replace(decoded,"%5F","_");
		}
		if (decoded.contains("%60")){
			decoded = replace(decoded,"%60","`");
		}
		if (decoded.contains("%7B")){
			decoded = replace(decoded,"%7B","{");
		}
		if (decoded.contains("%7C")){
			decoded = replace(decoded,"%7C","|");
		}
		if (decoded.contains("%7D")){
			decoded = replace(decoded,"%7D","}");
		}
		if (decoded.contains("%7E")){
			decoded = replace(decoded,"%7E","~");
		}
		if (decoded.contains("%7F")){
			decoded = replace(decoded,"%7F","");
		}
		if (decoded.contains("%E2%82%AC")){
			decoded = replace(decoded,"%E2%82%AC","€");
		}
		if (decoded.contains("%C2%A7")){
			decoded = replace(decoded,"%C2%A7","§");
		}
		if (decoded.contains("%C3%A0")){
			decoded = replace(decoded,"%C3%A0","à");
		}
		if (decoded.contains("%C3%A7")){
			decoded = replace(decoded,"%C3%A7","ç");
		}
		if (decoded.contains("%C3%A9")){
			decoded = replace(decoded,"%C3%A9","é");
		}
		if (decoded.contains("%C3%A8")){
			decoded = replace(decoded,"%C3%A8","è");
		}
		if (decoded.contains("%C3%B9")){
			decoded = replace(decoded,"%C3%B9","ù");
		}
			
		return decoded;
	}
	
	/**
	 * Renvois une Chaine copie de text dans laquelle on a remplacé substring par replaceWith
	 * @param text
	 * @param substring
	 * @param replaceWith
	 * @return
	 */
	public static String replace(String text, String substring, String replaceWith){
		
		int s = 0;
		int e = 0;
		
		StringBuffer newText = new StringBuffer();
		
		while ((e = text.indexOf(substring, s))>=0){
			newText.append(text.substring(s,e));
			newText.append(replaceWith);
			s = e + substring.length();
		}
		newText.append(text.substring(s));
		
		return newText.toString();
	}
}
