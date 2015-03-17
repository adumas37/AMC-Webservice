function ajoutReponse(elmnt){

	// To change input id for latex formula : Which input is created, tag it to target it for Latex Formula Button
	var stringNameReponse = elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].getElementsByClassName("reponse")[0].getElementsByClassName("reponseInput")[0].id;
	var reponseNb = elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].childElementCount+1;
	stringNameReponse = stringNameReponse.substring(0, stringNameReponse.length-1);
	stringNameReponse += reponseNb;

	// Change input for image upload
	var stringNameReponsePreview = elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].getElementsByClassName("imgUpload")[0].id;
	var reponseNb = elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].childElementCount+1;
	stringNameReponsePreview = stringNameReponsePreview.substring(stringNameReponsePreview.length-3, stringNameReponsePreview.length-1);
	stringNameReponsePreview = "R"+ stringNameReponsePreview + reponseNb;

	//console.log(stringNameReponsePreview);
	
	var reponse = document.getElementsByClassName("reponse")[0];
	var reponse2 = reponse.cloneNode(true);
	reponse2.getElementsByClassName("reponseInput")[0].value="";
	reponse2.getElementsByTagName("span")[0].getElementsByTagName("input")[0].checked=false;

	// Change input id for latex formula (input id and href for latex formula button)
	reponse2.getElementsByClassName("reponseInput")[0].id = stringNameReponse;
	reponse2.getElementsByClassName("linkLatex")[0].href="javascript:OpenLatexEditor('"+stringNameReponse+"','latex','fr-fr')";

	elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].appendChild(reponse2);

	//console.log(reponse2.getElementsByClassName("openImgUpload")[0]);

	var reponse3 = reponse.parentNode.getElementsByClassName("imgUpload")[0];

	var reponse4 = reponse3.cloneNode(true);

	// Change input for image upload
	reponse2.getElementsByClassName("openImgUpload")[0].onclick= function() { uploadImg('newImg'+ stringNameReponsePreview);return false } ;
	reponse4.setAttribute("style", "display: none;");
	reponse4.id = "newImg"+ stringNameReponsePreview;
	reponse4.getElementsByClassName("previewcanvas")[0].id = "previewcanvas"+ stringNameReponsePreview;
	reponse4.getElementsByClassName("uploadfileselection")[0].id = "uploadfileselection"+ stringNameReponsePreview;
	reponse4.getElementsByClassName("uploadfileselection")[0].onchange = function() { return ShowImagePreview( this.files, stringNameReponsePreview ); } ;
	reponse4.getElementsByClassName("previewclearbutton")[0].onclick = function() { ClearImagePreview( stringNameReponsePreview ); return false; } ;

	elmnt.parentNode.parentNode.parentNode.getElementsByTagName("reponses")[0].appendChild(reponse4);

};

function ajoutQuestion(elmnt){

	// To change input id for latex formula : Which input is created, tag it to target it for Latex Formula Button
	var questionNb = document.getElementById("questionnaire").childElementCount;
	var stringNameReponse = "reponse "+questionNb+",1";
	var stringNameReponsePreview = "R"+questionNb+",1";

	var question1 = document.getElementsByClassName("blocQR")[0];
	var question = question1.cloneNode(true);

	question.getElementsByTagName("input")[0].value="";
	var reponses = question.getElementsByTagName("reponses")[0];
	if (reponses.childElementCount > 1 ) {
		while (reponses.childNodes.length >=2){ // To check
			reponses.removeChild(reponses.firstChild);
		}
	}	
	reponses.getElementsByClassName("reponseInput")[0].value="";
	reponses.getElementsByTagName("input")[0].checked=true;

	question.getElementsByClassName("questionInput")[0].id="question"+questionNb;
	question.getElementsByClassName("linkLatex")[0].href="javascript:OpenLatexEditor('question"+questionNb+"','latex','fr-fr')";
	reponses.getElementsByClassName("reponseInput")[0].id = stringNameReponse;
	reponses.getElementsByClassName("linkLatex")[0].href="javascript:OpenLatexEditor('"+stringNameReponse+"','latex','fr-fr')";

	//Canvas for image upload stuff
	//Question part
	question.getElementsByClassName("imgUpload")[0].id="newImgQ"+questionNb;
	question.getElementsByClassName("openImgUpload")[0].onclick= function() { uploadImg('newImgQ'+questionNb);return false } ;
	question.getElementsByClassName("previewcanvas")[0].id="previewcanvasQ"+questionNb;
	question.getElementsByClassName("uploadfileselection")[0].onchange= function() { return ShowImagePreview( this.files, 'Q'+ questionNb); } ;
	question.getElementsByClassName("previewclearbutton")[0].onclick= function() { ClearImagePreview( 'Q'+ questionNb ); return false; } ;
	reponses.getElementsByClassName("openImgUpload")[0].onclick=function() { uploadImg('newImg' + stringNameReponsePreview );return false } ;

	//Answer part
	reponses.getElementsByClassName("imgUpload")[0].id="newImg"+stringNameReponsePreview;
	reponses.getElementsByClassName("previewcanvas")[0].id="previewcanvas"+stringNameReponsePreview;
	reponses.getElementsByClassName("uploadfileselection")[0].id="uploadfileselection"+stringNameReponsePreview;
	reponses.getElementsByClassName("uploadfileselection")[0].onchange=function() { return ShowImagePreview( this.files, "newImg"+stringNameReponsePreview); };
	reponses.getElementsByClassName("previewclearbutton")[0].onclick= function() { ClearImagePreview( "newImg"+stringNameReponsePreview ); return false; } ;

	document.getElementById("questionnaire").appendChild(question);

};

