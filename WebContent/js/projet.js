

function getProject(callback){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			callback(xhr.responseText);
		}
	};
	xhr.open("GET","rest/projet/nomProjet",true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(null);

};
function readProject(data){
	if (data!=""){
		document.getElementById("nomProjet").innerHTML = data;
		if(document.getElementById("nomProjetInput")!=null){
			document.getElementById("nomProjetInput").value = data;
		}
	}
};

getProject(readProject);
	

