package m2.hw;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("questionnaireTools")
public class QuestionnaireTools {

	/**
	 * Permet de créer un questionnaire depuis le json reçu 
	 * @param data
	 */
	@POST
	@Path("creation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public String creation(String data){
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject obj =parser.parse(data).getAsJsonObject();
		System.out.println(obj.get("questions"));
		Questionnaire quest=gson.fromJson(obj,Questionnaire.class);
		quest.setHeader(QuestionnaireTools.createHeader(quest));
		quest.setBody(QuestionnaireTools.createBody(quest));
		QuestionnaireTools.ecrireFichier(quest);
		CommandesAMC.prepareProject("questionnaire.tex");
		//ATTENDRE LA FIN DE COMPILATION ?
		QuestionnaireTools.exportProjet(quest);
		//On peut renvoyer les messages d'erreur de la compilation
		//Ici 1 nous informe que tout est OK
        return "1";
	}
	
	/**
	 * Permet de créer le header latex
	 * @param questionnaire
	 * @return
	 */
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
				+"\tDurée : "+questionnaire.getDuree()+ ".\n\n"
			
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
		
		String typeQuestion = null;
		String bareme = null;
						
				
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
	Questionnaire questionnaire = importProjet();
	Gson gson = new Gson();
	String json = gson.toJson(questionnaire);
	return json;
}

/**
 * Permet d'exporter l'objet questionnaire au format binaire
 * @param questionnaire
 */
public static void exportProjet(Questionnaire questionnaire){
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
public static Questionnaire importProjet(){
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

public static Questionnaire importFichier(){
	
	Questionnaire quest = null;
	String matiere = "";
	String date = "";
	String duree = "";
	int nbCopies = 0;
	String header = "";
	String body = "";
	ArrayList<Question> questions = new ArrayList<Question>();
	
	
	File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.tex");
		
	try (
		BufferedReader br = new BufferedReader(new FileReader(file))) {
	    String line;
	    while ((line = br.readLine()) != null) {
	       
	    	if (line.contains("\\exemplaire{")){
	    		nbCopies = Integer.parseInt(line.substring(line.indexOf("{")+1, line.indexOf("}")));
	    	}
	    	else if (line.contains("\\centering\\large\\bf")){
	    		matiere = line.split(" ")[1].substring(0, line.split(" ")[1].length()-2);
	    		date = line.split(" ")[4].substring(0, 10);
	    	}
	    	else if (line.contains("Durée : ")){
	    		duree = line.split("Durée : ")[1].substring(0, line.split("Durée : ")[1].length()-1);
	    	}
	    	else if (line.contains("\\begin{question")){	    		
	    		String texte = "";
	    		int bareme = 0;
	    		boolean col = false;
	    		ArrayList<Reponse> reponses = new ArrayList<Reponse>() ;
	    		
	    		if (line.contains("\\bareme{")){
	    			bareme=Integer.parseInt(line.split("bareme")[1].split("b")[1].split("=")[0]);
	    		}
	    		else {
	    			bareme=1;
	    		}
	    		texte = br.readLine().replace("\t", "");
	    		while (!line.contains("\\end{reponses}")){
	    			if (line.contains("\\begin{multicols}")){
	    				col = true;
	    			}
		    		if (line.contains("\\bonne{") || line.contains("\\mauvaise{")){
			    		reponses.add(new Reponse(line.split("\\{")[1].split("\\}")[0],line.contains("\\bonne{")));
		    		}
		    		line = br.readLine();
	    		}
	    		
	    		Reponse[] rep = new Reponse[reponses.size()];
	    		questions.add(new Question(texte, reponses.toArray(rep),bareme,col));
	    	}
	    	
	    	
	    }
	    System.out.println("nbC: "+nbCopies+", matiere: "+matiere+", date: "+date);
	    
	    Question[] questionslist = new Question[questions.size()];
	    quest = new Questionnaire(matiere, date, duree, questions.toArray(questionslist) ,nbCopies);
	    quest.setHeader(QuestionnaireTools.createHeader(quest));
		quest.setBody(QuestionnaireTools.createBody(quest));
		QuestionnaireTools.exportProjet(quest);
	    br.close();
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	return quest;
}

	/**
	* Permet de renvoyer une partie du code html de la page de modification du bareme.
	* Lis le fichier questionnaire.tex du projet courant.
	* @return
	*/
	@GET
	@Path("getBareme")
	public static String getBareme(){
		String html="";
		if (new File(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.tex").exists())
		try(BufferedReader br = new BufferedReader(new FileReader(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.tex"))) {
			String bareme = "1";
			String question = "";
			String line = br.readLine();
			while (line != null) {
				if (line.contains("\\begin{question")){
					bareme="1";
					question="";
				if (line.contains("\\bareme{")){
					bareme=line.split("bareme")[1].split("b")[1].split("=")[0];
				}
				else {
					bareme="1";
				}
				question = br.readLine();
				question = question.replaceAll("\\t", "");
				if (question.contains("\\euro{}")){
					question = replace(question,"\\euro{}","€");
				}
				while (!line.contains("\\end{reponses}")){
					line = br.readLine();
					}
					html += "<blocQB class=\"blocQB\">" +
					"<p class=\"question\">" +
					"Question: <span class=\"question\">"+question +"</span>" +
					"<span class=\"bareme\">bareme:<input class=\"baremeImput inputText\" name=\"bareme\" type=\"number\" min=\"1\" max=\"20\" value=\""+bareme+"\"/></span>" +
					"</p>" +
					"</blocQB>";
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return html;
	}

	
	@POST
	@Path("setBareme")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public static void setBareme(FormDataMultiPart formParams){
		
		Questionnaire quest = importProjet();
		Question[] questions = quest.getQuestions();
		int i=0;
		
		List<FormDataBodyPart> baremes = formParams.getFields("bareme");
		ListIterator<FormDataBodyPart> baremeIterator = baremes.listIterator();
		File file = new File(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire.tex");
		File file2 = new File(Utilisateurs.getCurrentUser().getProjectPath()+"questionnaire2.tex");
		
		if (file.exists()){
			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
				FileWriter fw = new FileWriter(file2);
				String line = br.readLine();
				while (line != null) {
					if (line.contains("\\begin{question")){
						if (line.contains("\\bareme{")){
							String bareme="1";
							if (baremeIterator.hasNext()){
								bareme = ((FormDataBodyPart) baremeIterator.next()).getValue();
								questions[i].setBareme(Integer.parseInt(bareme));
								i++;
							}
							line=line.split("bareme")[0]+"bareme{b"+bareme+"="+bareme+"}";
						}
						while (!line.contains("\\end{reponses}")){
							fw.write(line+"\n");
							line = br.readLine();
						}
					}
					fw.write(line+"\n");
					line = br.readLine();
				}
				exportProjet(quest);
				br.close();
				fw.close();
				file2.renameTo(file);
			} catch (IOException e) {
			e.printStackTrace();
			}
		}
	}
	@POST
	@Path("uploadImage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	public static String uploadImage(@FormDataParam("imageData") InputStream uploadedInputStream,@FormDataParam("imageNb") String imgName){
	    
	    System.out.println(imgName);
	    String uploadedFileLocation = Utilisateurs.getCurrentUser().getProjectPath()+"/"+imgName;
	    CreationProjet.saveFile(uploadedInputStream, uploadedFileLocation);
	    return "1";
	}
}