function supprReponse(elmnt){
	
	var element = elmnt.parentNode.parentNode;
	var nbChild = elmnt.parentNode.parentNode.parentNode.childElementCount;
	if (nbChild>1){
		element.parentNode.removeChild(element);
	}
	
};

function supprQuestion(elmnt){
	
	var element = elmnt.parentNode.parentNode.parentNode;
	var nbChild = elmnt.parentNode.parentNode.parentNode.parentNode.childElementCount;
	if (nbChild>2){
		element.parentNode.removeChild(element);
	}
	
};

function demanderQuestionnaire(callback){
	var xhr = new XMLHttpRequest();
	xhr.open("GET","rest/questionnaireTools/modification",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			var json = JSON.parse(xhr.responseText);
			if(json!=null){
				callback(json);
			}
		}
	};
	xhr.send(null);
};
function chargerQuestionnaire(json){
	
	var html='';
	html+='<p id="entete"> \
		<span id="matiere">Matiere:<input id="matiereInput" name="matiere" type="text" class="inputText inputButton" value="'+json.matiere+'"/></span> \
		<span id="duree">Durée:<select id="dureeInput" name="duree" class="inputText inputButton">\
			<option value="0h10">0h10</option> \
			<option value="0h15">0h15</option>\
			<option value="0h20">0h20</option>\
			<option value="0h25">0h25</option>\
			<option value="0h30">0h30</option>\
			<option value="0h35">0h35</option>\
			<option value="0h40">0h40</option>\
			<option value="0h45">0h45</option>\
			<option value="0h50">0h50</option>\
			<option value="0h55">0h55</option>\
			<option value="1h00">1h00</option>\
			<option value="1h05">1h05</option>\
			<option value="1h10">1h10</option>\
			<option value="1h15">1h15</option>\
			<option value="1h20">1h20</option>\
			<option value="1h25">1h25</option>\
			<option value="1h30">1h30</option>\
			<option value="1h35">1h35</option>\
			<option value="1h40">1h40</option>\
			<option value="1h45">1h45</option>\
			<option value="1h50">1h50</option>\
			<option value="1h55">1h55</option>\
			<option value="2h00">2h00</option>\
		</select></span>\
		<span id="date">Date (jj/mm/aaaa):<input id="dateInput" name="date" type="text" class="inputText inputButton" value="'+json.date+'"/></span> \
		<span id="nbCopies">Nombre d\'exemplaires de copies:<input id="nbCopiesImput" name="nbCopies" type="number" min="1" max="10" value="'+json.nbCopies+'" class="inputText inputButton"/></span> \
		</p>';
	for(i=0;i<json.questions.length;i++){
		html += '<blocQR class="blocQR"> \
		<div class="question"> \
		Question: <input type="text" id="question'+i+'" name="question" class="questionInput inputText inputButton" value="'+json.questions[i].texte+'"/> \
		<a class="linkLatex" href="javascript:OpenLatexEditor(\'question'+i+'\',\'latex\',\'fr-fr\')"><img src="src/formula_icon.png"></a> \
		<a href="" class="openImgUpload" onclick="uploadImg(\'newImgQ1\');return false"><img class="linkPicture" src="src/img.png"></a> \
		<div id="newImgQ1" class="imgUpload" style="display: none;"> \
			<span class="imgNb"></span> \
			<div class="previewcanvascontainer" style="width: 282px;"> \
                <canvas id="previewcanvasQ1" class="previewcanvas" style="width: 282px;"> \
                </canvas> \
            </div> \
            <input type="file" id="uploadfileselectionQ1" class="uploadfileselection" onchange="uploadImageOnServer(this);ShowImagePreview( this.files,\'Q1\');" /> \
		    <input class="previewclearbutton" type="button" onclick="deleteImageOnServer(this);ClearImagePreview(\'Q1\');return false;" value="Effacer"/> \
		</div></div><reponses>';
		for(j=0;j<json.questions[i].reponses.length;j++){
			html += '<p class="reponse"> \
			Reponse: <input type="text" id="reponse '+i+','+j+'" name="reponse" class="reponseInput inputText inputButton" value="'+json.questions[i].reponses[j].texte+'"/> \
			<a class="linkLatex" href="javascript:OpenLatexEditor(\'reponse '+i+','+j+'\',\'latex\',\'fr-fr\')"><img src="src/formula_icon.png"></a> \
			<span class="checkbox">Bonne reponse?<input class="bonneInput" type="checkbox" name="bonne"';
			if(json.questions[i].reponses[j].correcte){html+=' checked="true"';}
			html+='"/></span> \
			<span class="delQ"><input type="button" name="delQ" value="Supprimer reponse" onclick="supprReponse(this)" class="inputButton blueButton"/></span> \
			</p>';
		}
		html += '</reponses> \
		<options> \
		<span class="del"><input type="button" name="delQ" value="Supprimer question" onclick="supprQuestion(this)" class="inputButton blueButton"/></span> \
		<span class="addQ"><input type="button" name="addQ" value="Ajouter reponse" onclick="ajoutReponse(this)"  class="inputButton blueButton"/></span> \
		<span class="checkbox">Reponses horizontales?<input type="checkbox" name="horizontal"'+ (json.questions[i].colonnes?" checked":" ")+'/></span> \
		<span class="bareme">bareme:<input class="baremeImput inputText" name="bareme" type="number" min="1" max="20" value="'+json.questions[i].bareme+'"/></span> \
		</options> \
		</blocQR>';
		
	}
	
	document.getElementById("questionnaire").innerHTML = html;
	document.getElementById("dureeInput").value=json.duree;
};

