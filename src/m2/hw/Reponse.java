package m2.hw;

public class Reponse {
	private String texte;
	private boolean correcte;
	
	public Reponse(String texte, boolean correcte){
		setTexte(texte);
		setCorrecte(correcte);
	}

	@Override
	public String toString(){
		return "texte : "+getTexte()+" bonne : "+isCorrecte();
	}
	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public boolean isCorrecte() {
		return correcte;
	}

	public void setCorrecte(boolean correcte) {
		this.correcte = correcte;
	}
}
