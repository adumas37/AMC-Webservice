<?php
session_start();
if (isset($_SESSION['username']) && isset($_COOKIE['AMC_Webservice'])){
?>
<!DOCTYPE html >
<html lang="fr">
<head>
	<title>AMC - Correction des copies</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="description" content="Correction des copies">
    <meta name="author" content="Alexis DUMAS && Erwan Briand">
	
    <link href="css/styles.css" rel="stylesheet">
</head>
<body onload="chargerNotes(this)">
	
	<header class="main-header">AMC Webservice - Correction des copies : <span id="nomProjet"></span></header>
	<div class="ID">Logg&eacute; en tant que <?php echo $_SESSION['username'] ?></div>		
	<div class="logout"><a href="logout.php">Se d<?php echo htmlspecialchars("é") ?>connecter</a></div>
	<div id="content" class="contenu correction">
	<input type="button" value="Afficher Notes" onclick="afficherNotes(this)" class="inputButton blueButton"/>
	<div id="resultats"> 
		<div id="tableau">
			tableau des resultats
		</div>
		<div id="indicateurs"></div>
	</div>
		<input type="button" value="Changer le barème" class="inputButton orangeButton" onclick='changerBareme()'/>	
		<input type="button" value="Gerer les copies" class="inputButton orangeButton" onclick='changerFichiers()'/>
		<!-- TODO <input type="button" value="Gerer les classes" class="inputButton orangeButton" onclick='changerClasses()'/>  -->
		<input type="button" value="Gerer le csv" class="inputButton orangeButton" onclick='changerClasses()'/>
		<!-- <form id="correct" method="post" action="rest/correction/LancerCorrection">-->
			<input type="button" value="Relancer la correction" class="inputButton greenButton" onclick='lancerCorrection(getCorrectionStatus)'/>
		<!-- </form> -->
		<form id="DownloadResults" method="get" action="rest/correction/download">
			<input type="submit" value="Telecharger les resultats" class="inputButton greenButton"/>
		</form>		
		<input type="button" value="Envoi des mails aux eleves" class="inputButton orangeButton"/>
		<a href="index.php"><input type="button" value="Accueil" class="inputButton orangeButton"/></a>
	</div>
	<div id="baremePopup" style="display: none;">
		<form id="changerBareme" method="post" action="rest/questionnaireTools/setBareme" enctype="multipart/form-data">
			<div id="bareme">
			</div>
			<p>
				<input id="submitBareme" type="submit" value="Changer le bareme" onclick="hideBareme()" class="inputButton orangeButton"/>
				<input id="cancelBareme" type="button" value="Annuler" class="inputButton orangeButton" onclick="hideBareme()"/>
			</p>
		</form>
	</div>
	<div id="fichiers" style="display: none;">
		<div id="gestionFichiers">
			<div id="oldFiles">
			</div>
			<div id="newFiles">
				<form id=uploadCopies enctype="multipart/form-data">
					<div id="upload">
						<p class="fichierCopies">Copies (.pdf): <input class="copiesPDFInput" name="file" type="file" onchange="chooseFile()"> 
							<input type="button" value="Supprimer fichier" onclick="delFile(this)"/>
						</p>
					</div>
					<p id="alertText" style="display:none;">		</p>
					<div>
						<input id="cancelFichiers" type="button" value="Annuler" class="inputButton orangeButton" onclick="hideFichiers()"/>
					</div>
				</form>
				<input id="submitFichiers" type="button" value="Changer les fichiers et calculer les notes"  class="inputButton orangeButton" onclick="uploadCopies(getUploadStatusThenCorrect)"/>
			</div>
		</div>
	</div>
	<div id="classes" style="display: none;">
		<div id="gestionClasses">
			<!--  <div id="oldClasses">
			</div>
			<div id="newClasses">
				<input type="button" value="Ajouter une classe" onclick="ajouterClasse()"/>
				<form id=uploadClasses enctype="multipart/form-data">
					<div id="upload2">
						<p class="choixClasse">Classe: <select class="classeInput" name="classe">
							<option value="EI3-INFO">EI3-INFO</option>
							<option value="EI3-PROD">EI3-PROD</option>
							<option value="EI3-GC">EI3-GC</option>
							</select>
							<input type="button" value="Supprimer classe" onclick="delClasse(this)"/>
						</p>
					</div>
					<p id="alertTextClasses" style="display:none;">		</p>
					<div>
						<input id="cancelClasses" type="button" value="Annuler" class="inputButton orangeButton" onclick="hideClasses()"/>
					</div>
				</form>
				<input id="submitClasses" type="submit" value="Ajouter les classes et calculer les notes"  class="inputButton orangeButton" onclick="uploadClasses(getUploadStatusThenCorrect)"/>
			</div>
			-->
			<form id="csvForm" action="rest/correction/csv" method="post" enctype="multipart/form-data">
				<p class="fichierCsv">CSV de la classe (.csv): <input id="csvInput" name="csv" accept=".csv" type="file">
				</p>
			</form>
			<p>
				<input id="submitCSV" type="submit" value="Changer le fichier csv"  class="inputButton orangeButton"  onclick="uploadCsv(getUploadStatusCSVThenCorrect)"/>
				<input id="cancelClasses" type="button" value="Annuler" class="inputButton orangeButton" onclick="hideClasses()"/>
			</p>
		</div>
	</div>
	<footer class="footer">Work in progress - Erwan BRIAND && Alexis DUMAS</footer>
	
	<script src="js/correction.js"></script>
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
