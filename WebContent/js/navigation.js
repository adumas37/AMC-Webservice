function ouverture(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/navigation/ouverture",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	return true;
}
function correction(){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/navigation/correction",false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
	return true;
}