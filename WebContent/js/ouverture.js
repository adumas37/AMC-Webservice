
function afficherDossiers(elmnt){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/navigation/getAction",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	
	var action;
	if (xhr.responseText == "correction"){
		action = "UploadCopies.html";
	}
	else if (xhr.responseText == "suppression"){
		action = "Suppression";
		document.getElementById("action").innerHTML = "Suppression de projet";
	}
	else {
		action = "Projet.html";
	}
	
	var xhr2 = new XMLHttpRequest();
	xhr2.open("POST","rest/ouvertureProjet",true);
	xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr2.send();

	console.log(xhr2.responseText);
	var directories = xhr2.responseText.split("/");
	directories.sort();
	if (xhr2.responseText != ""){
		directories.forEach( function(directory){
			var linkNode = document.createElement("a");
			if (action != "Suppression"){
				linkNode.setAttribute("href",action);
				linkNode.setAttribute("onclick","return setProject(\""+directory+"\");");
			}
			else {
				linkNode.setAttribute("href","");
				linkNode.setAttribute("onclick","return delProject(\""+directory+"\");");
			}
			var newNode = document.createElement("div");
			newNode.className="directory";
			var img = document.createElement("img");
			img.src="src/directory.png";
			img.alt="directory: ";
			img.className="directory";
			newNode.appendChild(img);
			var text = document.createTextNode(directory);
			newNode.appendChild(text);
			linkNode.appendChild(newNode);
			document.getElementById("explorer").appendChild(linkNode);
		});
	}
	else {
		var textNode = document.createTextNode("Aucun projet en cours.");
		document.getElementById("explorer").appendChild(textNode);
	}
};

function setProject(directory){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/setProject",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(directory);
	return true;
};

function delProject(directory){
	
	if (confirm("Voulez vous vraiment supprimer le projet "+directory+"?\nCe changement est irreversible!") == true) {
		var xhr = new XMLHttpRequest();
		xhr.open("POST","rest/suppressionProjet",true);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr.send(directory);
    } 
	else {
		return false;
	}

	return true;
};