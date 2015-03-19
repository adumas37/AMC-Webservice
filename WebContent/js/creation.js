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
	question.getElementsByClassName("imageChargee")[0].src="";
	question.getElementsByClassName("imgNb")[0].innerHTML="";
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
		<span id="duree">Durée:<select id="dureeInput" name="duree" class="inputText inputButton">\
			<option value="0h10">0h10</option> \
			<option value="0h15">0h15</option>\
			<option value="0h20">0h20</option>\
			<option value="0h25">0h25</option>\
			<option value="0h30">0h30</option>\
			<option value="0h35">0h35</option>\
			<option value="0h40">0h40</option>\
			<option value="0h45">0h45</option>\
			<option value="0h50">0h50</option>\
			<option value="0h55">0h55</option>\
			<option value="1h00">1h00</option>\
			<option value="1h05">1h05</option>\
			<option value="1h10">1h10</option>\
			<option value="1h15">1h15</option>\
			<option value="1h20">1h20</option>\
			<option value="1h25">1h25</option>\
			<option value="1h30">1h30</option>\
			<option value="1h35">1h35</option>\
			<option value="1h40">1h40</option>\
			<option value="1h45">1h45</option>\
			<option value="1h50">1h50</option>\
			<option value="1h55">1h55</option>\
			<option value="2h00">2h00</option>\
		</select></span>\
		<span id="date">Date (jj/mm/aaaa):<input id="dateInput" name="date" type="text" class="inputText inputButton" value="'+json.date+'"/></span> \
		<span id="nbCopies">Nombre d\'exemplaires de copies:<input id="nbCopiesImput" name="nbCopies" type="number" min="1" max="10" value="'+json.nbCopies+'" class="inputText inputButton"/></span> \
		</p>';
	for(i=0;i<json.questions.length;i++){
		html += '<blocQR class="blocQR"> \
		<p class="question"> \
		Question: <input type="text" id="question'+i+'" name="question" class="questionInput inputText inputButton" value="'+json.questions[i].texte+'"/> \
		<a class="linkLatex" href="javascript:OpenLatexEditor(\'question'+i+'\',\'latex\',\'fr-fr\')"><img src="src/formula_icon.png"></a> \
		<a class="linkImage" href="#" onclick="showUploadImageFunctions(this.parentNode);" > \
		<img class="linkPicture" src="src/img.png"></a> \
		<img class="imageChargee" style="display:block;margin:auto;max-width:300px;max-height:150px;" src=""/> \
		<a class="delImage" style="visibility:hidden;" href="#" onclick="deleteImageOnServer(this);" > \
		<img class="linkPicture" src="src/delImg.png">Supprimer</a> \
		<input type="file" onchange="uploadImageOnServer(this);" class="uploadImage" name="uploadImage" style="visibility: hidden; width: 1px; height: 1px"/> \
		<span class="imgNb" style="visibility:hidden;">';
		if(typeof json.questions[i].image!= "undefined"){
			html+=json.questions[i].image;
			if(parseInt(json.questions[i].image.split(".")[0])>imageIndex){
				imageIndex=parseInt(json.questions[i].image.split(".")[0]);
			}
		}
		html+='</span> \
		</p><reponses>';
		getImages(json.questions[i].image,document.getElementsByClassName("question"),i);
		
		for(j=0;j<json.questions[i].reponses.length;j++){
			html += '<p class="reponse"> \
			Reponse: <input type="text" id="reponse '+i+','+j+'" name="reponse" class="reponseInput inputText inputButton" value="'+json.questions[i].reponses[j].texte+'"/> \
			<a class="linkLatex" href="javascript:OpenLatexEditor(\'reponse '+i+','+j+'\',\'latex\',\'fr-fr\')"><img src="src/formula_icon.png"></a> \
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
	document.getElementById("dureeInput").value=json.duree;
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
		var errorText = "";
		if (!matiere){
			errorText += "Aucune matière specifiée !</br><i>Une matière doit etre donnée pour continuer.</i></br>"; 
		}
		if (!date){
			errorText += "Date non spécifiée ou incorrecte !</br><i>Le format correct est jj/mm/aaaa.</i></br>"; 
		}
		if(reponseSansTexte >0){ 
			errorText += "Il y a des réponses sans texte !</br><i>Supprimez les réponses vides ou remplissez les.</i></br>"; 
		}
		if(questionSansTexte >0){ 
			errorText += "Il y a des questions sans texte !</br><i>Supprimez les questions vides ou remplissez les.</i></br>"; 
		}
		if(questionSansBonneReponse >0){ 
			errorText += "Il y a des questions sans bonnes réponses !</br><i>Choisissez au moins une bonne réponse par question.</i></br>"; 
		}
		showMessage("error",errorText);
		
	}
	else {
		var jsonData ='{"matiere":"'+document.getElementById("matiereInput").value+'", \
			"date":"'+document.getElementById("dateInput").value+'", \
			"duree":"'+document.getElementById("dureeInput").value+'", \
			"nbCopies":"'+document.getElementById("nbCopiesImput").value+'", \
			"questions":[';

		for(i=0;i<blocsQR.length;i++){
			if(i>0){
				jsonData+=',';
			}
			var imgName="";

			if(blocsQR[i].getElementsByClassName("question")[0].getElementsByClassName("imgNb")[0].innerHTML!=""){
				imgName=blocsQR[i].getElementsByClassName("question")[0].getElementsByClassName("imgNb")[0].innerHTML;
			}
			jsonData+='{"image":"'+imgName+'","texte":"'+blocsQR[i].getElementsByClassName("question")[0].getElementsByClassName("questionInput")[0].value.replace(/\\/g,"\\\\")+'", \
			"bareme":"2","reponses": \
		            	  	[';
			for(j=0;j<blocsQR[i].getElementsByClassName("reponse").length;j++){
				if(j>0){
					jsonData+=',';
				}
				jsonData+='{"texte":"'+blocsQR[i].getElementsByClassName("reponse")[j].getElementsByClassName("reponseInput")[0].value.replace(/\\/g,"\\\\")+'", \
				"correcte":"'+blocsQR[i].getElementsByClassName("reponse")[j].getElementsByClassName("bonneInput")[0].checked+'"}';
			}
			jsonData+=']}';
	
		}
		jsonData+=']}';
		showMessage("wait","Création du questionnaire...");
		sendQuestionnaire(stateQuestionnaireCompilation,jsonData);
		
	}
	return false;

};

