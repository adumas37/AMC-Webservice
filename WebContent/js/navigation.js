function ouverture(){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/ouverture",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);
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