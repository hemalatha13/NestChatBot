package example;

import java.io.IOException;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class manage {
	public static String request(String requestType, String thermostatName,String value, String token) throws ParseException, IOException{
		String deviceid;
		String temperaturescale=null;
		String readtemp=null;
		String response=null;
		String readdata=null;

		readdata = ReadNest.readcall(token);
		if(readdata==null){return "unable to read data with the access token. will come back to you";}
		
		//getting individual parameters from the read data of the nest
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(readdata);
		JSONObject devices = (JSONObject) jsonObject.get("devices");
		JSONObject structures = (JSONObject) jsonObject.get("structures");
		JSONObject devices_thermostats = (JSONObject) devices.get("thermostats");
		Set<String> structures_key = structures.keySet();
		Object[] structure_id=null;
		structure_id=(structures_key.toArray());
		JSONObject structures_structureid = (JSONObject) structures.get(structure_id[0]);
		JSONArray thermostatslist = (JSONArray) structures_structureid.get("thermostats");
		
		//find the thermostat if for the thermostat name and perform the task based on the request type
		for (int i = 0; i < thermostatslist.size(); i++) {  
			JSONObject devices_thermostats_deviceid = (JSONObject) devices_thermostats.get(thermostatslist.get(i));
			if (devices_thermostats_deviceid.get("name").toString().equalsIgnoreCase(thermostatName)){ //outer if
				if(requestType.equals("writetemp")||requestType.equals("writescale")){
					deviceid=thermostatslist.get(i).toString();
					try{
						NestWrite.writetempcall(deviceid,requestType,value,token); }
					catch (IOException e2) {
						response="error writing";
						e2.printStackTrace();}
					response="Set to "+value;
					break;
				}
				if(requestType.equals("readscale")){
					temperaturescale=devices_thermostats_deviceid.get("temperature_scale").toString().toLowerCase();
						response= "your "+thermostatName+" temperature scale  is set to "+temperaturescale;
					break;
				}
				if(requestType.equals("readtemp")){
					temperaturescale=devices_thermostats_deviceid.get("temperature_scale").toString().toLowerCase();
					readtemp=devices_thermostats_deviceid.get("target_temperature_"+temperaturescale).toString();
					response= " your "+thermostatName+" temperature is set to "+readtemp+" "+temperaturescale;	
					break;	
				}
			} //outerif close
		
			if(requestType.equals("readmode")){
				String mode=null;
				mode=structures_structureid.get("away").toString();
				response= "your nest is on "+mode+" mode";
				break;
			}
			if(requestType.equals("writemode")){
				System.out.println(value);
				try{
					NestWrite.writetempcall(structure_id[0].toString(), "writemode", value,token);}
				catch (IOException e2) {
					response="error writing";
					e2.printStackTrace();}
				response= "your mode is now  set to "+value+" mode";
				break;
			}	
		} //for loop close
	

	
	return response;
	}

}
