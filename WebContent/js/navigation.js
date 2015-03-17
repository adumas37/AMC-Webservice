function setCurrentUser(username){
	var xhr = new XMLHttpRequest();
	console.log("test");
	xhr.open("POST","rest/utilisateurs/currentUser",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(username);
}

function ouverture(username){
	setCurrentUser(username);
	var xhr2 = new XMLHttpRequest();
	console.log("test2");
	xhr2.open("POST","rest/navigation/ouverture",false);
	xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr2.send(null);
	return true;
}
function correction(username){
	setCurrentUser(username);
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/correction",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
	return true;
}
function suppression(username){
	setCurrentUser(username);
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/suppression",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
	return true;
}