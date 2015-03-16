/*
 * 
 * 
 * Fonctions pour les notes
 * 
 * 
 */
function chargerNotes(elmnt){
	
	var xhr = new XMLHttpRequest();
	document.getElementById("resultats").style.display="none";
	document.getElementById("tableau").style.display="none";
	xhr.open("POST","rest/correction/notes",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function (aEvt){
		document.getElementById("tableau").style.display="block";
		document.getElementById("tableau").innerHTML=xhr.responseText;
		afficherIndicateurs();
	};
	xhr.send();
	
	

};

function afficherIndicateurs(){
	var notesCont = document.getElementsByClassName("note");
	var indicateurs="";
	var notes = [];
	for (i=1;i<notesCont.length;i++){
		notes[notes.length] = parseFloat(notesCont[i].innerHTML);
	}
	
	var moyenne = 0;
	for (i=0;i<notes.length;i++){
		moyenne += notes[i];
	}
	moyenne /= notes.length;
	indicateurs +="<div>Moyenne: "+moyenne+"</div>";
	
	notes.sort( function(a,b){return a-b;});
	indicateurs += "<div>Note min: "+notes[0]+"</div>";
	indicateurs += "<div>Note max: "+notes[notes.length-1]+"</div>";
	
	var half = Math.floor(notes.length/2);
	var mediane = 0;
	if(notes.length % 2){
		mediane = notes[half];
	} 
	else{
		mediane = (notes[half-1] + notes[half]) / 2.0;
	}
	indicateurs += "<div>Mediane: "+mediane+"</div>";
	
	document.getElementById("indicateurs").innerHTML=indicateurs;
};

function afficherNotes(elmnt){
	var display = document.getElementById("resultats").style.display;
	if(display =="none"){
		document.getElementById("resultats").style.display="block";
	}
	else
	{
		document.getElementById("resultats").style.display="none";
	}
};

/*
 * 
 * 
 * Fonctions pour le barème
 * 
 * 
 */
function changerBareme(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/questionnaireTools/getBareme",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function (aEvt){
		document.getElementById("bareme").innerHTML=xhr.responseText;
		document.getElementById("baremePopup").style.display="block";
		
	};
	xhr.send();
};


function hideBareme(){
	document.getElementById("baremePopup").style.display="none";
};


/*
 * 
 * 
 * Fonctions pour les fichiers
 * 
 * 
 */
var globalElement;
function changerFichiers(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/correction/getFilesNames",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function (aEvt){
		var json = JSON.parse(xhr.responseText);
		document.getElementById("oldFiles").innerHTML="";
		document.getElementById("upload").innerHTML="";
		
		json.forEach(function(text){
			var newNode = document.createElement("p");
			var fileName = document.createTextNode(text);
			var textNode = document.createElement("span");
			textNode.setAttribute("class", "fileName");
			
			var supprButton = document.createElement("input");
			supprButton.setAttribute("type", "button");
			supprButton.setAttribute("value", "Supprimer fichier");
			supprButton.setAttribute("onclick", "delFileExistant(this)");
			textNode.appendChild(fileName);
			newNode.appendChild(textNode);
			newNode.appendChild(supprButton);
			document.getElementById("oldFiles").appendChild(newNode);
		});
		
		var newNode = document.createElement("p");
		newNode.setAttribute("class", "fichierCopies");
		var textNode = document.createTextNode("Copies (.pdf): ");
		
		var inputFile = document.createElement("input");
		inputFile.setAttribute("class", "copiesPDFInput");
		inputFile.setAttribute("name","file");
		inputFile.setAttribute("type", "file");
		inputFile.setAttribute("onchange", "chooseFile()");
		
		var supprButton = document.createElement("input");
		supprButton.setAttribute("type", "button");
		supprButton.setAttribute("value", "Supprimer fichier");
		supprButton.setAttribute("onclick", "delFile(this)");
		
		newNode.appendChild(textNode);
		newNode.appendChild(inputFile);
		newNode.appendChild(supprButton);
		document.getElementById("upload").appendChild(newNode);
		
		document.getElementById("fichiers").style.display="block";
		
	};
	
	xhr.send();
};

function hideFichiers(){
	document.getElementById("fichiers").style.display="none";
};

