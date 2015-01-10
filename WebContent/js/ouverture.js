
function afficherDossiers(elmnt){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/getAction",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	
	var xhr2 = new XMLHttpRequest();
	xhr2.open("POST","rest/ouvertureProjet",false);
	xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr2.send();

	var directories = xhr2.responseText.split("/");
	directories.sort();
	
	directories.forEach( function(directory){
		var newNode = document.createElement("div");
		newNode.className="directory";
		var img = document.createElement("img");
		img.src="src/directory.png";
		img.alt="directory: ";
		img.className="directory";
		newNode.appendChild(img);
		var text = document.createTextNode(directory);
		newNode.appendChild(text);
		document.getElementById("explorer").appendChild(newNode);
	});
};