package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {
	
	public AddPlace addPlacePayload(String name, String language, String address) {
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe-type");
		myList.add("shoe-type");
		myList.add("shoe-size");
		
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress(address);
		p.setLanguage(language);
		p.setPhone_number("76534423315");
		p.setWebsite("https://www.example.com");
		p.setName(name);
		p.setTypes(myList);
		p.setLocation(loc);
		
		return p;
		
	}
	
	public String deletePlacePayload(String placeID) {
		return "{\r\n\"place_id\":\""+placeID+"\"\r\n}";
	}

}
