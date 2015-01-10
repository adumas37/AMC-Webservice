
function afficherNotes(elmnt){
	
	var xhr = new XMLHttpRequest();
	document.getElementById("resultats").style.display="none";
	xhr.open("POST","rest/correction/notes",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function (aEvt){
		document.getElementById("loading").style.display="none";
		document.getElementById("resultats").style.display="block";
		document.getElementById("resultats").innerHTML=xhr.responseText;
		
	};
	xhr.send();
	
	

};