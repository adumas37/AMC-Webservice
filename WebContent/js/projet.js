

function getProjet(callback){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			callback(xhr.responseText);
		}
	};
	xhr.open("GET","rest/projet/nomProjet",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);

}
function readProject(data){
	if (data!=""){
		document.getElementById("nomProjet").innerHTML = data;
		document.getElementById("nomProjetInput").value = data;
	}
}
getProject(readProject);
	
function upload(){
	document.getElementById("newTex").setAttribute("style", "visibility:visible;");
}

function hide(){
	document.getElementById("newTex").setAttribute("style", "visibility:hidden;");
	document.getElementById("alertText").innerHTML="";
	document.getElementById("alertText").setAttribute("style", "display:none;");
}

function creationValide(){
	var name = false;
	var file = false;
	var projectName = document.getElementById("nomProjetInput").value;
	var filename = document.getElementById("fichierTexInput").value;
	var alertText = "";

	if ( projectName != "" ){ 
		name = true;
	}
	else {
		alertText = "Erreur lors de l'import du nom de projet.";
	}
	
	if (filename != ""){
		var nameList = filename.split(".");
		var extension = nameList[nameList.length-1];
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
		if (alertText != ""){ alertText += "\n"; }
		alertText += "Vous devez specifier un fichier.";
		file = false;
	}
		
	if (name == false || file == false){
		document.getElementById("alertText").innerHTML=alertText;
		document.getElementById("alertText").setAttribute("style", "");
		return false;
	}
	else {
		return true;
	}
}

function eraseFile(){
	document.getElementById("fichierTexInput").value = "";
	document.getElementById("alertText").innerHTML="";
	document.getElementById("alertText").setAttribute("style", "display:none;");
}