function questionnaireValide(){

	var reponseSansTexte = 0;
	var questionSansTexte = 0;
	var questionSansBonneReponse = 0;
	var matiere = false;
	var date = false;
	
	var blocsQR = document.getElementsByClassName("blocQR");
	var reponses;
	var bonnesReponses;
	var nbBonnesReponses;
	
	if (document.getElementById("matiereInput").value != ""){
		matiere = true;
	}
	if (document.getElementById("dateInput").value != ""){
		var date2 = document.getElementById("dateInput").value;
	    var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
	    if (!pattern.test(date2))
	    {
	    	date = false;
	    }
	    else{
	    	date = true;
	    }
	}
	
	
	for (var i = 0; i < blocsQR.length; i++) {
		if (blocsQR[i].getElementsByClassName("questionInput")[0].value == ""){ 
			questionSansTexte ++; 
		}
	
		reponses = blocsQR[i].getElementsByClassName("reponseInput");
		for (var j = 0; j < reponses.length; j++) {
			if (reponses[j].value == ""){ 
				reponseSansTexte ++;
			}
		}
		
		bonnesReponses = blocsQR[i].getElementsByClassName("bonneInput");
		nbBonnesReponses = 0;
		for (var j = 0; j < bonnesReponses.length; j++) {
			if (bonnesReponses[j].checked == true) { 
				nbBonnesReponses ++; 
			}
		}
		if (nbBonnesReponses == 0){ 
			questionSansBonneReponse ++;
		}
		
	}
	
	if (reponseSansTexte >0 || questionSansTexte >0 || questionSansBonneReponse > 0 || !matiere || !date){
		var errorText = "";
		if (!matiere){
			errorText += "Aucune matière specifiée !</br><i>Une matière doit etre donnée pour continuer.</i></br>"; 
		}
		if (!date){
			errorText += "Date non spécifiée ou incorrecte !</br><i>Le format correct est jj/mm/aaaa.</i></br>"; 
		}
		if(reponseSansTexte >0){ 
			errorText += "Il y a des réponses sans texte !</br><i>Supprimez les réponses vides ou remplissez les.</i></br>"; 
		}
		if(questionSansTexte >0){ 
			errorText += "Il y a des questions sans texte !</br><i>Supprimez les questions vides ou remplissez les.</i></br>"; 
		}
		if(questionSansBonneReponse >0){ 
			errorText += "Il y a des questions sans bonnes réponses !</br><i>Choisissez au moins une bonne réponse par question.</i></br>"; 
		}
		showMessage("error",errorText);
		
	}
	else {
		var jsonData ='{"matiere":"'+document.getElementById("matiereInput").value+'", \
			"date":"'+document.getElementById("dateInput").value+'", \
			"duree":"'+document.getElementById("dureeInput").value+'", \
			"nbCopies":"'+document.getElementById("nbCopiesImput").value+'", \
			"questions":[';

		for(i=0;i<blocsQR.length;i++){
			if(i>0){
				jsonData+=',';
			}
				var imgName="";
				if(typeof blocsQR[i].getElementsByClassName("question")[0].getElementsByClassName("imgNb")[0]!= "undefined"){
					imgName=blocsQR[i].getElementsByClassName("question")[0].getElementsByClassName("imgNb")[0].innerHTML;
				}
				jsonData+='{"image":"'+imgName+'","texte":"'+blocsQR[i].getElementsByClassName("question")[0].getElementsByClassName("questionInput")[0].value.replace(/\\/g,"\\\\")+'", \
				"bareme":"2","reponses": \
			            	  	[';
				for(j=0;j<blocsQR[i].getElementsByClassName("reponse").length;j++){
					if(j>0){
						jsonData+=',';
					}
					jsonData+='{"texte":"'+blocsQR[i].getElementsByClassName("reponse")[j].getElementsByClassName("reponseInput")[0].value.replace(/\\/g,"\\\\")+'", \
					"correcte":"'+blocsQR[i].getElementsByClassName("reponse")[j].getElementsByClassName("bonneInput")[0].checked+'"}';
				}
				jsonData+=']}';
		}
		jsonData+=']}';
		showMessage("wait","Création du questionnaire...");
		sendQuestionnaire(stateQuestionnaireCompilation,jsonData);
		
	}
	return false;

};