function delFile(elmnt){
	var element = elmnt.parentNode;
	element.parentNode.removeChild(element);
	chooseFile();
};
function delFileExistant(elmnt){
	
	var filename = elmnt.parentNode.getElementsByTagName("span")[0].innerHTML;
	globalElement=elmnt;
	showMessage("question","Voulez vous vraiment supprimer le fichier <b>"+filename+"</b> ?" +
			"</br>Ce changement est irreversible!","delFileRest(getUploadStatusThenNothing,'"+filename+"')");	
};
function delFileRest(callback,filename){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/correction/supprimerCopie/"+filename,true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.onreadystatechange = function (){
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			var element = globalElement.parentNode;
			element.parentNode.removeChild(element);
			callback(xhr.responseText);
		}
	};
	xhr.send();
};
function chooseFile(){
	var fichiersUploades = document.getElementsByClassName("copiesPDFInput");
	for (var i=0; i< fichiersUploades.length;i++){
		if (fichiersUploades[i].value==""){
			var node = fichiersUploades[i].parentNode;
			node.parentNode.removeChild(node);
		}
	}
	var newNode = document.createElement("p");
	newNode.setAttribute("class", "fichierCopies");
	var textNode = document.createTextNode("Copies (.pdf): ");
	
	var inputFile = document.createElement("input");
	inputFile.setAttribute("class", "copiesPDFInput");
	inputFile.setAttribute("name","file");
	inputFile.setAttribute("type", "file");
	inputFile.setAttribute("onchange", "chooseFile()");
	
	var supprButton = document.createElement("input");
	supprButton.setAttribute("type", "button");
	supprButton.setAttribute("value", "Supprimer fichier");
	supprButton.setAttribute("onclick", "delFile(this)");
	
	newNode.appendChild(textNode);
	newNode.appendChild(inputFile);
	newNode.appendChild(supprButton);
	document.getElementById("upload").appendChild(newNode);
	
};

function verificationFichier(){
	var erreursExtension = 0;
	var sameName = 0;
	var alertText = "";
	var copies = document.getElementsByClassName("copiesPDFInput");
	var names = document.getElementsByClassName("fileName");
	
	for (var i = 0; i < copies.length; i++) {
		var filename = copies[i].value;

		if (filename != ""){
	
			var nameList = filename.split(".");
			var extension = nameList[nameList.length-1];
			if (extension!="pdf") {
				erreursExtension ++;
			}
			for (var j=0;j<names.length;j++){
				name=names[j].innerHTML;
				if (name==filename){
					sameName ++;
				}
			}
			for (var j=0;j<copies.length;j++){
				if (j!=i){
					name=copies[j].value;
					if (name==filename){
						sameName ++;
					}
				}
			}
		}	

	}

	if ((erreursExtension > 0) || (sameName > 0) ){
		if (erreursExtension > 0){
			alertText += "<p>L'un des fichiers fourni n'est pas au bon format et n'a pas été pris en compte. " +
				"Verifiez que l'ensemble des fichiers nécessaires sont présents. " +
				"Les fichiers doivent etre au format pdf (.pdf).</p>";
		}
		if (sameName > 0 ){
			alertText += "<p>Deux fichiers disposent du même nom. " +
				"Supprimez le fichier que vous ne souhaitez pas utiliser pour la correction.</p>";
		}		
		document.getElementById("alertText").innerHTML=alertText;
		document.getElementById("alertText").setAttribute("style", "display:block;");
		console.log("extensions!");
		chooseFile();
		return false;
	}
	else {
		console.log("OK/File");
		document.getElementById("fichiers").setAttribute("style", "display:none;");
		return true;
	}
};




/*
 * 
 * 
 * Fonctions pour les classes
 * 
 * 
 */

