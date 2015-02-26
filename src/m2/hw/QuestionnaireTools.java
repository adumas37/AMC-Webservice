package m2.hw;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("questionnaireTools")
public class QuestionnaireTools {

	public static String createHeader(Questionnaire questionnaire){
		String entete= new String();
		entete = "\t%%% En-tête des copies \n\n" 
				+"\t\\noindent{\\bf QCM  \\hfill TEST}\n\n"
			
				+"\t\\vspace*{.5cm}\n"
				+"\t\\begin{minipage}{.4\\linewidth}\n"
				+"\t\\centering\\large\\bf "+ questionnaire.getMatiere() +"\\\\ Examen du " +questionnaire.getDate() +"\\end{minipage}\n"
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
	 * Permet de former le questionnaire en latex a partir des donnees recues depuis le webservice
	 * @param data
	 * @return
	 */
	public static String createBody(Questionnaire questionnaire){	
		String questionnaireBody = "\t%%% Debut du questionnaire\n\n";
		String[] chaines = null;
		
		String[] questionsReponses = null;
		String typeQuestion = null;
		String bareme = null;
		
		boolean multicols = false;
				
				
				//chaque questions[i] contient la question
				//Une question contient les réponses et le bareme
				
				for (int i=0 ; i<questionnaire.getQuestions().length ; i++){//pour chaque question
					
					if (questionnaire.getQuestions()[i].getNbBonnes() >= 2 ){
						//s'il y a plusieurs bonnes reponses
						typeQuestion = "questionmult";
					}
					else {
						typeQuestion = "question";
					}
					
					bareme = "\\bareme{b" 
							+ questionnaire.getQuestions()[i].getBareme() 
							+ "=" 
							+ questionnaire.getQuestions()[i].getBareme() 
							+"}";
					
					
					questionnaireBody += "\t\\begin{"+typeQuestion+"}{Q"+i+"}"+bareme+"\n";
					questionnaireBody += "\t\t"+questionnaire.getQuestions()[i].getTexte()+"\n";
					
					if(questionnaire.getQuestions()[i].isColonnes()){
						questionnaireBody += "\t\t\\begin{multicols}{2}\n";
					}
					questionnaireBody += "\t\t\\begin{reponses}\n";
					
					for (int j=0 ; j<questionnaire.getQuestions()[i].getReponses().length ; j++){
						if (questionnaire.getQuestions()[i].getReponses()[j].isCorrecte()){
							//si la reponse est bonne
							questionnaireBody += "\t\t\t\\bonne{"+questionnaire.getQuestions()[i].getReponses()[j].getTexte()+"}\n";
						}
						else {
							questionnaireBody += "\t\t\t\\mauvaise{"+questionnaire.getQuestions()[i].getReponses()[j].getTexte()+"}\n";
						}
						
						
					}
					questionnaireBody +="\t\t\\end{reponses}\n";
					if(questionnaire.getQuestions()[i].isColonnes()){
						questionnaireBody += "\t\t\\end{multicols}\n";
					}
					questionnaireBody += "\t\\end{"+typeQuestion+"}\n\n";
					
					
					
				}
				
			
			
	questionnaireBody += "\t\\clearpage\n\n";
	questionnaireBody += "\t%%% Fin du questionnaire\n\n";
		
		return decode(questionnaireBody);
	
}

/**
 * Renvoie une chaine en ayant remplacé les caracteres pseciaux par ceux attendus par l'utilisateur
 * @param s
 * @return
 */
private static String decode(String s){
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
		decoded = replace(decoded,"%E2%82%AC","\\euro{}");
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
 * Renvoie une Chaine copie de text dans laquelle on a remplacé substring par replaceWith
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
/**
 * Permet d'ecrire le fichier latex a partir des donnees recues
 * @param data
 */
public static void ecrireFichier(Questionnaire questionnaire){
	System.out.println(Utilisateurs.getCurrentUser());
	File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.tex");
    FileWriter fw;

    try {
      fw = new FileWriter(file);
      fw.write(formerImports());
      fw.write("\\begin{document}\n\n");
      fw.write("\\exemplaire{"+questionnaire.getNbCopies()+"}{\n\n");
      fw.write(questionnaire.getHeader());
      fw.write(questionnaire.getBody());
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
private static String formerImports(){
	String imports = new String();
	
	imports= "\\documentclass[a4paper]{article}\n\n"
			
			+"\\usepackage[utf8x]{inputenc}\n"
			+"\\usepackage[gen]{eurosym}\n"
			+"\\usepackage{fontenc}\n"
			+"\\usepackage{multicol}\n"
			+"\\usepackage[francais,bloc,completemulti]{automultiplechoice}\n\n";
	
	return imports;
}
/**
 * Permet de telecharger le questionnaire créé. Si le questionnaire n'a pas été créé auparavant,
 * on ne renvois rien.
 * @return
 */
@GET
@Produces("text/plain")
public Response getTextFile() {

	File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.tex");
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
 * Permet de renvoyer l'objet questionnaire en JSON
 * @return json string
 */
@GET
@Path("modification")
@Produces(MediaType.APPLICATION_JSON)
public static String modifierQuestionnaire(){
	Questionnaire questionnaire = ImportProjet();
	Gson gson = new Gson();
	String json = gson.toJson(questionnaire);
	return json;
}

/**
 * Permet d'exporter l'objet questionnaire au format binaire
 * @param questionnaire
 */
public static void ExportProjet(Questionnaire questionnaire){
	try {
		FileOutputStream fos = new FileOutputStream(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.bin");
		ObjectOutputStream oos= new ObjectOutputStream(fos);
		try {
			oos.writeObject(questionnaire); 
			oos.flush();
		} finally {
			try {
				oos.close();
			} finally {
				fos.close();
			}
		}
	} catch(IOException ioe) {
		ioe.printStackTrace();
	}
}

/**
 * Permet d'importer le fichier binaire du questionnaire du projet
 * @return
 */
public static Questionnaire ImportProjet(){
	Questionnaire questionnaire = null;
	try {
		FileInputStream fis = new FileInputStream(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.bin");
		ObjectInputStream ois= new ObjectInputStream(fis);
		try {	
			questionnaire = (Questionnaire) ois.readObject(); 
		} finally {
			try {
				ois.close();
			} finally {
				fis.close();
			}
		}
	} catch(IOException ioe) {
		return null;
		//ioe.printStackTrace();
	} catch(ClassNotFoundException cnfe) {
		cnfe.printStackTrace();
	}
	return questionnaire;
}
}
