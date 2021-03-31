package com.nagarro.boot.exceptionhandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.nagarro.utils.Views;

/**
 * 
 * @author vinaypatanjali
 *         <p>
 *         This class handles when any exception occurs
 *
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String error = ex.getRequestPartName() + " part is missing";
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String error = ex.getParameterName() + " parameter is missing";
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * This method handles EntityNotFoundException
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<Object> handleEntityNotFound(final EntityNotFoundException ex, final WebRequest request) {

		logger.info(ex.getClass().getName());

		final String error = ex.getMessage();

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * This method handles ConstraintViolationException
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {

		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage() + " (Maybe duplicate entry for employee email) ", errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * This method handles HttpClientErrorException
	 * 
	 * @param ex
	 * @param request
	 * @param request2
	 * @return String view
	 */
	@ExceptionHandler({ HttpClientErrorException.class })
	public String handleHttpClientError(final HttpClientErrorException ex, final WebRequest request,
			HttpServletRequest request2) {

		logger.info(ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		errors.add(ex.getLocalizedMessage());
		errors.add("For now no employee is saved among these");

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage()
						+ " (Maybe duplicate entry for employee email . Pls remove or update duplicate email) ",
				errors);
		request2.setAttribute(Views.MESSAGE,
				apiError.getMessage() + ".<br/><br/>" + "For now no employee is saved among these!!");
		request2.setAttribute(Views.TARGET, "uploadCSV");
		request2.setAttribute(Views.BUTTON_VALUE, "Try Upload Again");
		return Views.RESPONSE;
	}

	/**
	 * This method handles ResourceAccessException
	 * 
	 * @param ex
	 * @param request
	 * @param request2
	 * @return String view
	 */
	@ExceptionHandler({ ResourceAccessException.class })
	public String handleResourceAccess(final ResourceAccessException ex, final WebRequest request,
			HttpServletRequest request2) {

		System.out.println("in constraint");
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		errors.add(ex.getLocalizedMessage());
		errors.add("For now no employee is saved among these");

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage() + " (Cannot login due to server error) ", errors);
		request2.setAttribute(Views.MESSAGE, apiError.getMessage());
		request2.setAttribute(Views.TARGET, "login");
		request2.setAttribute(Views.BUTTON_VALUE, "login");
		return Views.RESPONSE;
	}

	/**
	 * This method handles DataIntegrityViolationException
	 * 
	 * @param ex
	 * @param request
	 * @return String ResponseEntity
	 */
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolation(final DataIntegrityViolationException ex,
			final WebRequest request) {

		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();

		errors.add(ex.getMessage());

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage() + " ( duplicate entry for employee email) ", errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * This method handles EmptyResultDataAccessException
	 * 
	 * @param ex
	 * @param request
	 * @return String ResponseEntity
	 */
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(final EmptyResultDataAccessException ex,
			final WebRequest request) {

		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		errors.add(ex.getLocalizedMessage());

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "No entry found with this id", errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * This method handles JsonProcessingException
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler({ JsonProcessingException.class })
	public ResponseEntity<Object> handleJsonProcessing(final JsonProcessingException ex, final WebRequest request) {

		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		errors.add(ex.getLocalizedMessage());

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Some error occured", errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Handles FileNotFoundException
	 * 
	 * @param ex
	 * @param request
	 * @param request2
	 * @return
	 */
	@ExceptionHandler({ FileNotFoundException.class })
	public String handleFileNotFound(final FileNotFoundException ex, final WebRequest request,
			HttpServletRequest request2) {

		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		errors.add(ex.getLocalizedMessage());

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Some error occured", errors);
		request2.setAttribute(Views.MESSAGE, apiError.getMessage());
		request2.setAttribute(Views.TARGET, "login");
		request2.setAttribute(Views.BUTTON_VALUE, "login");
		return Views.RESPONSE;
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
				builder.toString());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
				builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * This method handles all other exceptions
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {

		ex.printStackTrace();
		logger.info(ex.getClass().getName());
		logger.error("error", ex);
		//
		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				"error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
