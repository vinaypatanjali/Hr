package com.nagarro.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.models.Employee;
import com.nagarro.models.User;
import com.nagarro.service.HRService;
import com.nagarro.sorter.SortByID;
import com.nagarro.utils.Constants;
import com.nagarro.utils.Fields;
import com.nagarro.utils.Views;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

/**
 * 
 * @author vinaypatanjali
 *         <p>
 *         Main Spring MVC Controller to control full web application
 *         </p>
 */
@Controller
public class WebController {

	@Autowired(required = true)
	private HRService hrService;

	@Autowired(required = true)
	private RestTemplate restTemplate;

	private Employee employee;

	/**
	 * Mapping of url /
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return Views.LOGIN;
	}

	/**
	 * Mapping of login page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return Views.LOGIN;
	}

	/**
	 * Verifies User
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @param session
	 * @return target
	 */
	@RequestMapping(value = "/verifyLogin", method = RequestMethod.POST)
	public String validateUser(@RequestParam(Fields.USERNAME) String userName,
			@RequestParam(Fields.PASSWORD) String password, HttpServletRequest request, HttpSession session) {

		boolean check = false;

		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);

		try {
			// validate User from Database
			check = hrService.validateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// check if user found
		if (check) {
			System.out.println(Constants.LOGIN_SUCCESSFULL);
			session.setAttribute(Constants.IS_LOGGED_IN, true);
			session.setAttribute(Fields.USERNAME, user.getUsername());

			return "redirect:/getAllEmployees";

		} else {
			request.setAttribute(Views.MESSAGE, Constants.WRONG_CREDENTIALS);
			request.setAttribute(Views.TARGET, Constants.LOGIN_TARGET);
			request.setAttribute(Views.BUTTON_VALUE, Constants.LOGIN_STRING);
			return Views.RESPONSE;
		}
	}

