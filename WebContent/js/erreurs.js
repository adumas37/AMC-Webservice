/*****
Fichier JS pour le traitement et l'affichage des erreurs et loading screens
******/
var coverDiv;
var messageDiv;
var styleCover="position:absolute;bottom:0px;width:100%;height:100%;background-color:rgba(0, 0, 0, 0.6);z-index:999;";
var styleMessage="position:absolute;padding:10px;" +
		"width:300px;height:150px;left:50%;top:50%;margin-top:-75px;margin-left:-150px;" +
		"background-color:#F7F7F7;border:2px solid #D1D1D1;";
var messageTopImage;
var messageTitle;
var messageParagraph;
var messageDivBottom;

coverDiv=document.createElement("div");
coverDiv.setAttribute("style", "visibility:hidden;"+styleCover);

messageDiv = document.createElement("div");
messageDiv.setAttribute("id", "message");
coverDiv.appendChild(messageDiv);

document.getElementsByTagName("body")[0].appendChild(coverDiv);

function initialiseMessage(){

	coverDiv.removeChild(messageDiv);
	messageDiv=0;
	messageDiv = document.createElement("div");
	messageDiv.setAttribute("id", "message");
	messageDiv.setAttribute("style", styleMessage);
	
	messageTopImage = document.createElement("img");
	messageTopImage.setAttribute("id","messageTopImage");
	messageTopImage.setAttribute("style","display:inline-block;margin-right:10px;");
	messageDiv.appendChild(messageTopImage);

	messageTitle=document.createElement("P");
	messageTitle.setAttribute("style","text-align:left;display:inline-block;");
	messageDiv.appendChild(messageTitle);

	messageParagraph=document.createElement("P");
	messageParagraph.setAttribute("style","text-align:center;");
	messageDiv.appendChild(messageParagraph);

	messageDivBottom = document.createElement("div");
	messageDivBottom.setAttribute("style","text-align:center;");
	messageDiv.appendChild(messageDivBottom);

	coverDiv.appendChild(messageDiv);

};
function setMessageVisible(state){
	if(state){
		coverDiv.setAttribute("style", "visibility:visible;"+styleCover);
	}else{
		coverDiv.setAttribute("style", "visibility:hidden;"+styleCover);
		
	}
};
function setMessageText(text){
	messageParagraph.innerHTML=text;
};
function setMessageType(type){
	switch(type){
	case 1:
		messageTitle.innerHTML="Information";
		messageTopImage.setAttribute("src","src/information.png");
		break;
	case 2:
		messageTitle.innerHTML="Veuillez patienter...";
		var messageBottomImage = document.createElement("img");
		messageBottomImage.setAttribute("id","messageBottomImage");
		messageBottomImage.setAttribute("src","src/waiting.gif");
		messageDivBottom.appendChild(messageBottomImage);
		messageTopImage.setAttribute("src","src/choice.png");
		break;
	case 3:
		messageTitle.innerHTML="Attention !";
		createChoiceForm();
		messageTopImage.setAttribute("src","src/information.png");
		break;
	case 4:
		messageTitle.innerHTML="Oups !";
		createOKButton();
		messageTopImage.setAttribute("src","src/error.png");
		break;
	default:
		messageTitle.innerHTML="Information";
		messageTopImage.setAttribute("src","src/information.png");
	}
};
function createOKButton(){
	var messageFormulaireBoutonOK=document.createElement("button")
	messageFormulaireBoutonOK.setAttribute("name","answer");
	messageFormulaireBoutonOK.setAttribute("style","text-align:center;");
	messageFormulaireBoutonOK.setAttribute("onclick","setMessageVisible(false)");
	messageFormulaireBoutonOK.innerHTML="OK";
	messageDivBottom.appendChild(messageFormulaireBoutonOK);
};
function createChoiceForm(){
	var messageFormulaire=document.createElement("form");
	messageFormulaire.setAttribute("style","text-align:center;");
	var messageFormulaireBoutonOK=document.createElement("button")
	messageFormulaireBoutonOK.setAttribute("name","answer");
	messageFormulaireBoutonOK.setAttribute("value",true);
	messageFormulaireBoutonOK.setAttribute("style","text-align:right;");
	messageFormulaireBoutonOK.innerHTML="OK";
	var messageFormulaireBoutonCancel=document.createElement("button")
	messageFormulaireBoutonCancel.setAttribute("name","answer");
	messageFormulaireBoutonCancel.setAttribute("value",false);
	messageFormulaireBoutonCancel.setAttribute("style","text-align:left;");
	messageFormulaireBoutonCancel.innerHTML="Annuler";
	messageFormulaire.appendChild(messageFormulaireBoutonCancel);
	messageFormulaire.appendChild(messageFormulaireBoutonOK);
	messageDiv.appendChild(messageFormulaire);
};