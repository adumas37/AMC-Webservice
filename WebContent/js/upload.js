
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
	var file = 0;
	var erreursExtension = 0;
	var alertText = "";
	var copies = document.getElementsByClassName("copiesPDFInput");
	
	for (var i = 0; i < copies.length; i++) {
		var filename = copies[i].value;

		if (filename != ""){
	
			var nameList = filename.split(".");
			var extension = nameList[nameList.length-1];
			if (extension=="pdf") {
				file ++;
			}
			else {
				erreursExtension ++;
				if (copies[i].parentNode.parentNode.childElementCount>2){
					copies[i].parentNode.remove();
					i--;
				}
				else {
					copies[i].value = "";
				}
			}
		}	
		else {
			if (copies[i].parentNode.parentNode.childElementCount>2){
				copies[i].parentNode.remove();
				i--;
			}
		}
	}

	if ( (file == 0) || (erreursExtension > 0) ){
		if (file == 0){
			showMessage("error","Au moins un fichier contenant les copies des étudiants est necessaire pour la correction.</br>" +
					"<i>Veuillez selectionner un fichier pdf.</i>");
		}
		if (erreursExtension > 0 ){
			showMessage("error","L'un des fichiers fourni n'est pas au bon format et n'a pas été pris en compte.</br>" +
					"<i>Verifiez que l'ensemble des fichiers nécessaires sont présents.</br>" +
					"Les fichiers doivent etre au format pdf (.pdf).</i>");
		}
		return false;
	}
	else {
		return true;
	}
	
};

function uploadFichiers(callback){
	if(uploadValide()){
		var form=document.getElementById("uploadCopies");
		var formData = new FormData(form);
		var xhr = new XMLHttpRequest();
		xhr.open("POST","rest/uploadCopies",true);
		xhr.onreadystatechange = function (){
			if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0) ) {
				callback(xhr.responseText);
			}
		};
		showMessage("wait","Upload des copies en cours...");
		xhr.send(formData);
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