	/**
	 * Mapping of getAllEmployees
	 * 
	 * @param request
	 * @param session
	 * @return target
	 */
	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)
	public String getAllEmployees(HttpServletRequest request, HttpSession session) {

		if (!userLoggedIn((Boolean) session.getAttribute(Constants.IS_LOGGED_IN), Constants.NO_LOGIN,
				Constants.LOGIN_TARGET, Constants.LOGIN_STRING, request)) {
			return Views.RESPONSE;
		}

		List<Employee> employees2 = getAllEmployeesList();

		request.setAttribute("list", employees2);

		return Views.EMPLOYEE_LISTING;

	}

	/**
	 * This method handles request for getting employee with a particular id
	 * 
	 * @return target index
	 */
	@RequestMapping(value = "/getEmployee/{id}", method = RequestMethod.GET)
	public String getEmployee(@PathVariable(value = "id") Long id, HttpServletRequest request, HttpSession session) {

		if (!userLoggedIn((Boolean) session.getAttribute(Constants.IS_LOGGED_IN), Constants.NO_LOGIN,
				Constants.LOGIN_TARGET, Constants.LOGIN_STRING, request)) {
			return Views.RESPONSE;
		}

		final String uri = "http://localhost:9090/employees/employee/" + id;

		String result = restTemplate.getForObject(uri, String.class);
		System.out.println(result);

		System.out.println("result id" + result);

		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		Employee employee = null;

		// read json file and convert to customer object
		try {
			employee = objectMapper.readValue(result, Employee.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(employee);

		request.setAttribute("employee", employee);

		return "editEmployee";
	}

	/**
	 * This method handles request for updating a employee
	 * 
	 * @return target index
	 */
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
	public String updateEmployee(@RequestParam(value = Fields.EMPLOYEE_CODE) Long employeeCode,
			@RequestParam(value = Fields.EMPLOYEE_NAME) String employeeName,
			@RequestParam(value = Fields.EMPLOYEE_LOCATION) String employeeLocation,
			@RequestParam(value = Fields.EMPLOYEE_EMAIL) String employeeEmail,
			@RequestParam(value = Fields.EMPLOYEE_DOB) Date employeeDOB, HttpSession session,
			HttpServletRequest request) {

		if (!userLoggedIn((Boolean) session.getAttribute(Constants.IS_LOGGED_IN), Constants.NO_LOGIN,
				Constants.LOGIN_TARGET, Constants.LOGIN_STRING, request)) {
			return Views.RESPONSE;
		}

		employee = new Employee();
		employee.setId(employeeCode);
		employee.setEmployeeName(employeeName);
		employee.setEmployeeLocation(employeeLocation);
		employee.setEmployeeEmail(employeeEmail);
		employee.setEmployeeDOB(employeeDOB);

		// Prepare url
		String url = "http://localhost:9090/employees/add";

		// Creating the ObjectMapper object
		ObjectMapper mapper = new ObjectMapper();
		// Converting the Object to JSONString
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(employee);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
		String answerString =  restTemplate.postForObject(url, entity, String.class);
		
		Employee emp2=null;
		
		 try {
			emp2 = mapper.readValue(answerString, Employee.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute(Views.MESSAGE, "Employee Updated Successfully with id : "+emp2.getId() + ", Name : "+emp2.getEmployeeName());
		request.setAttribute(Views.TARGET, "getAllEmployees");
		request.setAttribute(Views.BUTTON_VALUE, Constants.GO_BACK);
		return Views.RESPONSE;
	}

	/**
	 * This method writes all employee details to a csv file name employeeDetails
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/downloadCSV", method = RequestMethod.GET)
	public String downloadCSV(HttpServletRequest request, HttpSession session) {

		if (!userLoggedIn((Boolean) session.getAttribute(Constants.IS_LOGGED_IN), Constants.NO_LOGIN,
				Constants.LOGIN_TARGET, Constants.LOGIN_STRING, request)) {
			return Views.RESPONSE;
		}

		List<Employee> employees2 = getAllEmployeesList();

		System.out.println(employees2);

		File file = new File(WebController.class.getResource("").getPath() + "/employeeDetails.csv");

		System.out.println(file);

		FileWriter outputfile;
		try {
			outputfile = new FileWriter(file);

			CSVWriter writer = new CSVWriter(outputfile);

			String[] header = { "Employee Name", "Employee Location", "Employee Email", "EmployeeDOB" };
			writer.writeNext(header);

			employees2.forEach(employee -> {
				String[] data1 = { employee.getEmployeeName(), employee.getEmployeeLocation(),
						employee.getEmployeeEmail(), employee.getEmployeeDOB().toString() };
				writer.writeNext(data1);
			});

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect: downloadEmployeeDetails";
	}

	/**
	 * This method downloads csv file containing employee details
	 * 
	 * @param response
	 * @param request
	 * @param session
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/downloadEmployeeDetails", method = RequestMethod.GET)
	public void downloadEmployeeDetails(HttpServletResponse response, HttpServletRequest request, HttpSession session)
			throws FileNotFoundException {

		if (!userLoggedIn((Boolean) session.getAttribute(Constants.IS_LOGGED_IN), Constants.NO_LOGIN,
				Constants.LOGIN_TARGET, Constants.LOGIN_STRING, request)) {
			return;
		}

		File file = new File(WebController.class.getResource("").getPath());

		// String downloadFolder = context.getRealPath("/WEB-INF/downloads/");
		Path file1 = Paths.get(file.toString(), "employeeDetails.csv");

		// Check if file exists
		if (Files.exists(file1)) {
			// set content type
			response.setContentType("application/pdf");
			// add response header
			response.addHeader("Content-Disposition", "attachment; filename=" + "employeeDetails.csv");
			try {
				// copies all bytes from a file to an output stream
				Files.copy(file1, response.getOutputStream());
				// flushes output stream
				response.getOutputStream().flush();
			} catch (IOException e) {
				System.out.println("Error :- " + e.getMessage());
			}

		} else {
			System.out.println("Sorry File not found!!!!");
			throw new FileNotFoundException();

		}
	}
	
	/**
	 * Return page for uploading file for employee details
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/uploadCSV" , method = RequestMethod.GET)
	public String uploadCSV(HttpServletRequest request, HttpSession session) {
		
		if (!userLoggedIn((Boolean) session.getAttribute(Constants.IS_LOGGED_IN), Constants.NO_LOGIN,
				Constants.LOGIN_TARGET, Constants.LOGIN_STRING, request)) {
			return Views.RESPONSE;
		}
		
		return Views.UPLOAD_EMPLOYEE_DETAILS;
	}

	/**
	 * This method uploads csv containing employee details and save employees to
	 * database
	 * 
	 * @param file
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadEmployeeDetails", method = RequestMethod.POST)
	public String uploadEmployeeDetails(@RequestParam(value = "csvFile") MultipartFile file, HttpSession session,
			HttpServletRequest request) {

		if (!userLoggedIn((Boolean) session.getAttribute(Constants.IS_LOGGED_IN), Constants.NO_LOGIN,
				Constants.LOGIN_TARGET, Constants.LOGIN_STRING, request)) {
			return Views.RESPONSE;
		}

		String typeString = file.getOriginalFilename().split("\\.")[1];

		if (!typeString.equals(Constants.FILE_FORMAT_CSV)) {
			request.setAttribute(Views.MESSAGE, "Only CSV File allowed");
			request.setAttribute(Views.TARGET, "uploadCSV");
			request.setAttribute(Views.BUTTON_VALUE, Constants.GO_BACK);
			return Views.RESPONSE;
		}

		Reader reader = null;
		try {
			reader = new InputStreamReader(file.getInputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}
		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
		System.out.println(csvReader);

		List<String[]> employeeDetailsArrayList = null;

		try {
			employeeDetailsArrayList = csvReader.readAll();
		} catch (IOException | CsvException e) {

			e.printStackTrace();
		}

		List<Employee> employeesList = new ArrayList<Employee>();

		// write to csv
		employeeDetailsArrayList.forEach(data -> {
			for (int i = 0; i < data.length; i++) {
				System.out.print(data[i] + "ji");
				String[] employeesData = data[i].split(Constants.SPLIT_FILE_DATA);

				Employee employee = new Employee();
				employee.setEmployeeName(employeesData[0]);
				employee.setEmployeeLocation(employeesData[1]);
				employee.setEmployeeEmail(employeesData[2]);
				try {
					employee.setEmployeeDOB(
							new Date(new SimpleDateFormat("dd-mm-yyyy").parse(employeesData[3]).getTime()));
				} catch (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
				}
				System.out.println(employee);

				employeesList.add(employee);

			}
		});

		String url = "http://localhost:9090/employees/addAll";

		// Creating the ObjectMapper object
		ObjectMapper mapper = new ObjectMapper();
		// Converting the Object to JSONString
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(employeesList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		String answer = null;

		if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
			try {
				answer = mapper.writeValueAsString(responseEntity.getBody());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println(answer);

		request.setAttribute(Views.MESSAGE, "Employee(s) Uploaded Successfully.");
		request.setAttribute(Views.TARGET, "getAllEmployees");
		request.setAttribute(Views.BUTTON_VALUE, "Go Back");
		return Views.RESPONSE;
	}

	/**
	 * This method handles request for invalid addresses
	 * 
	 * @return target index
	 */
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String invalidAddress() {
		return Views.LOGIN;
	}

	/**
	 * Logout user from current session
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpSession session) {

		session.invalidate();
		request.setAttribute(Views.MESSAGE, "Logout Successful");
		request.setAttribute(Views.TARGET, "login");
		request.setAttribute(Views.BUTTON_VALUE, "Login");
		return Views.RESPONSE;
	}

	/**
	 * 
	 * @return list of all employees
	 */
	public List<Employee> getAllEmployeesList() {
		final String uri = "http://localhost:9090/employees";

		String result = restTemplate.getForObject(uri, String.class);
		System.out.println(result);

		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		Employee[] employees = null;

		// read json file and convert to customer object
		try {
			employees = objectMapper.readValue(result, Employee[].class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Employee> employees2 = new ArrayList<Employee>(Arrays.asList(employees));

		Collections.sort(employees2, new SortByID());

		System.out.println(employees2);

		return employees2;
	}

	/**
	 * checks whether user logged in or not
	 * 
	 * @param isLoggedIn
	 * @param message
	 * @param target
	 * @param buttonText
	 * @param request
	 * @return
	 */
	public boolean userLoggedIn(Boolean isLoggedIn, String message, String target, String buttonText,
			HttpServletRequest request) {

		// checks user loggen in or not
		if (isLoggedIn == null || isLoggedIn == false) {
			request.setAttribute(Views.MESSAGE, message);
			request.setAttribute(Views.TARGET, target);
			request.setAttribute(Views.BUTTON_VALUE, buttonText);

			return false;
		}

		return true;
	}

}
