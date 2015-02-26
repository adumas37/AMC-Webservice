function ouverture(){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/ouverture",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	return true;
}
function correction(){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/correction",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	return true;
}
function suppression(){
	var xhr = new XMLHttpRequest();
	xhr.open("POST","rest/navigation/suppression",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	return true;
}