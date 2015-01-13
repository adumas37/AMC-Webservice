
var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/projet/nomProjet",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	if (xhr.responseText!=""){
		document.getElementById("nomProjet").innerHTML = xhr.responseText;
	}