
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


