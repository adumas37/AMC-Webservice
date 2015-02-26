package m2.hw;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String texte;
	private Reponse[] reponses;
	private int bareme;
	private int nbBonnes;
	private boolean colonnes;
	
	public Question(String texte, Reponse[] reponses, int bareme){
		setTexte(texte);
		setReponses(reponses);
		setBareme(bareme);
		for(int i=0;i<reponses.length;i++){
			if(reponses[i].isCorrecte()){
				setNbBonnes(getNbBonnes()+1);
			}
		}
	}

	@Override
	public String toString(){
		String result="";
		result+="texte : "+getTexte()+"colonnes : "+isColonnes()+" bareme : "+getBareme()+" Reponses : \n";
		
		for(int i=0;i<getReponses().length;i++){
			result+="Reponse"+i+" : "+getReponses()[i].toString()+"\n";
		}
		return result;
		
	}

	public String getTexte() {
		return texte;
	}


	public void setTexte(String texte) {
		this.texte = texte;
	}


	public Reponse[] getReponses() {
		return reponses;
	}


	public void setReponses(Reponse[] reponses) {
		this.reponses = reponses;
	}


	public int getBareme() {
		return bareme;
	}


	public void setBareme(int bareme) {
		this.bareme = bareme;
	}


	public int getNbBonnes() {
		return nbBonnes;
	}


	public void setNbBonnes(int nbBonnes) {
		this.nbBonnes = nbBonnes;
	}

	public boolean isColonnes() {
		return colonnes;
	}

	public void setColonnes(boolean colonnes) {
		this.colonnes = colonnes;
	}
	

}
