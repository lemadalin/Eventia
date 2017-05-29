<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	session.invalidate();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/login_style.css" rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/css/materialize.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Prata"
	rel="stylesheet">
<title>Eventia - Authentication</title>
</head>
<body>
	<header> </header>
	<section>
		<div id="wrapper">
			<div class="row">
				<form class="col s12" action="LoginController" method="post">
					<div class="row">
						<div class="col s12 right-align">
							<a href="index.html"><i class="material-icons prefix">fast_rewind</i></a>
						</div>
					</div>
					<div class="row">
						<div class="col s12 center-align">
							<div id="authenticate_text">Authenticate</div>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<i class="material-icons prefix">account_circle</i> <input
								name="username" id="username" type="text"
								class="active validate" required> <label for="username">Username</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<i class="material-icons prefix">vpn_key</i> <label
								for="password">Password</label> <input id="password"
								name="password" type="password" class="validate" required>
						</div>
					</div>
					<div class="row" id="error_msg_login">
						<div class="col s12">
							<div class="center-align">
								<span class="red-text text-darken-2">Incorrect username or password!</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<div class="center-align">
								<button type="submit" class="waves-effect waves-light btn blue darken-2">
									Login<i class="material-icons right">lock_open</i>
								</button>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col s12">
							<div class="center-align">
								Not registered yet? Click <a href="#" id="click_here_register">here</a> to sign up!
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="row" id="register_form">
				<form class="col s12" action="RegisterController" method="post">
					<div class="row">
						<div class="col s12 center-align">
							<div id="register_text">Register</div>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<i class="material-icons prefix">account_circle</i> <input
								name="username_reg" id="username_reg" type="text"
								class="active validate" required> <label for="username_reg">Username</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<i class="material-icons prefix">vpn_key</i> <label
								for="password_reg">Password</label> <input id="password_reg"
								name="password_reg" type="password" class="validate" required>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<div class="center-align">
								<button type="submit" class="waves-effect waves-light btn blue darken-2">
									Sign up<i class="material-icons right">perm_identity</i>
								</button>
							</div>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col s12 center-align">
							<a href="#"><i class="material-icons prefix small" id="hide_register_form">open_in_browser</i></a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
	<footer>
		<script src="js/login_tools.js"></script>
	</footer>
</body>
</html>