function sendQuestionnaire(callback, data){
	var jsonobj=JSON.parse(data);
	var count = Object.keys(jsonobj).length;

	var xhr = new XMLHttpRequest();
	var url = "rest/questionnaireTools/creation";
	xhr.open("POST", url, true);
	//On envoie l'objet JSON avec xmlhttprequest
	xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
	
	//Afficher les messages d'erreur de compilation ?
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0) ) {
			callback(xhr.responseText);
		}
	}
	xhr.send(data);
	
	

};
function stateQuestionnaireCompilation(code){
	if(code=='1'){
		 document.location.href="Projet.php";
	}else{
		showMessage("error",code);
	}
};
function creationValide(){
	var name = false;
	var file = false;
	var projectExists = false;
	var projectName = document.getElementById("nomProjetInput").value;
	var filename = document.getElementById("fichierTexInput").value;

	if ( projectName != "" ){ 
		name = true;
	}
	else {
		showMessage("error","Aucun nom de projet spécifié.</br><i>Veuillez donner un nom au projet !</i>");
	}
	
	if (filename != ""){
		var nameList = filename.split(".");
		var extension = nameList[nameList.length-1];
		if (extension=="tex") {
			file = true;
		}
		else {
			file = false;
			document.getElementById("fichierTexInput").value = "";
			showMessage("error","Le fichier fourni n'est pas au bon format.</br><i>Le fichier doit etre un fichier Latex (.tex) !</i>");
		}
	}
	else {			
		file = true;
	}
	
	if (name == false || file == false){
		return;
	}
	else{
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST","rest/ouvertureProjet",false);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
				var projects=xhr.responseText.split("/");
				projects.forEach( function testExistingProject(project){
					if (project == projectName){ 
						projectExists = true;
					}
				});
				
				if (projectExists){
					if (filename != ""){
						showMessage("question","Un projet nommé <b>"+projectName+"</b> existe déjà !"+
								"</br><i>Si vous souhaitez remplacer le questionnaire du projet par celui " +
								"que vous venez de choisir, cliquez sur OK." +
								"</br>Pour annuler et conserver l'ancien projet ou changer de nom, cliquez sur Annuler.</i>","document.forms.formulaireProjet.submit()");
					}
					else {
						showMessage("question","Un projet nommé <b>"+projectName+"</b> existe déjà !"+
								"</br><i>Si vous souhaitez editer le questionnaire du projet, cliquez sur OK." +
								"</br>Pour annuler et choisir un nouveau nom de projet, cliquez sur Annuler.</i>","document.forms.formulaireProjet.submit()");
					}
				}else{
					document.forms.formulaireProjet.submit();
				}
			}
		};
		
		xhr.send(projectName);
		
	}
	
	
	
};

