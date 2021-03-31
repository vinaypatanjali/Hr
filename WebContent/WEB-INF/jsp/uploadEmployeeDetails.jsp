<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Employee Details</title>
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
		<div class="w-100 p-3 right welcome">
			<a class="navbar-brand" style="font-weight: bolder;" href="#">Welcome
				${sessionScope.username}</a>

			<button type="button" class="btn btn-secondary"
				onclick="window.location='${pageContext.request.contextPath}/logout'"
				title="Logout">
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
					fill="currentColor" class="bi bi-box-arrow-left"
					viewBox="0 0 16 16">
  <path fill-rule="evenodd"
						d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"></path>
  <path fill-rule="evenodd"
						d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"></path>
</svg>
			</button>
		</div>
	</nav>
	<!-- Container -->
	<div class="container min-vh-80">
		<!-- Login Form -->
		<form action="uploadEmployeeDetails" method="post"
			enctype="multipart/form-data">
			<div class="card card-login">

				<!-- Form Header -->
				<div class="card-header">Upload</div>

				<!-- Form Body -->
				<div class="card-body">

					<!-- Form Row for Username-->
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">Upload File<label
							class="asterisk">*</label></label>
						<div class="col-sm-8">
							<input type="file" name="csvFile" id="csvFile"
								class="form-control" required />
						</div>
					</div>



				</div>
				<!-- Form Footer -->
				<div class="card-footer text-muted">
					<input type="submit" /> <input type="button"
						onclick="window.location='getAllEmployees'" value="Go Back" />
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

</body>
</html>