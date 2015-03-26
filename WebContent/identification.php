<?php
session_start();
if (isset($_SESSION['username']) && isset($_COOKIE['AMC_Webservice'])){
	header('location: index.php');
}
?>
<!DOCTYPE html >
<html lang="fr">
<head>
	<title>AMC - Login</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="description" content="login">
    <meta name="author" content="Alexis DUMAS && Erwan Briand">
	<link href="css/styles.css" rel="stylesheet">
</head>

	<body onload="Load()">
		<div class="page">
			<div class="bloc-principal">
				<div class="main-header"> Identification </div> 
				<div id="content" class="contenu identification">
					
						<h2>Saisir votre nom d'utilisateur et mot de passe</h2>
						<p>	<form id="identification" action="authentification.php" method="post">
							<p id="wrongPassword">
							<?php 
							if (isset($_SESSION['loginFailed']) && $_SESSION['loginFailed']==true){
								echo 'Login ou mot de passe incorrect';
								$_SESSION['loginFailed']=false;
							}
							?>
							</p>
							<p>Nom d'utilisateur <input id="usernameInput" type="text" name="username" value="" class="inputButton inputText"/></p>
							<p>Mot de passe <input id="passwordInput" type="password" name="password" class="inputButton inputText"/></p>
							<p><input type="checkbox" name="stayLogged" value="stayLogged"/>M&eacute;moriser mes identifiants</p>
							<input type="submit" value="submit" value="Connexion" class="orangeButton inputButton"/>
							</form><p>
				
				</div> <!-- /content -->
			</div><!-- /bloc -->
			<div class="footer">
				Work in progress - Erwan BRIAND && Alexis DUMAS</div>

		</div><!-- page -->
	</body>
	<script>
		function Load(){var xhr2 = new XMLHttpRequest();
		xhr2.open("POST","rest/utilisateurs/load",false);
		xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr2.send();
		console.log("send");};
	</script>
</html>
