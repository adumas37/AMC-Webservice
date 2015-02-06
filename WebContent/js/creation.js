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
	reponses.getElementsByClassName("reponseInput")[0].value="";
	reponses.getElementsByTagName("input")[0].checked=true;

	document.getElementById("questionnaire").appendChild(question);
};

function supprReponse(elmnt){
	
	var element = elmnt.parentNode.parentNode;
	var nbChild = elmnt.parentNode.parentNode.parentNode.childElementCount;
	if (nbChild>1){
		element.parentNode.removeChild(element);
	}
	
};

function supprQuestion(elmnt){
	
	var element = elmnt.parentNode.parentNode.parentNode;
	var nbChild = elmnt.parentNode.parentNode.parentNode.parentNode.childElementCount;
	if (nbChild>2){
		element.parentNode.removeChild(element);
	}
	
};

function chargerQuestionnaire(){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/creationQuestionnaire/modification",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	if (xhr.responseText!=""){
		document.getElementById("questionnaire").innerHTML = xhr.responseText;
	}
};

function questionnaireValide(){
	console.log("kkkkkkk");
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
		if (blocsQR[i].getElementsByClassName("questionInput")[0].value == ""){ 
			questionSansTexte ++; 
		}
	
		reponses = blocsQR[i].getElementsByClassName("reponseInput");
		for (var j = 0; j < reponses.length; j++) {
			if (reponses[j].value == ""){ 
				reponseSansTexte ++;
			}
		}
		
		bonnesReponses = blocsQR[i].getElementsByClassName("bonneInput");
		nbBonnesReponses = 0;
		for (var j = 0; j < bonnesReponses.length; j++) {
			if (bonnesReponses[j].checked == true) { 
				nbBonnesReponses ++; 
			}
		}
		if (nbBonnesReponses == 0){ 
			questionSansBonneReponse ++;
		}
		
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
		//Ici on définit les attributs d'une classe java
		//Dans la classe java, les attributs doivent etre précédés de la mention @jsonproperty
		//ex: @JsonProperty("nom") private String nom;

		
		
		//"colonnes":header.getElementsByName("horizontal")[0].checked

		var jsonData ='{"matiere":"'+document.getElementById("matiereInput").value+'", \
			"date":"'+document.getElementById("dateInput").value+'", \
			"nbCopies":"'+document.getElementById("nbCopiesImput").value+'", \
			"questions":[';

	for(i=0;i<blocsQR.length;i++){
		if(i>0){
			jsonData+=',';
		}
		jsonData+='{"texte":"'+blocsQR[i].getElementsByClassName("question")[0].getElementsByClassName("questionInput")[0].value+'", \
		"bareme":"2","reponses": \
	            	  	[';
		for(j=0;j<blocsQR[i].getElementsByClassName("reponse").length;j++){
			if(j>0){
				jsonData+=',';
			}
			jsonData+='{"texte":"'+blocsQR[i].getElementsByClassName("reponse")[j].getElementsByClassName("reponseInput")[0].value+'", \
			"correcte":"'+blocsQR[i].getElementsByClassName("reponse")[j].getElementsByClassName("bonneInput")[0].checked+'"}';
		}
		jsonData+=']}';

	}
		jsonData+=']}';
		
		console.log(jsonData);
		var jsonobj=JSON.parse(jsonData);
		var count = Object.keys(jsonobj).length;
		console.log(jsonobj);
		var http = new XMLHttpRequest();
		var url = "rest/creationQuestionnaireJSON";
		http.open("POST", url, false);
		//On envoie l'objet JSON avec xmlhttprequest
		http.setRequestHeader("Content-type", "application/json");
		http.setRequestHeader("Content-length", count);
		http.send(jsonData);
		
		return true;
	}
};

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
};

function eraseFile(){
	document.getElementById("fichierTexInput").value = "";
};

