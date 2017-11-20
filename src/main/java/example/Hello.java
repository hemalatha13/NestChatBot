package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import example.ResponseClass;
import example.RequestClass;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import org.json.simple.parser.ParseException;

public class Hello implements RequestHandler<RequestClass, ResponseClass>{ 
	//Variable to store id's and keys
	static	Hashtable<String, String> source = new Hashtable<String,String>();
	static HashMap<String, String> map = new HashMap(source);

	static{
		map.put("1499176146821169", "c.UoKKHt0lflgeDhvsPdSHKq6uEo3HnlmQHAgWXB8AES43BrzarHjbfByzWFV6rRMcTtx8rLcwejmRQCBtNiytkoplgtAUXFmXh3sgqoUjpCCDL7g1T8t9BvMgepRqyAehB25EoGnx17GO1paJ");
			}
	
	//entry
	public ResponseClass handleRequest(RequestClass request, Context context){

    	String response="";
		String fbid=request.originalRequest.data.sender.id;
		String token=getAccess.fetchToken(fbid);
		
		//Check if user id and token are stored or not. If he is a new user he has to register and send the pin which triggers storeKey Intent
		if(request.result.metadata.intentName.equals("Welcome")){
			if(token!=null)
				response="hi existing user";
			else 
				response="hi new user, Please go to https://home.nest.com/login/oauth2?client_id=b0499a75-e963-4745-aaf1-d04c206b05c4&state=STATE and let me know the pin";
			System.out.println(request.originalRequest.data.sender.id);
		return new ResponseClass(response);
		}
		
		
		
		switch(request.result.metadata.intentName){
			//get token and store it with its fbid
			case "storeKey":try {
								getAccess.storeToken(fbid, getAccess.getAccessToken(request.result.parameters.pin));
								response="you can now control your thermostat";
							} 
							catch (IOException | ParseException e2) {
								response="error";
								e2.printStackTrace();
							}
							break;
			//read\write temp,scale,mode	pass on token for accessing nest corresponding to token
			case "read":try {
							if(request.result.parameters.readparameter.contains("temperature")){
								response=	manage.request("readtemp", request.result.parameters.thermostats, null,token);
								response=response+" ";}
							else if(request.result.parameters.readparameter.contains("scale"))
								response=	manage.request("readscale", request.result.parameters.thermostats, null,token);
							else if(request.result.parameters.readparameter.contains("mode"))
								response=	manage.request("readmode", request.result.parameters.thermostats, null,token);
							} 
							catch (IOException | ParseException e2) {
								response="error while reading";
								e2.printStackTrace();
							}		
						break;
			case "setTemp":try{			
								response=manage.request("writetemp", request.result.parameters.thermostats, request.result.parameters.temperature,token);
						   } 
						   catch (IOException | ParseException e2) {
							   response="error while setting temperature";
							   e2.printStackTrace();
						   }		
						   break;
			case "setMode":try {
								response= manage.request("writemode",request.result.parameters.thermostats, request.result.parameters.mode,token);
							} 
							catch (ParseException | IOException e) {
								response="error while setting mode";
								e.printStackTrace();
							}
							break;
			case "setScale":try {
								response=manage.request("writescale", request.result.parameters.thermostats, request.result.parameters.scaleValue,token);
							}
							catch (ParseException | IOException e) {
								   response="error while setting scale";
								   e.printStackTrace();
							}
							break;
							
		} //switch close
    	
    	return new ResponseClass(response);
    } //Handler close
    
    
   


} //class close

