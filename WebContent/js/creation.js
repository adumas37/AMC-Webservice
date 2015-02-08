function ajoutReponse(elmnt){

	// To change input id for latex formula : Which input is created, tag it to target it for Latex Formula Button
	var stringNameReponse = elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].getElementsByClassName("reponse")[0].getElementsByClassName("reponseInput")[0].id;
	var reponseNb = elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].childElementCount+1;
	stringNameReponse = stringNameReponse.substring(0, stringNameReponse.length-1);
	stringNameReponse += reponseNb;
	
	var reponse = document.getElementsByClassName("reponse")[0];
	var reponse2 = reponse.cloneNode(true);
	reponse2.getElementsByClassName("reponseInput")[0].value="";
	reponse2.getElementsByTagName("span")[0].getElementsByTagName("input")[0].checked=false;
	elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].appendChild(reponse2);

	// Change input id for latex formula (input id and href for latex formula button)
	reponse2.getElementsByClassName("reponseInput")[0].id = stringNameReponse;
	reponse2.getElementsByClassName("linkLatex")[0].href="javascript:OpenLatexEditor('"+stringNameReponse+"','latex','fr-fr')";
};

function ajoutQuestion(elmnt){

	// To change input id for latex formula : Which input is created, tag it to target it for Latex Formula Button
	var questionNb = document.getElementById("questionnaire").childElementCount;
	var stringNameReponse = "reponse "+questionNb+",1";

	var question1 = document.getElementsByClassName("blocQR")[0];
	var question = question1.cloneNode(true);

	question.getElementsByTagName("input")[0].value="";
	var reponses = question.getElementsByTagName("reponses")[0];
	if (reponses.childElementCount > 1 ) {
		while (reponses.childNodes.length >=2){
			reponses.removeChild(reponses.firstChild);
		}
	}	
	reponses.getElementsByClassName("reponseInput")[0].value="";
	reponses.getElementsByTagName("input")[0].checked=true;

	question.getElementsByClassName("questionInput")[0].id="question"+questionNb;
	question.getElementsByClassName("linkLatex")[0].href="javascript:OpenLatexEditor('question"+questionNb+"','latex','fr-fr')";
	reponses.getElementsByClassName("reponseInput")[0].id = stringNameReponse;
	reponses.getElementsByClassName("linkLatex")[0].href="javascript:OpenLatexEditor('"+stringNameReponse+"','latex','fr-fr')";

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

function chargerQuestionnaire(){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/creationQuestionnaire/modification",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	if (xhr.responseText!=""){
		document.getElementById("questionnaire").innerHTML = xhr.responseText;
	}
}

function questionnaireValide(){
	var reponseSansTexte = 0;
	var questionSansTexte = 0;
	var questionSansBonneReponse = 0;
	var matiere = false;
	var date = false;
	
	var blocsQR = document.getElementsByClassName("blocQR");
	var reponses;
	var bonnesReponses;
	var nbBonnesReponses;
	
	if (document.getElementById("matiereInput").value != ""){
		matiere = true;
	}
	if (document.getElementById("dateInput").value != ""){
		var date2 = document.getElementById("dateInput").value;
	    var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
	    if (!pattern.test(date2))
	    {
	    	date = false;
	    }
	    else{
	    	date = true;
	    }
	}
	
	
	for (var i = 0; i < blocsQR.length; i++) {
		if (blocsQR[i].getElementsByClassName("questionInput")[0].value == ""){ questionSansTexte ++; }
	
		reponses = blocsQR[i].getElementsByClassName("reponseInput");
		for (var j = 0; j < reponses.length; j++) {
			if (reponses[j].value == ""){ reponseSansTexte ++; }
		}
		
		bonnesReponses = blocsQR[i].getElementsByClassName("bonneInput");
		nbBonnesReponses = 0;
		for (var j = 0; j < bonnesReponses.length; j++) {
			if (bonnesReponses[j].checked == true) { nbBonnesReponses ++; }
		}
		if (nbBonnesReponses == 0){ questionSansBonneReponse ++; }
		
	}
	
	if (reponseSansTexte >0 || questionSansTexte >0 || questionSansBonneReponse > 0 || !matiere || !date){
		var alertText = "";
		if (!matiere){
			if (alertText != ""){ alertText += "\n"; }
			alertText += "Aucune matière specifiée. Une matière doit etre donnée pour continuer."; 
		}
		if (!date){
			if (alertText != ""){ alertText += "\n"; }
			alertText += "Date non spécifiée ou incorrecte. Le format correct est jj/mm/aaaa."; 
		}
		if(reponseSansTexte >0){ 
			if (alertText != ""){ alertText += "\n"; }
			alertText += "Il y a des réponses sans texte. Supprimez les réponses vides ou remplissez les."; 
		}
		if(questionSansTexte >0){ 
			if (alertText != ""){ alertText += "\n"; }
			alertText += "Il y a des questions sans texte. Supprimez les questions vides ou remplissez les."; 
		}
		if(questionSansBonneReponse >0){ 
			if (alertText != ""){ alertText += "\n"; }
			alertText += "Il y a des questions sans bonnes réponses. Choisissez au moins une bonne réponse par question."; 
		}
		alert(alertText);
		return false;
	}
	else {
		return true;
	}
}

function creationValide(){
	var name = false;
	var file = false;
	var projectExists = false;
	var projectName = document.getElementById("nomProjetInput").value;
	var filename = document.getElementById("fichierTexInput").value;
	var alertText = "";

	if ( projectName != "" ){ 
		name = true;
	}
	else {
		alertText = "Aucun nom de projet spécifié. Veuillez donner un nom au projet.";
	}
	
	if (filename != ""){
		console.log(filename);
		var nameList = filename.split(".");
		console.log(nameList);
		var extension = nameList[nameList.length-1];
		console.log(extension);
		if (extension=="tex") {
			file = true;
		}
		else {
			file = false;
			if (alertText != ""){ alertText += "\n"; }
			document.getElementById("fichierTexInput").value = "";
			alertText += "Le fichier fournit n'est pas au bon format. Le fichier doit etre un fichier Latex (.tex)."; 
		}
	}
	else {			
		file = true;
	}
		
	if (name == false || file == false){
		alert(alertText);
		return false;
	}
	else{
		var xhr = new XMLHttpRequest();
		xhr.open("POST","rest/ouvertureProjet",false);
		xhr.send(projectName);
		
		var projects = xhr.responseText.split("/");
		projects.forEach( function testExistingProject(project){
			if (project == projectName){ 
				projectExists = true;
			}
		});
		if (projectExists){
			alertText="Un projet nommé "+projectName+" existe déjà.";
			if (filename != ""){
				alertText += "\nSi vous souhaitez remplacer le questionnaire du projet par celui " +
						"que vous venez de choisir, cliquez sur OK. " +
						"\nPour annuler et conserver l'ancien projet ou changer de nom, cliquez sur Annuler.";
			}
			else {
				alertText += "\nSi vous souhaitez editer le questionnaire du projet, cliquez sur OK." +
						"\nPour annuler et choisir un nouveau nom de projet, cliquez sur Annuler.";
			}
			return confirm(alertText);
		}
		else {
			return true;
		}
	}
}

function eraseFile(){
	document.getElementById("fichierTexInput").value = "";
}

