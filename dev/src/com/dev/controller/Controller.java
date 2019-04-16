package com.dev.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public interface Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
}
