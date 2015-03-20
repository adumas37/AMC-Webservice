<?php
session_start();
if (isset($_SESSION['username']) && isset($_COOKIE['AMC_Webservice'])){
	
?>
<!DOCTYPE html >
<html lang="fr">
<head>
    <title>AMC Webservice</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="Page d'accueil">
    <meta name="author" content="Alexis DUMAS && Erwan Briand">
    <link href="css/styles.css" rel="stylesheet">

</head>

<body>
	
	<div class="main-header">AMC Webservice</div>
	<div class="ID">Logg&eacute; en tant que <?php echo $_SESSION['username'] ?></div>
	<div class="logout"><a href="logout.php">Se d<?php echo htmlspecialchars("é") ?>connecter</a></div>


	<div id="content" class="contenu index">
		<!-- 	<a href="rest/first">coucou</a> 	<p/> <a href="html/test2.html">test</a>	<p/> <form action="rest/first" method="GET"><input type="submit" value="Get HelloWorld"></form> -->
		<ul id="homeNavList">
			<li class="homeLi"><a class="homeHref" href="CreationProjet.php">
					<img class="homeImg" height="50" src="src/bookmark-new.png" alt="creation">
					<span class="homeText">Creation d'un projet</span>
			</a></li>
			<li class="homeLi"><a class="homeHref" href="Ouverture.php" onclick="return ouverture();">
				<img class="homeImg" height="50" src="src/document-open.png" alt="open">
				<span class="homeText">Ouverture d'un projet</span>
			</a></li>
			<li class="homeLi"><a class="homeHref" href="Ouverture.php" onclick="return correction();">
				<img class="homeImg" height="50" src="src/document-open.png" alt="open">
				<span class="homeText">Correction de copies</span>
			</a></li>
			<li class="homeLi"><a class="homeHref" href="Ouverture.php"  onclick="return suppression();">
				<img class="homeImg" height="50" src="src/edit-delete.png" alt="delete">
				<span class="homeText"> Suppression d'un projet </span>
			</a>
			</li>
		</ul>
			
	</div> <!-- /content -->

	
	<footer class="footer">Work in progress - Erwan BRIAND && Alexis DUMAS </footer>
	
	<script src="js/navigation.js"></script>

</body>
</html>
<?php 
}
else {
	header('location: identification.php');
}
?>
