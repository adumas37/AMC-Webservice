
function addFile(){
	
}

function delFile(elmnt){
	var element = elmnt.parentNode.parentNode;
	var nbChild = element.childElementCount;
	if (nbChild>2){
		element.removeChild(elmnt.parentNode);
		console.log("1");
	}
	else {
		elmnt.parentNode.childNodes[1].value="";
		console.log("2");
	}
}

function addClasse(){
	
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