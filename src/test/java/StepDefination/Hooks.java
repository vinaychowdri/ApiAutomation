package StepDefination;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException 
	{
		StepDefination m =new StepDefination();
		if(m.place_id==null)
		{		
		m.add_Place_Payload_with("vinay", "kannada", "Asia");
		m.user_calls_with_http_request("AddPlaceAPI", "POST");
		m.verify_Place_id_created_maps_to_using("vinay", "getPlaceAPI");
		}
	}
	
}
