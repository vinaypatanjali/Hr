<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<meta name="keywords" content="VP">
<meta name="description" content="Made by Vinay Patanjali @ Nagarro">
<meta name="author" content="Vinay Patanjali">


<title>HR Login</title>
<!-- CSS only -->

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
<link href="<c:url value="/resources/css/styleMy.css" />"
	rel="stylesheet">
</head>

<body>

	<!-- NAVBAR -->
	<nav class="navbar sticky-top navbar-light">
		<div class="container-fluid nav navbar-nav navbar-center">
			<a class="navbar-brand" style="font-weight: bolder;" href="#">Employee
				Management System</a>
		</div>
	</nav>

	<!-- Container -->
	<div class="container paddingTop-5">
		<!-- Login Form -->
		<form action="verifyLogin" method="post">
			<div class="card card-login">

				<!-- Form Header -->
				<div class="card-header">Login</div>

				<!-- Form Body -->
				<div class="card-body">

					<!-- Form Row for Username-->
					<div class="form-group row">
						<label for="username" class="col-sm-3 col-form-label">Username<label
							class="asterisk">*</label></label>
						<div class="col-sm-8">
							<input type="text" name="username" class="form-control"
								minlength="5" maxlength="40" required />
						</div>
					</div>

					<br />

					<!-- Form Row for Password-->
					<div class="form-group row">
						<label for="inputPassword" class="col-sm-3 col-form-label">Password<label
							class="asterisk">*</label></label>
						<div class="col-sm-8">
							<input type="password" name="password" class="form-control"
								minlength="5" maxlength="40" required />
						</div>
					</div>
				</div>

				<!-- Form Footer -->
				<div class="card-footer text-muted">
					<button>Login</button>
				</div>

			</div>
		</form>


	</div>

	<!-- Footer -->
	<footer class="page-footer font-small blue">

		<!-- Copyright -->
		<div class="footer-copyright text-center py-3">
			© 2021 Copyright: <a href="#"> Fresher Training</a>
		</div>
		<!-- Copyright -->

	</footer>
	<!-- Footer -->
</body>

<!-- JavaScript Bundle with Popper -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
	crossorigin="anonymous"></script>

</html>