function changerClasses(){

	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/correction/getClasses",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function (aEvt){
		document.getElementById("oldClasses").innerHTML="";

		var json = JSON.parse(xhr.responseText);
		console.log(json);
		json.forEach(function(text){
			var newNode = document.createElement("p");
			var fileName = document.createTextNode(text);
			var textNode = document.createElement("span");
			textNode.setAttribute("class", "classeName");
			
			var supprButton = document.createElement("input");
			supprButton.setAttribute("type", "button");
			supprButton.setAttribute("value", "Supprimer classe");
			supprButton.setAttribute("onclick", "delClasseExistante(this)");
			textNode.appendChild(fileName);
			newNode.appendChild(textNode);
			newNode.appendChild(supprButton);
			document.getElementById("oldClasses").appendChild(newNode);
		});
		
		document.getElementById("classes").style.display="block";
		
	};
	xhr.send();
};


function hideClasses(){
	document.getElementById("classes").style.display="none";
};

function delClasse(elmnt){
	if (document.getElementById("upload2").childElementCount >1){
		element = elmnt.parentNode;
		element.parentNode.removeChild(element);
	}
	
};
function delClasseExistante(elmnt){
	var classeName = elmnt.parentNode.getElementsByTagName("span")[0].innerHTML;
	globalElement=elmnt;
	showMessage("question","Voulez vous vraiment supprimer la classe <b>"+classeName+"</b> ?","delClasseRest(getUploadStatusThenNothing,'"+classeName+"');");	
};
function delClasseRest(callback,classeName){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/correction/supprimerClasse/"+classeName,false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.onreadystatechange = function (){
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			var element = globalElement.parentNode;
			element.parentNode.removeChild(element);
			callback(xhr.responseText);
		}
	};
	xhr.send();
};
function ajouterClasse(){
	var node = document.getElementsByClassName("choixClasse")[0];
	var newNode = node.cloneNode(true);
	
	document.getElementById("upload2").appendChild(newNode);	
};

function verificationClasses(){
	var classes = document.getElementById("upload2").getElementsByClassName("classeInput");
	for (var i = 0; i < classes.length; i++) {
		var classe = classes[i].value;
		for (var j=0;j<classes.length;j++){
			if (j!=i){
				if (classe==classes[j].value){
					showMessage("error", "Vous voulez ajouter plusieurs fois une même classe !");
					return false;
				}
			}
		}	
	}
	var oldClasses=document.getElementById("oldClasses").getElementsByClassName("classeName");
	for (var i = 0; i < classes.length; i++) {
		for (var j=0;j<oldClasses.length;j++){
			if (oldClasses[j].innerHTML==classes[i].value){
				showMessage("error", "Vous voulez ajouter une classe qui existe déjà !");
				return false;
			}
		}
	}
	return true;
};
function uploadClasses(callback){
	if(verificationClasses()){
		var form=document.getElementById("uploadClasses");
		var formData = new FormData(form);
		var xhr = new XMLHttpRequest();
		xhr.open("POST","rest/correction/ajouterClasses",true);
		xhr.onreadystatechange = function (){
			if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0) ) {
				callback(xhr.responseText);
			}
		};
		xhr.send(formData);
	}
};
function uploadCopies(callback){
	if(verificationFichier()){
		var form=document.getElementById("uploadCopies");
		var formData = new FormData(form);
		var xhr = new XMLHttpRequest();
		xhr.open("POST","rest/correction/ajouterCopies",true);
		xhr.onreadystatechange = function (){
			if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0) ) {
				callback(xhr.responseText);
			}
		};
		showMessage("wait","Upload des copies en cours...");
		xhr.send(formData);
	}
};
function lancerCorrection(callback){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/correction/LancerCorrection",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.onreadystatechange = function (){
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0) ) {
			callback(xhr.responseText);
		}
	};
	showMessage("wait","Correction en cours...");
	xhr.send(null);
};
function getCorrectionStatus(code){
	if(code=='1'){
		 document.location.href="Correction.php";
	}else{
		showMessage("error",code);
	}
};
function getUploadStatusThenCorrect(code){
	if(code=='1'){
		//L'upload s'est bien passé on lance la correction
		lancerCorrection(getCorrectionStatus);
	}else{
		showMessage("error",code);
	}
};
function getUploadStatusThenReload(code){
	if(code=='1'){
		//L'upload s'est bien passé on recharge la page
		location.reload();
	}else{
		showMessage("error",code);
	}
};
function getUploadStatusThenNothing(code){
	if(code=='1'){
		//L'upload s'est bien passé on ne fait rien
	}else{
		showMessage("error",code);
	}
};
