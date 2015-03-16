<?php
session_start();
if (isset($_SESSION['username'])){
	session_set_cookie_params('0');
	session_regenerate_id(true); 
	session_unset();
	session_destroy();
	header('location:identification.php');
?>
<!DOCTYPE html >
<html lang="fr">
<head>
    <title>AMC Webservice Logout</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="Page d'accueil">
    <meta name="author" content="Alexis DUMAS && Erwan Briand">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
</body>
</html>
<?php 
}
else {
	header('location: identification.php');
}