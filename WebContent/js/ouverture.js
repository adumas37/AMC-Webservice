
function afficherDossiers(elmnt){	
	
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/ouvertureProjet",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();

	var directories = xhr.responseText.split("/");
	directories.sort();
	
	directories.forEach( function(directory){
		var newNode = document.createElement("div");
		var text = document.createTextNode(directory);
		newNode.appendChild(text);
		document.getElementById("explorer").appendChild(newNode);
	});
};