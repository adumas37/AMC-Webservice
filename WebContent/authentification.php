<?php
session_start();
$_SESSION['logAttempt']=true;
if (isset($_POST['username'])){
	if ($_POST['username']=="AMC"){
		$_SESSION['username']=$_POST['username'];
		$_SESSION['logAttempt']=false;
		header('location: index.php');
	}
	else {
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
