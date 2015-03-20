package m2.hw;

import java.io.Serializable;

public class Reponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String texte;
	private String image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
