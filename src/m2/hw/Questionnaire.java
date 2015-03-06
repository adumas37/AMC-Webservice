package m2.hw;
import java.io.Serializable;

public class Questionnaire implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String matiere;
	private String date;
	private int nbCopies;
	private String header;
	private String body;
	private Question[] questions;
	
	
	public Questionnaire(String matiere, String date, Question[] questions, boolean colonnes, int nbCopies){
		setDate(date);
		setMatiere(matiere);
		setQuestions(questions);
		setNbCopies(nbCopies);
		setHeader(QuestionnaireTools.createHeader(this));
		setBody(QuestionnaireTools.createBody(this));
		
	}

	public String getMatiere() {
		return matiere;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString(){
		String result="";
		result+= "MATIERE :"+getMatiere()+"  DATA : "+getDate()+"  copies : "+getNbCopies();
		result+="  Questions : \n";
		for(int i=0;i<getQuestions().length;i++){
			result+="Question"+i+" : "+getQuestions()[i].toString()+"\n";
		}
			return result;	
	}

	public Question[] getQuestions() {
		return questions;
	}

	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getNbCopies() {
		return nbCopies;
	}

	public void setNbCopies(int nbCopies) {
		this.nbCopies = nbCopies;
	}
}
