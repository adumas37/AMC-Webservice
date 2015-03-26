<?php
session_start();
if (isset($_SESSION['username'])){
?>
<!DOCTYPE html >
<html lang="fr">
<head>
	<title>AMC Webservice - Projet</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="Gestion d'un projet">
    <meta name="author" content="Alexis DUMAS">
	
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>

	<header class="main-header">AMC Webservice - Projet: <span id="nomProjet"></span></header>
	<div class="ID">Logg&eacute; en tant que <?php echo $_SESSION['username'] ?></div>
	<div class="logout"><a href="logout.php">Se d<?php echo htmlspecialchars("é") ?>connecter</a></div>
	
	<div id="content" class="contenu index">
		<div id="projetQuestionnaire">
			<table>
				<tr>
				<td>Questionnaire :</td>
				<td><a href="CreationQuestionnaire.php" >
					<input type="button" value="Edition questionnaire" class="inputButton orangeButton"/>
				</a></td>
				<td>
					<input type="button" name="download" value="Uploader questionnaire" class="inputButton orangeButton" onclick="upload()"/>
				</td>
				<td><form id="getQuestionnaire" method="get" action="rest/projet/questionnaire">
					<input type="submit" value="Telechargement source Latex" class="inputButton greenButton"/>
				</form>
				</td>
				</tr>
			</table>	
		</div>
	
		<div id="projetFichiers">

			<table>
				<tr>
					<td>Fichiers : </td>
					<td><form id="getCopies" method="get" action="rest/projet/copies">
						<input type="submit" value="Copies" class="inputButton greenButton"/>
					</form></td>
					<td><form id="getCatalog" method="get" action="rest/projet/catalog">
						<input type="submit" value="Catalogue des questions" class="inputButton greenButton"/>
					</form></td>
					<td><form id="getCorrige" method="get" action="rest/projet/corrige">
						<input type="submit" value="Correction des questions" class="inputButton greenButton"/>
					</form></td>
				</tr>
			</table>
		</div>
		<p>
		<a href="UploadCopies.php">
			<input type="button" value="Debuter la correction" class="inputButton orangeButton"/>
		</a>
		</p>
		<p>
			<a href="index.php">
				<input type="button" value="Accueil" class="inputButton orangeButton"/>
			</a>
		</p>
	</div>
	<footer class="footer">Work in progress - Erwan BRIAND && Alexis DUMAS</footer>
	
	<div id="newTex" style="visibility:hidden;">
		<form id="creationProjet" method="post" action="rest/creationProjet" enctype="multipart/form-data" onsubmit="return creationValide();">
			<p>Choisissez un nouveau fichier latex pour votre projet:</p>
			<p id="nomProjet"  style="display:none;">Nom du projet: <input id="nomProjetInput" name="nom" type="text" class="inputButton inputText"/></p>
			<p id="fichierTex">Questionnaire (.tex): <input id="fichierTexInput" name="file" type="file" accept=".tex" class="inputButton"> <input type="button" value="Supprimer le fichier" onclick="eraseFile()"/></p>
			<p>
				<input id="submit" type="submit" value="Creer Projet" class="inputButton orangeButton"/>
				<input id="cancel" type="button" value="Annuler" class="inputButton orangeButton" onclick="hide()"/>
			</p>
		</form>
	</div>
	<script src="js/projet.js"></script>
	<script src="js/erreurs.js"></script>
</body>
</html>
<?php 
}
else {
	header('location: identification.php');
}
?>
