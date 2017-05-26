package threescalesetup.operation.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import threescalesetup.dto.api.ApplicationPlan;
import threescalesetup.dto.api.Threescale;
import threescalesetup.operation.ThreescaleManagment;

public class ApplicationPlanOperation {
	Logger log = Logger.getLogger(ApplicationPlanOperation.class.getName());
	
	String SERVICE_URL = "admin/api/services/";
	String apiurl = "";
	ThreescaleManagment threescalemanagement;
	
	public ApplicationPlanOperation(String hostName, String serviceid){
		apiurl = SERVICE_URL+serviceid+"/application_plans";
		threescalemanagement = new ThreescaleManagment(hostName);
	}
	
	
	public String create(String accessToken, String name){
		String url = apiurl+".json";
		String applicationPlanid = null;
		
		ApplicationPlan applicationplan = new ApplicationPlan();
		applicationplan.setAccessToken(accessToken);
		applicationplan.setName(name);
		
		try {
			String response = threescalemanagement.sendPost(url, applicationplan.getAllParam());
		
			//System.out.println(response);
			log.fine(response);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode serviceNode = rootNode.path("application_plan");
			applicationPlanid = serviceNode.get("id").asText();
			//System.out.println("Application plan Created with id:["+applicationPlanid+"]");
			log.log(Level.FINE, "Application plan created. Id:[{0}]", applicationPlanid);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicationPlanid;
	}
	
	public void delete(String accessToken, String applicationplanid){
		String url = apiurl+"/"+applicationplanid+".json";
		Threescale applicationPlan = new Threescale();
		applicationPlan.setAccessToken(accessToken);
		
		try {
			threescalemanagement.sendDelete(url,applicationPlan.getAllParam());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
