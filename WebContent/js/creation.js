/**
 * 
 */

function ajoutReponse(elmnt){
	
	var reponse = document.getElementsByClassName("reponse")[0];
	var reponse2 = reponse.cloneNode(true);
	reponse2.getElementsByClassName("reponseInput")[0].value="";
	reponse2.getElementsByTagName("span")[0].getElementsByTagName("input")[0].checked=false;
	elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].appendChild(reponse2);
};

function ajoutQuestion(elmnt){
	
	var question1 = document.getElementsByClassName("blocQR")[0];
	var question = question1.cloneNode(true);

	question.getElementsByTagName("input")[0].value="";
	var reponses = question.getElementsByTagName("reponses")[0];
	if (reponses.childElementCount > 1 ) {
		while (reponses.childNodes.length >=2){
			reponses.removeChild(reponses.firstChild);
		}
	}	
	reponses.firstChild.value="";
	reponses.firstChild.checked=false;

	document.getElementById("questionnaire").appendChild(question);
};

function supprReponse(elmnt){
	
	var element = elmnt.parentNode.parentNode;
	var nbChild = elmnt.parentNode.parentNode.parentNode.childElementCount;
	if (nbChild>1){
		element.parentNode.removeChild(element);
	}
	
}

function supprQuestion(elmnt){
	
	var element = elmnt.parentNode.parentNode.parentNode;
	var nbChild = elmnt.parentNode.parentNode.parentNode.parentNode.childElementCount;
	if (nbChild>2){
		element.parentNode.removeChild(element);
	}
	
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