function sendQuestionnaire(callback, data){
	var jsonobj=JSON.parse(data);
	var count = Object.keys(jsonobj).length;

	var xhr = new XMLHttpRequest();
	var url = "rest/questionnaireTools/creation";
	xhr.open("POST", url, true);
	//On envoie l'objet JSON avec xmlhttprequest
	xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
	
	//Afficher les messages d'erreur de compilation ?
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0) ) {
			callback(xhr.responseText);
		}
	}
	xhr.send(data);
	
	

};
function stateQuestionnaireCompilation(code){
	if(code=='1'){
		 document.location.href="Projet.html";
	}else{
		showMessage("error",code);
	}
};
function creationValide(){
	var name = false;
	var file = false;
	var projectExists = false;
	var projectName = document.getElementById("nomProjetInput").value;
	var filename = document.getElementById("fichierTexInput").value;

	if ( projectName != "" ){ 
		name = true;
	}
	else {
		showMessage("error","Aucun nom de projet spécifié.</br><i>Veuillez donner un nom au projet !</i>");
	}
	
	if (filename != ""){
		var nameList = filename.split(".");
		var extension = nameList[nameList.length-1];
		if (extension=="tex") {
			file = true;
		}
		else {
			file = false;
			document.getElementById("fichierTexInput").value = "";
			showMessage("error","Le fichier fourni n'est pas au bon format.</br><i>Le fichier doit etre un fichier Latex (.tex) !</i>");
		}
	}
	else {			
		file = true;
	}
	
	if (name == false || file == false){
		return;
	}
	else{
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST","rest/ouvertureProjet",false);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
				var projects=xhr.responseText.split("/");
				projects.forEach( function testExistingProject(project){
					if (project == projectName){ 
						projectExists = true;
					}
				});
				
				if (projectExists){
					if (filename != ""){
						showMessage("question","Un projet nommé <b>"+projectName+"</b> existe déjà !"+
								"</br><i>Si vous souhaitez remplacer le questionnaire du projet par celui " +
								"que vous venez de choisir, cliquez sur OK." +
								"</br>Pour annuler et conserver l'ancien projet ou changer de nom, cliquez sur Annuler.</i>","document.forms.formulaireProjet.submit()");
					}
					else {
						showMessage("question","Un projet nommé <b>"+projectName+"</b> existe déjà !"+
								"</br><i>Si vous souhaitez editer le questionnaire du projet, cliquez sur OK." +
								"</br>Pour annuler et choisir un nouveau nom de projet, cliquez sur Annuler.</i>","document.forms.formulaireProjet.submit()");
					}
				}else{
					document.forms.formulaireProjet.submit();
				}
			}
		};
		
		xhr.send(projectName);
		
	}
	
	
	
};

