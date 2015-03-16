/*******************************************************************************
 * Fichier JS pour le traitement et l'affichage des erreurs et loading screens
 * 
 * UTILISATION
 * inclure erreur.js dans la page html
 * Pour afficher un message :
 * showMessage(type_du_message, texte_du_messagse, callback_boutonOK, callback_boutonAnnuler);
 * 
 * Les types de messages sont :
 * 	"information"
 * 	"wait"
 * 	"question"
 * 	"error"
 * Les callback par défaut sont réglés sur la fermeture du message (si non précisés dans la fonction)
 * Le bouton 'annuler' n'apparait que lorsque le message est du type question
 * Le bouton 'OK' apparaît partout sauf lors d'un wait
 ******************************************************************************/
var coverDiv;
var messageDiv;
var styleCover = "position:absolute;bottom:0px;width:100%;height:100%;background-color:rgba(0, 0, 0, 0.6);z-index:999;";
var styleMessage = "position:absolute;padding:2px;"
		+ "width:300px;height:auto;left:50%;top:50%;margin-top:-75px;margin-left:-150px;"
		+ "background-color:#F7F7F7;border:2px solid #D1D1D1;";
var messageTopImage;
var messageTitle;
var messageParagraph;
var messageDivBottom;
var returnValue = "-1";
var callbackA, callbackB;
coverDiv = document.createElement("div");
coverDiv.setAttribute("style", "visibility:hidden;" + styleCover);

messageDiv = document.createElement("div");
messageDiv.setAttribute("id", "message");
coverDiv.appendChild(messageDiv);

document.getElementsByTagName("body")[0].appendChild(coverDiv);
function showMessage(type, text, cbA,cbB) {
	initialiseMessage();
	setCallbackFunctions(cbA,cbB);
	setMessageType(type);
	setMessageText(text);
	setMessageVisible(true);
};
function initialiseMessage() {

	coverDiv.removeChild(messageDiv);
	returnValue = -1;
	messageDiv = 0;
	messageDiv = document.createElement("div");
	messageDiv.setAttribute("id", "message");
	messageDiv.setAttribute("style", styleMessage);

	messageTopImage = document.createElement("img");
	messageTopImage.setAttribute("id", "messageTopImage");
	messageTopImage.setAttribute("style",
			"display:inline-block;margin-right:10px;");
	messageDiv.appendChild(messageTopImage);

	messageTitle = document.createElement("P");
	messageTitle.setAttribute("style", "text-align:left;display:inline-block;margin:10px;padding:2px;");
	messageDiv.appendChild(messageTitle);

	messageParagraph = document.createElement("P");
	messageParagraph.setAttribute("style", "text-align:center;");
	messageDiv.appendChild(messageParagraph);

	messageDivBottom = document.createElement("div");
	messageDivBottom.setAttribute("style", "text-align:center;");
	messageDiv.appendChild(messageDivBottom);

	coverDiv.appendChild(messageDiv);

};
function setMessageVisible(state) {
	if (state) {
		coverDiv.setAttribute("style", "visibility:visible;" + styleCover);
	} else {
		coverDiv.setAttribute("style", "visibility:hidden;" + styleCover);

	}
};
function setMessageText(text) {
	messageParagraph.innerHTML = text;
};
function setMessageType(type) {
	switch (type) {
	case "information":
		messageTitle.innerHTML = "Information";
		messageTopImage.setAttribute("src", "src/information.png");
		break;
	case "wait":
		messageTitle.innerHTML = "Veuillez patienter...";
		var messageBottomImage = document.createElement("img");
		messageBottomImage.setAttribute("id", "messageBottomImage");
		messageBottomImage.setAttribute("src", "src/waiting.gif");
		messageDivBottom.appendChild(messageBottomImage);
		messageTopImage.setAttribute("src", "src/choice.png");
		break;
	case "question":
		messageTitle.innerHTML = "Attention !";
		createChoiceForm();
		messageTopImage.setAttribute("src", "src/information.png");
		break;
	case "error":
		messageTitle.innerHTML = "Oups !";
		createOKButton();
		messageTopImage.setAttribute("src", "src/error.png");
		break;
	default:
		messageTitle.innerHTML = "Information";
		messageTopImage.setAttribute("src", "src/information.png");
	}
};
function createOKButton() {
	var messageFormulaireBoutonOK = document.createElement("button")
	messageFormulaireBoutonOK.setAttribute("name", "answer");
	messageFormulaireBoutonOK.setAttribute("style", "text-align:center;");
	messageFormulaireBoutonOK.setAttribute("onclick",callbackA+";setMessageVisible(false);");
	messageFormulaireBoutonOK.innerHTML = "OK";
	messageDivBottom.appendChild(messageFormulaireBoutonOK);
};
function createChoiceForm() {
	var messageFormulaireBoutonOK = document.createElement("button");
	messageFormulaireBoutonOK.setAttribute("name", "answer");
	messageFormulaireBoutonOK.setAttribute("onclick",callbackA+";setMessageVisible(false);");
	messageFormulaireBoutonOK.setAttribute("style", "text-align:right;");
	messageFormulaireBoutonOK.innerHTML = "OK";
	var messageFormulaireBoutonCancel = document.createElement("button");
	messageFormulaireBoutonCancel.setAttribute("name", "answer");
	messageFormulaireBoutonCancel.setAttribute("onclick",callbackB+";setMessageVisible(false);");
	messageFormulaireBoutonCancel.setAttribute("style", "text-align:left;");
	messageFormulaireBoutonCancel.innerHTML = "Annuler";
	messageDivBottom.appendChild(messageFormulaireBoutonCancel);
	messageDivBottom.appendChild(messageFormulaireBoutonOK);
};
function waitForValue() {
	if(returnValue<0)
	{setTimeout(function(){waitForValue();},5000)}
	else{return 0;}
};
function setReturnValue(value) {
	returnValue = value;
};
function setCallbackFunctions(cbA,cbB){
	if( typeof(cbA) != 'undefined' ){
		callbackA=cbA;
	}else{
		callbackA="";
	}
	if (typeof(cbB) != 'undefined' ){
		callbackB=cbB;
	}else{
		callbackB="";
	}
};