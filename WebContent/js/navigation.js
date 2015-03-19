function ouverture(){
	var xhr2 = new XMLHttpRequest();
	console.log("test2");
	xhr2.open("POST","rest/navigation/ouverture",false);
	xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr2.send(null);
	return true;
}
function correction(){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/correction",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
	return true;
}
function suppression(){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/suppression",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
	return true;
}