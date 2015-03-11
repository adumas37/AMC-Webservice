<?php
session_start();
$_SESSION['logAttempt']=true;
if (isset($_POST['username'])){
	$Serveur = "ldap://rldap.ec-nantes.fr";
	$Liaison_LDAP = ldap_connect($Serveur);
	/*if ($Liaison_LDAP) {
		// Le serveur est accessible
		$LDAP_DN="uid=$login, ou=people, dc=ec-nantes, dc=fr";
		$LDAPBind_User = ldap_bind($Liaison_LDAP, $LDAP_DN, $password);
		if ($LDAPBind_User)*/
	if ($_POST['username']=="AMC" && $_POST['password']=="AMC"){
			// Utilisateur authentifie sur le serveur LDAP
			//ldap_close($Liaison_LDAP); Enlever le comment losque le serveur sera sur le réseau ECN
			$_SESSION['username']=$_POST['username'];
			$_SESSION['logAttempt']=false;
			header('location: index.php');
	}
	else {
			// Utilisateur non authentifie
			header('location: identification.php');
		}
	
}
elseif (isset ($_SESSION['username'])){
	header('location: index.php');
}
else
{
	header('location: identification.php');
}
?>
<!DOCTYPE html >
<html lang="fr">
<head>
    <title>AMC Webservice Login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="Page d'accueil">
    <meta name="author" content="Alexis DUMAS && Erwan Briand">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
</body>
</html>
