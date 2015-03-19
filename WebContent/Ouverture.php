<?php
session_start();
if (isset($_SESSION['username'])){
?>
<!DOCTYPE html >
<html lang="fr">
<head>
	<title>AMC - Ouverture de projet</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="description" content="Ouverture de projet">
    <meta name="author" content="Alexis DUMAS && Erwan Briand">
	
    <link href="css/styles.css" rel="stylesheet">
</head>
<body onload="afficherDossiers(this)">

	<header class="main-header">AMC Webservice - <span id="action">Choix du projet</span></header>
	<div class="ID">Logg&eacute; en tant que <?php echo $_SESSION['username'] ?></div>
	<div class="logout"><a href="logout.php">Se d<?php echo htmlspecialchars("é") ?>connecter</a></div>
	
	<hr>

	<div id="content" class="contenu" >
		<div id="explorer"></div>
		<a href="index.php" >
			<input type="button" value="Accueil" class="inputButton orangeButton"/>
		</a>
	</div> 
	
	<hr>
	<footer class="footer">Work in progress - Erwan BRIAND && Alexis DUMAS</footer>
	
	<script src="js/ouverture.js"></script>
	<script src="js/erreurs.js"></script>
</body>
</html>
<?php 
}
else {
	header('location: identification.php');
}
?>
