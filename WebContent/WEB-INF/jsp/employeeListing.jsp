<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<meta name="keywords" content="VP">
<meta name="description" content="Made by Vinay Patanjali @ Nagarro">
<meta name="author" content="Vinay Patanjali">


<title>Employees List</title>
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
	<div class="container">


		<!-- Header -->
		<div class="card-header center">Employees</div>
		<br />
		<div class="right">
			<input type="button" onclick="window.location='uploadCSV'"
				value="Upload Employee Details" /> <input type="button"
				onclick="window.location='${pageContext.request.contextPath}/downloadCSV'"
				value="Download Employee Details" />
		</div>
		<br />

		<div class="card  card-imageutility">



			<!-- Form Body -->
			<div class="card-body">


				<br />


				<!-- Found FLight Details are listed in the below table -->
				<table class="card-table table table-bordered tableFixHead"
					id="employeeTable">

					<!-- Table Head -->
					<thead class="sticky">
						<tr>
							<th scope="col">Code</th>
							<th scope="col">Name</th>
							<th scope="col">Location</th>
							<th scope="col">Email</th>
							<th scope="col">Date of Birth</th>
							<th scope="col">Action</th>
						</tr>
					</thead>

					<!-- Table Body -->
					<tbody>
						<!-- Loop to list flight details -->
						<c:forEach items="${list}" var="element">

							<!-- Table row -->
							<tr>
								<td>${element.id}</td>
								<td>${element.employeeName}</td>
								<td>${element.employeeLocation}</td>
								<td>${element.employeeEmail}</td>
								<td><fmt:formatDate type="date"
										value="${element.employeeDOB}" /></td>
								<td><button
										onclick="window.location='getEmployee/${element.id}'">Edit</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
			<!-- Footer -->

			<!-- <div class="card-footer text-muted">
						<input type="button" onclick="window.location='logout'" value="Logout"/>
					</div> -->
		</div>


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