
function chargerNotes(elmnt){
	
	var xhr = new XMLHttpRequest();
	document.getElementById("resultats").style.display="none";
	document.getElementById("tableau").style.display="none";
	xhr.open("POST","rest/correction/notes",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function (aEvt){
		document.getElementById("tableau").style.display="block";
		document.getElementById("tableau").innerHTML=xhr.responseText;
		
	};
	xhr.send();
	
	

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

function changerBareme(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/creationQuestionnaire/getBareme",false);
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

function changerFichiers(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/correction/getFilesNames",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function (aEvt){
		var json = JSON.parse(xhr.responseText);
		document.getElementById("gestionFichiers").innerHTML="";
		json.forEach(function(text){
			var newNode = document.createElement("p");
			var fileName = document.createTextNode(text);
			newNode.appendChild(fileName);
			document.getElementById("gestionFichiers").appendChild(newNode);
		});
		
		document.getElementById("fichiers").style.display="block";
		
	};
	xhr.send();
};

function hideFichiers(){
	document.getElementById("fichiers").style.display="none";
};
