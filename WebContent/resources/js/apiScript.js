/**
 * Not used
 */

//getAllEmployees();

function getAllEmployees() {
    console.log("response", "hi");
    var request = new XMLHttpRequest();
	
    request.open("GET", "http://localhost:9090/employees");/*
	request.setRequestHeader('Access-Control-Allow-Origin', 'http://localhost:8080');
	request.setRequestHeader('Access-Control-Allow-Credentials', 'true');*/

    request.send();




    request.addEventListener('load', function () {

        console.log("response", request.responseText);

        var employees = JSON.parse(request.responseText);

        console.log("employees", employees);

		addEmployees(employees);



    });
}


function addEmployees(employees){
	
  var table = document.getElementById("employeeTable");
	
  for(var i=0;i<employees.length;i++)
{
  var row = table.insertRow();
  var cell1 = row.insertCell(0);
  var cell2 = row.insertCell(1);
  var cell3 = row.insertCell(2);
  var cell4 = row.insertCell(3);
  var cell5 = row.insertCell(4);
  var cell6 = row.insertCell(5);
  cell1.innerHTML = employees[i].id;
  cell2.innerHTML = employees[i].employeeName;
  cell3.innerHTML = employees[i].employeeLocation;
  cell4.innerHTML = employees[i].employeeEmail;
  cell5.innerHTML = employees[i].employeeDOB;
  cell6.innerHTML = "Actions";

}
	
}