function eraseFile(){
	document.getElementById("fichierTexInput").value = "";
};

function uploadImg(balise){
	if (document.getElementById(balise).style.display=="none"){
		document.getElementById(balise).setAttribute("style", "display: block;");
	}
	else
	{
		document.getElementById(balise).setAttribute("style", "display: none;");
	}
}

function ShowImagePreview( files, selection )
{
    if( !( window.File && window.FileReader && window.FileList && window.Blob ) )
    {
      alert('The File APIs are not fully supported in this browser.');
      return false;
    }

    if( typeof FileReader === "undefined" )
    {
        alert( "Filereader undefined!" );
        return false;
    }

    var file = files[0];

    if( !( /image/i ).test( file.type ) )
    {
        alert( "File is not an image." );
        return false;
    }

    reader = new FileReader();
    reader.onload = function(event) { var img = new Image;
                img.onload = function (){
                    UpdatePreviewCanvas(img, selection);
                }
                img.src = event.target.result;  
            };
    reader.readAsDataURL( file );
}

function UpdatePreviewCanvas(image, selection)
{
    var img = image;

    var canvas = document.getElementById( "previewcanvas" + selection );

    if( typeof canvas === "undefined" 
        || typeof canvas.getContext === "undefined" )
        return;

    var context = canvas.getContext( '2d' );

    var world = new Object();
    world.width = canvas.offsetWidth;
    world.height = canvas.offsetHeight;

    canvas.width = world.width;
    canvas.height = world.height;

    if( typeof img === "undefined" )
        return;

    var WidthDif =  - world.width;
    var HeightDif = img.height - world.height;

    var Scale = 0.0;
    if( WidthDif > HeightDif )
    {
        Scale = world.width / img.width;
    }
    else
    {
        Scale = world.height / img.height;
    }
    if( Scale > 1 )
        Scale = 1;

    var UseWidth = Math.floor( img.width * Scale );
    var UseHeight = Math.floor( img.height * Scale );

    var x = Math.floor( ( world.width - UseWidth ) / 2 );
    var y = Math.floor( ( world.height - UseHeight ) / 2 );

    context.drawImage( img, x, y, UseWidth, UseHeight );  
};

function ClearImagePreview( selection )
{
    var UploadForm = document.getElementById( "uploadfileselection" + selection);
    if( UploadForm !== undefined && UploadForm != null )
    {
        document.getElementById("uploadfileselection" + selection).value = "";
        
        var canvas = document.getElementById( "previewcanvas" + selection );
            
        if( canvas === "undefined" )
            return;

        var context = canvas.getContext( '2d' );

        context.clearRect( 0, 0, canvas.width, canvas.height );     
    }
};
var imageIndex=0;

function uploadImageOnServer(element){
	showMessage("wait","Upload de l'image sur le serveur...");
	imageIndex=imageIndex+1;
	var file=element.files[0];
	var fd=new FormData();
	
	var nameList = element.value.split(".");
	var extension = nameList[nameList.length-1];
	
	fd.append("imageNb",imageIndex+"."+extension);
	fd.append("imageData",file);
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'rest/questionnaireTools/uploadImage', true);
	xhr.onload = function() {
	    if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
	    	//Disparition du wait
	    	//Status d'upload?
	    	//Ajout du div contenant l'index de l'image
	    	element.parentNode.parentNode.getElementsByClassName("imgNb")[0].innerHTML=imageIndex+"."+extension;
	    	setMessageVisible(false);
	    }
	};
	xhr.send(fd);
}
function deleteImageOnServer(element){
	var fileName=element.parentNode.parentNode.getElementsByClassName("imgNb")[0].innerHTML;
	if(fileName!=""){
		showMessage("wait","Suppression de l'image sur le serveur...");
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'rest/questionnaireTools/deleteImage', true);
		xhr.onload = function() {
		    if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
		    	//Disparition du wait
		    	element.parentNode.parentNode.getElementsByClassName("imgNb")[0].innerHTML="";
		    	setMessageVisible(false);
		    }
		};
		xhr.send(fileName);
	}
}
