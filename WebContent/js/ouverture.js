
function afficherDossiers(elmnt){
	
	var action="";
	var xhr2 = new XMLHttpRequest();
	xhr2.open("POST","rest/ouvertureProjet",true);
	xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr2.onreadystatechange = function() {
		if (xhr2.readyState == 4 && (xhr2.status == 200 || xhr2.status == 0) ) {
			var directories = xhr2.responseText.split("/");
			directories.sort();
			console.log(action);
			if (xhr2.responseText != ""){
				directories.forEach( function(directory){
					var linkNode = document.createElement("a");
					if (action == "Suppression"){
						linkNode.setAttribute("href","");
						linkNode.setAttribute("onclick","return delProject(\""+directory+"\");");
					}
					else if (action == "correction"){
						linkNode.setAttribute("href","rest/navigation/correctionLien/" + directory);
						linkNode.setAttribute("onclick","return setProject(\""+directory+"\");");
					}
					else {
						linkNode.setAttribute("href",action);
						linkNode.setAttribute("onclick","return setProject(\""+directory+"\");");
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
		}
	};
	
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/navigation/getAction",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			
			if (xhr.responseText == "correction"){
				action = "correction";
			}
			else if (xhr.responseText == "suppression"){
				action = "Suppression";
				document.getElementById("action").innerHTML = "Suppression de projet";
			}
			else {
				action = "Projet.html";
			}

			xhr2.send(null);
			
		}
	};
	xhr.send(null);

};

function setProject(directory){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/setProject",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(directory);
	console.log(directory);
	return true;
};

function delProject(directory){
	
	if (confirm("Voulez vous vraiment supprimer le projet "+directory+"?\nCe changement est irreversible!") == true) {
		var xhr = new XMLHttpRequest();
		xhr.open("POST","rest/suppressionProjet",false);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr.send(directory);
    } 
	else {
		return false;
	}

	return true;
};
