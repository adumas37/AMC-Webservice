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

function demanderQuestionnaire(callback){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/questionnaireTools/modification",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.setRequestHeader("Accept-Encoding", "UTF-8");
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			var json = JSON.parse(xhr.responseText);
			if(json!=null){
				callback(json);
			}
		}
	};
	xhr.send(null);
};
function chargerQuestionnaire(json){
	
	var html='';
	html+='<p id="entete"> \
		<span id="matiere">Matiere:<input id="matiereInput" name="matiere" type="text" class="inputText inputButton" value="'+json.matiere+'"/></span> \
		<span id="date">Date (jj/mm/aaaa):<input id="dateInput" name="date" type="text" class="inputText inputButton" value="'+json.date+'"/></span> \
		<span id="nbCopies">Nombre d\'exemplaires de copies:<input id="nbCopiesImput" name="nbCopies" type="number" min="1" max="10" value="'+json.nbCopies+'" class="inputText inputButton"/></span> \
		</p>';
	for(i=0;i<json.questions.length;i++){
		html += '<blocQR class="blocQR"> \
		<p class="question"> \
		Question: <input type="text" name="question" class="questionInput inputText inputButton" value="'+json.questions[i].texte+'"/> \
		</p><reponses>';
		for(j=0;j<json.questions[i].reponses.length;j++){
			html += '<p class="reponse"> \
			Reponse: <input type="text" name="reponse" class="reponseInput inputText inputButton" value="'+json.questions[i].reponses[j].texte+'"/> \
			<span class="checkbox">Bonne reponse?<input class="bonneInput" type="checkbox" name="bonne"';
			if(json.questions[i].reponses[j].correcte){html+=' checked="true"';}
			html+='"/></span> \
			<span class="delQ"><input type="button" name="delQ" value="Supprimer reponse" onclick="supprReponse(this)" class="inputButton blueButton"/></span> \
			</p>';
		}
		html += '</reponses> \
		<options> \
		<span class="del"><input type="button" name="delQ" value="Supprimer question" onclick="supprQuestion(this)" class="inputButton blueButton"/></span> \
		<span class="addQ"><input type="button" name="addQ" value="Ajouter reponse" onclick="ajoutReponse(this)"  class="inputButton blueButton"/></span> \
		<span class="checkbox">Reponses horizontales?<input type="checkbox" name="horizontal"'+ (json.questions[i].colonnes?" checked":" ")+'/></span> \
		<span class="bareme">bareme:<input class="baremeImput inputText" name="bareme" type="number" min="1" max="20" value="'+json.questions[i].bareme+'"/></span> \
		</options> \
		</blocQR>';
		
	}
	document.getElementById("questionnaire").innerHTML = html;
};
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
		 document.getElementById("message").style.visibility='visible';
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
		

		var jsonobj=JSON.parse(jsonData);
		var count = Object.keys(jsonobj).length;

		var http = new XMLHttpRequest();
		var url = "rest/questionnaireTools/creation";
		http.open("POST", url, false);
		//On envoie l'objet JSON avec xmlhttprequest
		http.setRequestHeader("Content-type", "application/json; charset=UTF-8");
		http.setRequestHeader("Content-length", count);
		http.setRequestHeader("Accept-Encoding", "UTF-8");
		http.send(jsonData);
		//Afficher les messages d'erreur de compilation ?
		if(http.responseText=='1'){
			return true;
		}else{
			return false;
		}
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
		xhr.open("POST","rest/ouvertureProjet",true);
		xhr.send(projectName);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
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
	}
};

function eraseFile(){
	document.getElementById("fichierTexInput").value = "";
};

