<?php
session_start();
if (isset($_SESSION['username'])){
?>
<!DOCTYPE html >
<html lang="fr">
<head>
	<title>AMC Webservice - Mail envoye</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="mail envoye">
    <meta name="author" content="Alexis DUMAS && Erwan Briand">
	
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>

	<header class="main-header">AMC Webservice - Envois des mails</header>	
	<div class="ID">Logg&eacute; en tant que <?php echo $_SESSION['username'] ?></div>
	<div class="logout"><a href="logout.php">Se d<?php echo htmlspecialchars("é") ?>connecter</a></div>
	
	<div id="content" class="contenu index">
		<div id="mailResultats">
			<p>Un mail contenant les copies corrigées des étudiants ainsi que leurs notes a été envoyé.</p>
			<p>Si vous souhaitez conserver une trace des résultats, cliquez sur le bouton téléchargement.</p>
			<input type="button" value="Telecharger les resultats" class="inputButton greenButton"/>
		</div>
		<p>
			<a href="CorrectionCopies.php">
				<input type="button" class="inputButton orangeButton" value="retour"/>
			</a>
		</p>
	</div>

	<footer class="footer">Work in progress - Erwan BRIAND && Alexis DUMAS</footer>

</body>
</html>
<?php 
}
else {
	header('location: identification.php');
}
?>
