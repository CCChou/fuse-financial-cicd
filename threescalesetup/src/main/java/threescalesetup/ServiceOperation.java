package threescalesetup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import threescalesetup.dto.Service;
import threescalesetup.dto.Threescale;

public class ServiceOperation{
	
	static final String SERVICE_URL = "admin/api/services";
	String apiurl = "";
	ThreescaleManagment threescalemanagement;
	
	public ServiceOperation(String hostName){
		apiurl = ServiceOperation.SERVICE_URL;
		threescalemanagement = new ThreescaleManagment(hostName);
	}
	
	
	public String create(String accessToken, String name, String servicename, String description, boolean isSelfmanage){
		String url = apiurl+".json";
		String serviceid = null;
		Service service = new Service(accessToken, name);
		service.setDescription(description);
		service.setServicename(servicename);
		service.isSelfManaged(isSelfmanage);
		
		try {
			String response = threescalemanagement.sendPost(url, service.getAllParam());
		
			System.out.println(response);
			
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode serviceNode = rootNode.path("service");
			serviceid = serviceNode.get("id").asText();
			System.out.println("Service Created with id:["+serviceid+"]");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceid;
	}
	
	public void delete(String accessToken, String serviceid){
		String url = apiurl+"/"+serviceid+".json";
		Threescale service = new Threescale();
		service.setAccessToken(accessToken);
		
		try {
			threescalemanagement.sendDelete(url,service.getAllParam());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
