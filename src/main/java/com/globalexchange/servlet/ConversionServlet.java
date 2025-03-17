package com.globalexchange.servlet;

import com.globalexchange.model.CurrencyConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConversionServlet extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(ConversionServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String fromCurrency = request.getParameter("from");
            String toCurrency = request.getParameter("to");
            String amountStr = request.getParameter("amount");

            LOGGER.info("Received request - From: " + fromCurrency + ", To: " + toCurrency + ", Amount: " + amountStr);

            if (fromCurrency == null || toCurrency == null || amountStr == null) {
                throw new IllegalArgumentException("Missing input parameters");
            }

            double amount = Double.parseDouble(amountStr);

            if (amount < 0) {
                throw new IllegalArgumentException("Amount must be non-negative");
            }

            double result = CurrencyConverter.convert(fromCurrency, toCurrency, amount);

            LOGGER.info("Conversion result: " + result);

            out.println("{\"success\": true, \"result\": " + result + "}");

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid amount format", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"success\": false, \"error\": \"Invalid amount format\"}");
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Invalid input", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during conversion", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"success\": false, \"error\": \"An error occurred during conversion\"}");
        }
    }
}