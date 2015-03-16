

function getProject(callback){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			callback(xhr.responseText);
		}
	};
	xhr.open("GET","rest/projet/nomProjet",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);

};
function readProject(data){
	if (data!=""){
		document.getElementById("nomProjet").innerHTML = data;
		if(document.getElementById("nomProjetInput")!=null){
			document.getElementById("nomProjetInput").value = data;
		}
	}
};

getProject(readProject);
	
function upload(){
	document.getElementById("newTex").setAttribute("style", "visibility:visible;");
}

function hide(){
	document.getElementById("newTex").setAttribute("style", "visibility:hidden;");
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
		alertText = "Erreur lors de l'import du nom de projet.</br>";
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
			alertText += "Le fichier fournit n'est pas au bon format. Le fichier doit etre un fichier Latex (.tex).</br>"; 
		}
	}
	else {
		alertText += "Vous devez specifier un fichier.</br>";
		file = false;
	}
		
	if (name == false || file == false){
		showMessage("error",alertText);
		return false;
	}
	else {
		return true;
	}
}

function eraseFile(){
	document.getElementById("fichierTexInput").value = "";
}