package example;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NestWrite {

   public Request makeRequest(String url, String value, String setparameter, String setvalue, String token) {

	   MediaType mediaType = MediaType.parse("application/octet-stream");
	   //initializing randomly which will be changed based on request
       RequestBody body = RequestBody.create(mediaType, "{\"target_temperature_f\": 67}\"");
       
       if (setparameter.equals("writescale")) {
           setvalue=setvalue.replaceAll("[0-9]", "");
           body = RequestBody.create(mediaType, "{\"temperature_scale\": \""+setvalue+"\"}\"");
       }
       else if (setparameter.equals("writetemp")) {
           setvalue=setvalue.replaceAll("[^0-9]", "");
           //if temp < 40 consider it centigrade
    	   if (Integer.parseInt(setvalue) > 40)
    		   body = RequestBody.create(mediaType, "{\"target_temperature_f\": "+setvalue+"}\"");
    	   else
    		   body = RequestBody.create(mediaType, "{\"target_temperature_c\": "+setvalue+"}\"");
	   }       
       else if (setparameter.equals("writemode"))
	   {
            body = RequestBody.create(mediaType, "{\"away\": \""+setvalue+"\"}\"");
	   }

       // String auth = "Bearer c.UoKKHt0lflgeDhvsPdSHKq6uEo3HnlmQHAgWXB8AES43BrzarHjbfByzWFV6rRMcTtx8rLcwejmRQCBtNiytkoplgtAUXFmXh3sgqoUjpCCDL7g1T8t9BvMgepRqyAehB25EoGnx17GO1paJ"; // Update with your token
       String auth = "Bearer "+token; // Update with your token

       Request request = new Request.Builder()
       .url(url)
       .put(body)
       .addHeader("authorization", auth)
       .build();
       return request;
   }
 		
   public static void writetempcall(String thermostatid,String setparameter, String setvalue, String token) throws IOException {
	   OkHttpClient client = new OkHttpClient();
       NestWrite postman = new NestWrite();
       
       // String url = "https://developer-api.nest.com/devices/thermostats/-DIZqoUxuE-85w8qrdrMp8Pc0NsgOLQg";
       String url = "https://developer-api.nest.com/devices/thermostats/"+thermostatid;
       if (setparameter.equals("writemode")){
       	  // url = "https://developer-api.nest.com/structures/mgLgOBnaieG6N8eN6tKE6zdWS9o1SD6MQ2iuZFrly5dQctIIee5yuw";
       	  //this is not the actual thermostat id. It is the device id which is sent as thermostatid name by  the manage program
    	  url = "https://developer-api.nest.com/structures/"+thermostatid;
       }

       String value = "{\"home\": \"away\"}";
       Request request = postman.makeRequest(url, value, setparameter,setvalue, token);
       Response response = client.newCall(request).execute();
       //System.out.println(response.toString()); // to see if the task is actually performed or not
      
       // Use the new URL in case of temporary redirect
       if(response.isRedirect()){
           String newUrl = response.header("Location");
           request = postman.makeRequest(newUrl, value,setparameter,setvalue, token);
           response = client.newCall(request).execute();
       }
       System.out.println(response.body().string());
   }

  
}