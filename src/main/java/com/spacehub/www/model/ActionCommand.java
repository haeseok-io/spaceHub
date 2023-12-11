package com.spacehub.www.model;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionCommand {
	public String execute(HttpServletRequest req, HttpServletResponse resp);
}
