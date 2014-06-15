package com.b14h.controllers;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.RequestEnvelope;

public class Pay extends HttpServlet {

	private static final long serialVersionUID = 2187633796363073807L;

	private final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	/**
	 * TODO: use put requests instead of get requests. triggers a payment from a
	 * static parent paypal account to a static store payment account.
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write("hello payment!");
		
		RequestEnvelope env = new RequestEnvelope();
		env.setErrorLanguage("en_US");

		List<Receiver> receiver = new ArrayList<Receiver>();
		Receiver rec = new Receiver();
		rec.setAmount(2.0);
		rec.setEmail("teambh.store@gmx.de");
		receiver.add(rec);
		ReceiverList receiverlst = new ReceiverList(receiver);

        Query q = new Query("preapprovalKey");
        List<Entity> preapprovalKeys = datastore.prepare(q).asList(withLimit(50));
        Entity entity = preapprovalKeys.get(0);
        String preapprovalKey = (String) entity.getProperty("key");
		
		PayRequest payRequest = new PayRequest();
		payRequest.setActionType("PAY");
		payRequest.setReceiverList(receiverlst);
		payRequest.setCurrencyCode("EUR");
		payRequest.setCancelUrl("http://www.notUsedButRequiredUrl.de/");
		payRequest.setReturnUrl("http://www.notUsedButRequiredUrl.de/");
		payRequest.setRequestEnvelope(env);
//		payRequest.setSenderEmail("teambh.parent@gmx.de");
		payRequest.setPreapprovalKey(preapprovalKey);

		Map<String, String> customConfigurationMap = new HashMap<String, String>();
		customConfigurationMap.put("mode", "sandbox"); // Load the map with all mandatory parameters
		customConfigurationMap.put("acct1.UserName", "teambh_api1.gmx.de");
		customConfigurationMap.put("acct1.Password", "1402429072");
		customConfigurationMap.put("acct1.Signature",
				"AFcWxV21C7fd0v3bYYYRCpSSRl31AUsTqNigADQwIjFRV.1kjkOVHgdp");
		// The Sandbox uses a global test App ID value that remains constant
		customConfigurationMap.put("acct1.AppId", "APP-80W284485P519543T");
		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(customConfigurationMap);
		
		PayResponse payResponse = null;
		try {
			payResponse = adaptivePaymentsService.pay(payRequest);
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
		
		resp.getWriter().write("payment executed!");
	}

}
