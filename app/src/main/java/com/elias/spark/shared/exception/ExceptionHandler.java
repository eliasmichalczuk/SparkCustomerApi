package com.elias.spark.shared.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Request;
import spark.Response;
import spark.Route;

public class ExceptionHandler implements Route {

	private Route controllerHandle;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public ExceptionHandler(Route controllerHandle) {
		super();
		this.controllerHandle = controllerHandle;
		objectMapper.findAndRegisterModules();
	}

	public static ExceptionHandler wrap(Route controllerHandle) {
		return new ExceptionHandler(controllerHandle);
	}

	public Object handle(Request req, Response res) throws JsonProcessingException {
		try {
			return objectMapper.writeValueAsString(controllerHandle.handle(req, res));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(res.raw().getStatus());
			System.out.println(res.raw().getStatus() / 100);
			if (res.raw().getStatus() / 100 == 2) {
				res.status(400);
			}
			res.raw().getStatus();
			return objectMapper.writeValueAsString(new ExceptionResponse(e.getMessage(), e.getClass().getSimpleName()));
		}
	}
}