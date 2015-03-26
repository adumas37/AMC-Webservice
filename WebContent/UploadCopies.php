<?php
session_start();
if (isset($_SESSION['username']) && isset($_COOKIE['AMC_Webservice'])){
?>
<!DOCTYPE html >
<html lang="fr">
<head>
	<title>AMC - Upload des copies</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="description" content="Upload des copies">
    <meta name="author" content="Erwan BRIAND && Alexis DUMAS">
	
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
	<header class="main-header">AMC Webservice - Import des copies scannées: <span id="nomProjet"></span></header>	
	<div class="ID">Logg&eacute; en tant que <?php echo $_SESSION['username'] ?></div>		
	<div class="logout"><a href="logout.php">Se d<?php echo htmlspecialchars("é") ?>connecter</a></div>
	<div id="content" class="contenu index">
	
		<p> Veuillez uploader les copies scannées au format PDF </p>
		
		<form id="uploadCopies" enctype="multipart/form-data">
			<copies id="copies">
				<input type="button" value="Ajouter fichier" onclick="addFile()"/>
				<p class="fichierCopies">Copies (.pdf): <input class="copiesPDFInput" name="file" accept=".pdf" type="file"> 
					<input type="button" value="Supprimer fichier" onclick="delFile(this)"/>
				</p>
			</copies>
			<!-- TODO  
			<classes id="classes">
				<input type="button" value="Ajouter classe" onclick="addClasse()"/>
				<p class="choixClasse">Classe: <select class="classeInput" name="classe">
					<option value="EI3-INFO">EI3-INFO</option>
					<option value="EI3-PROD">EI3-PROD</option>
					<option value="EI3-GC">EI3-GC</option>
					</select>
					<input type="button" value="Supprimer classe" onclick="delClasse(this)"/>
				</p>
			</classes>
			<csv id ="csv">
			-->
				<p class="fichierCsv">CSV de la classe (.csv): <input id="csvInput" name="csv" accept=".csv" type="file">
			</csv>
		</form>
		<input id="submit" type="button" value="Correction Copies" class="inputButton orangeButton" onclick="uploadFichiers(getUploadStatusThenCorrect)"/>
		<p>
			<a href="index.php">
				<input type="button" value="Accueil" class="inputButton orangeButton"/>
			</a>
		</p>
		
	</div>

	<footer class="footer">Work in progress - Erwan BRIAND && Alexis DUMAS</footer>
	
	<script src="js/projet.js"></script>
	<script src="js/upload.js"></script>
	<script src="js/erreurs.js"></script>
	
</body>
</html>
<?php 
}
else {
	header('location: identification.php');
}
?>
