package m2.hw;

import org.codehaus.jackson.annotate.JsonProperty;



public class Question {
	
	@JsonProperty("nom") private String nom;
	@JsonProperty("prenom") private String prenom;
	public Question(){
		
	}
	
	public Question(String nom, String prenom){
		setNom(nom);
		setPrenom(prenom);
	}
	
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	@Override
	public String toString(){
		return nom+" - "+prenom;
	}
}
