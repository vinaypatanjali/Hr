// get reference to departure location input
var departureLocationField = document.getElementById("departureLocation");

// get reference to arrival location input
var arrivalLocationField = document.getElementById("arrivalLocation");


/*
 Validate Form
*/
function validateEditForm() {

	// Get departure location
	var departureLocation = departureLocationField.value;
	
	// Get arrival location
	var arrivalLocation = arrivalLocationField.value;
	
	// If criteria not met for departure location and arrival location
	if(departureLocation.length != 3 || arrivalLocation.length != 3)
	{
	alert("Departure Location and Arrival Location should be of 3 Characters Like DEL");
	return false;
	}
	
	console.log("here");
	
	// If departure location and arrival location are same
	if( departureLocation.toUpperCase() === arrivalLocation.toUpperCase())
	{
	alert("Departure Location and Arrival Location cannot be same");
	return false;
	}

	return true;
}