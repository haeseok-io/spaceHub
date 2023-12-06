package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public interface JsonAction {
	public JSONObject execute (HttpServletRequest req, HttpServletResponse resp);
}
