/**
 * 
 */

function ajoutReponse(elmnt){
	//elmnt.parentNode.parentNode.parentNode.style.backgroundColor="red";
	
	/*var question = document.createElement('p');
	question.id = "reponse";
	var texte = document.createTextNode("Reponse: ");
	
	var question = document.createElement('p');
	question.id = "reponse";
	var texte = document.createTextNode("Reponse: ");
	var inputText = document.createElement('input');
	inputText.type="text";
	inputText.name="testName";
	inputText.id="reponseInput";
	texte.appendChild(inputText);
	question.appendChild(texte);*/
	
	var reponse = document.getElementById("reponse");
	var reponse2 = reponse.cloneNode(true);
	
	elmnt.parentNode.parentNode.parentNode.appendChild(reponse2);
};

function suprReponse(elmnt){
	var element = elmnt.parentNode.parentNode;
	element.parentNode.removeChild(element);
}


/*
<reponses>
<p id="reponse">
	Reponse: <input type="text" name="r1.1" id="reponseInput"/>
	<span id="checkbox">Bonne reponse?<input type="checkbox" name="c1"/></span>
	<span id="add"><input type="button" name="addQ" value="Ajouter reponse" onclick="ajoutReponse(this)" /></span>
</p>
</reponses>
*/