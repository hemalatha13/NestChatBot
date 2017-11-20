package example;
  
import static example.Hello.*;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import okhttp3.OkHttpClient;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;
 
public class getAccess {
	
	public static String fetchToken(String id){
		Iterator<String> keySetIterator = map.keySet().iterator(); 
		while(keySetIterator.hasNext()){ 
			String key = keySetIterator.next(); 
			if(key.equals(id))
				return map.get(key);
		}
		return null;
	}
	
	public static void storeToken(String id,String token){
		map.put(id, token);
	}
  		
    //get token using the PIN sent by the user
	public static String getAccessToken(String pin) throws IOException, ParseException {
        String output=null;
        String token=null;
    	OkHttpClient client = new OkHttpClient();
        

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        //RequestBody body = RequestBody.create(mediaType, "code=KXPLAEXU&client_id=b0499a75-e963-4745-aaf1-d04c206b05c4&client_secret=NtiFdghcd1zPqKYdbtmuZmHfa&grant_type=authorization_code");
        //adding pin here
        RequestBody body = RequestBody.create(mediaType, "code="+pin+"&client_id=b0499a75-e963-4745-aaf1-d04c206b05c4&client_secret=NtiFdghcd1zPqKYdbtmuZmHfa&grant_type=authorization_code");

        Request request = new Request.Builder()
          .url("https://api.home.nest.com/oauth2/access_token")
          .post(body)
          .build();

        Response response = client.newCall(request).execute();
        //the generated access token by nest
        output=response.body().string();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(output);
		token=jsonObject.get("access_token").toString();
		return token;

    }
}