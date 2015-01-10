
function afficherDossiers(elmnt){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/navigation/getAction",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	
	var action;
	if (xhr.responseText == "correction"){
		action = "Correction.html";
	}
	else {
		action = "Projet.html";
	}
	
	var xhr2 = new XMLHttpRequest();
	xhr2.open("POST","rest/ouvertureProjet",false);
	xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr2.send();

	var directories = xhr2.responseText.split("/");
	directories.sort();
	
	directories.forEach( function(directory){
		var linkNode = document.createElement("a");
		linkNode.setAttribute("href",action);
		linkNode.setAttribute("onclick","return setProject(\""+directory+"\");");
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
};

function setProject(directory){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/setProject",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(directory);
	return true;
};