
function addFile(){
	
	var copie = document.getElementById("copies").getElementsByClassName("fichierCopies")[0];
	var copie2 = copie.cloneNode(true);
	copie2.getElementsByClassName("copiesPDFInput")[0].value = "";
	document.getElementById("copies").appendChild(copie2);
	
}

function delFile(elmnt){
	var element = elmnt.parentNode.parentNode;
	var nbChild = element.childElementCount;
	if (nbChild>2){
		element.removeChild(elmnt.parentNode);
	}
	else {
		elmnt.parentNode.childNodes[1].value="";
	}
}

function addClasse(){
	
	var classe = document.getElementById("classes").getElementsByClassName("choixClasse")[0];
	var classe2 = classe.cloneNode(true);
	document.getElementById("classes").appendChild(classe2);
}

function delClasse(elmnt){
	
	var element = elmnt.parentNode.parentNode;
	var nbChild = element.childElementCount;
	if (nbChild>2){
		element.removeChild(elmnt.parentNode);
	}
	
}

function uploadValide(){
	var file = false;
	var alertText = "";
	var filename = document.getElementById("copiesPDFInput").value;
	
	if (filename != ""){

		var nameList = filename.split(".");
		var extension = nameList[nameList.length-1];
		if (extension=="pdf") {
			file = true;
		}
		else {
			file = false;
			document.getElementById("copiesPDFInput").value = "";
			alertText += "Le fichier fournit n'est pas au bon format. Le fichier doit etre un fichier pdf (.pdf)."; 
		}
	}
	else {
		file = false;
		alertText += "Un fichier contenant les copies des étudiants est necessaire pour la correction." +
				"\nVeuillez selectionner un fichier pdf.";
	}
	
	if (!file){
		alert(alertText);
		return false;
	}
	else {
		return true;
	}
	
}