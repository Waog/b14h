package com.b14h.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PreapprovalRequest;
import com.paypal.svcs.types.ap.PreapprovalResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

public class Preapproval extends HttpServlet {

	private static final long serialVersionUID = -5608710629269370689L;

	// private final DatastoreService datastore = DatastoreServiceFactory
	// .getDatastoreService();

	/**
	 * Get Preapproval redirect URL.
	 * 
	 * @param req
	 *            HttpServletRequest should contain the parent Paypal ID in
	 *            future versions.
	 * @param resp
	 *            HttpServletResponse contains redirect URL to paypal with the
	 *            preapproval key.
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// super.doGet(req, resp);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().write("Hello Preapproval!");

		Map<String, String> customConfigurationMap = new HashMap<String, String>();
		customConfigurationMap.put("mode", "sandbox"); // Load the map with all
														// mandatory parameters
		customConfigurationMap.put("acct1.UserName",
				"teambh_api1.gmx.de");
		customConfigurationMap.put("acct1.Password", "1402429072");
		customConfigurationMap.put("acct1.Signature", "AFcWxV21C7fd0v3bYYYRCpSSRl31AUsTqNigADQwIjFRV.1kjkOVHgdp");
		customConfigurationMap.put("acct1.AppId", "APP-80W284485P519543T"); // The Sandbox uses a global test App ID value that remains constant

		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(
				customConfigurationMap);

		RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");

		Date now = new Date();
        SimpleDateFormat sdfDestination = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'");
        String startingDate = sdfDestination.format(now);
        
		
		
		PreapprovalRequest preapprovalRequest = new PreapprovalRequest(
				requestEnvelope, "http://localhost:8080/html/canceledPreapproval/",
				"USD", "http://localhost:8080/html/succeededPreapproval/", startingDate);

		// String apiUserName = "randomApiUserName5489ht5njr89";

		PreapprovalResponse preapprovalResponse = null;
		try {
			preapprovalResponse = adaptivePaymentsService.preapproval(
					preapprovalRequest);
		} catch (SSLConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientActionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PreapprovalResponse debugPreApprovalResp = preapprovalResponse;
	}
}
