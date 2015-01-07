
function afficherDossiers(elmnt){
	console.log("BLABLA");
	
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/ouvertureProjet",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	console.log(xhr.responseText);
	console.log("CASSER DES CHEVILLES");
};