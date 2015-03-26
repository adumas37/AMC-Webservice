<?php
session_start();
$_SESSION['loginFailed']=false;
if (isset($_POST['username'])){
	/*$Serveur = "ldap://rldap.ec-nantes.fr";
	$Liaison_LDAP = ldap_connect($Serveur);
	if ($Liaison_LDAP) {
		// Le serveur est accessible
		$LDAP_DN="uid=$login, ou=people, dc=ec-nantes, dc=fr";
		$LDAPBind_User = ldap_bind($Liaison_LDAP, $LDAP_DN, $password);
		if ($LDAPBind_User)*/
	if (($_POST['username']=="AMC"||$_POST['username']=="AMC2") && $_POST['password']=="AMC"){
			// Utilisateur authentifie sur le serveur LDAP
			//ldap_close($Liaison_LDAP); Enlever le comment losque le serveur sera sur le réseau ECN
			session_set_cookie_params('31536000');
			setcookie('AMC_Webservice',$_POST['username'],time()+2*365*86400);
			session_regenerate_id(true); 
			$_SESSION['username']=$_POST['username'];
			echo '<script>
				  var xhr = new XMLHttpRequest();
				  xhr.open("POST","rest/utilisateurs/add",false);
				  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				  xhr.send(null);
				  location.href="index.php";
				  </script>';
	}
	else {
			// Utilisateur non authentifie
			$_SESSION['loginFailed']=true;
			header('location: identification.php');
		}
	
}
//Utilisateur déjà authéntifié pendant les dernières 24 heures
elseif (isset ($_SESSION['username'])){
	header('location: index.php');
}
//Utilisateur qui tente d'accéder à la page sans passer par identification.php
else
{
			$_SESSION['loginFailed']=true;
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