function eraseFile(){
	document.getElementById("fichierTexInput").value = "";
};



var imageIndex=0;

function showUploadImageFunctions(element){
	element.getElementsByClassName('uploadImage')[0].click();
	console.log("coucou");
}
function uploadImageOnServer(element){

	if(element.parentNode.getElementsByClassName("imgNb")[0].innerHTML!=""){
		imageIndex=parseInt(element.parentNode.parentNode.getElementsByClassName("imgNb")[0].innerHTML.split(".")[0]);
		deleteImageOnServer(element);
	}else{
		imageIndex=imageIndex+1;
	}	
	showMessage("wait","Upload de l'image sur le serveur...");
	var file=element.files[0];
	var fd=new FormData();
	
	var nameList = element.value.split(".");
	var extension = nameList[nameList.length-1];
	
	fd.append("imageNb",imageIndex+"."+extension);
	fd.append("imageData",file);
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'rest/questionnaireTools/uploadImage', true);
	xhr.onload = function() {
	    if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
	    	element.parentNode.parentNode.getElementsByClassName("imgNb")[0].innerHTML=imageIndex+"."+extension;
	    	element.parentNode.parentNode.getElementsByClassName("imageChargee")[0].src="";
	    	getImages("N"+imageIndex+"."+extension,element.parentNode.parentNode,-1);
	    	setMessageVisible(false);
	    }
	};
	xhr.send(fd);
}
function deleteImageOnServer(element){
	var fileName=element.parentNode.parentNode.getElementsByClassName("imgNb")[0].innerHTML;
	if(fileName!=""){
		showMessage("wait","Suppression de l'image sur le serveur...");
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'rest/questionnaireTools/deleteImage', true);
		xhr.onload = function() {
		    if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
		    	element.parentNode.parentNode.getElementsByClassName("imgNb")[0].innerHTML="";
		    	element.parentNode.parentNode.getElementsByClassName("imageChargee")[0].src="";
		    	element.parentNode.parentNode.getElementsByClassName("delImage")[0].style.visibility="hidden";
		    	setMessageVisible(false);
		    }
		};
		xhr.send(fileName);
	}
}
function getImages(imgName,elementCharge,i){
	if(imgName!=""){
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'rest/questionnaireTools/getImage', true);
		xhr.onload = function() {
		    if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
		    	if(i>=0){
		    		elementCharge[i].getElementsByClassName("imageChargee")[0].src="data:image/png;charset=utf-8;base64, "+xhr.responseText;
		    		elementCharge[i].getElementsByClassName("delImage")[0].style.visibility="visible";
		    	}else{
		    		elementCharge.getElementsByClassName("imageChargee")[0].src="data:image/png;charset=utf-8;base64, "+xhr.responseText;
		    		elementCharge.getElementsByClassName("delImage")[0].style.visibility="visible";
		    	}
	        };    
		};
		xhr.send(imgName);
	}
}
