//read the value of nest
package example;


import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Authenticator;
import okhttp3.Route;
 

public class ReadNest {
	public static String readcall(String token) throws IOException{
		String a="";
		//final String auth = "Bearer c.UoKKHt0lflgeDhvsPdSHKq6uEo3HnlmQHAgWXB8AES43BrzarHjbfByzWFV6rRMcTtx8rLcwejmRQCBtNiytkoplgtAUXFmXh3sgqoUjpCCDL7g1T8t9BvMgepRqyAehB25EoGnx17GO1paJ"; // Update with your token
		final String auth = "Bearer "+token; // Update with your token

        OkHttpClient client = new OkHttpClient.Builder()
        .authenticator(new Authenticator() {
        	public Request authenticate(Route route, Response response) throws IOException {
            return response.request().newBuilder()
                .header("Authorization", auth)
                .build();
          }
        })
        .followRedirects(true)
        .followSslRedirects(true)
        .build();
 
        Request request = new Request.Builder()
        .url("https://developer-api.nest.com")
        .get()
        .addHeader("content-type", "application/json; charset=UTF-8")
        .addHeader("authorization", auth)
        .build();
        
        try { 
            System.out.println("Begin request:  ");
            Response response = client.newCall(request).execute();
            // System.out.println(response.body().string()); //the read data (json)
            a=response.body().string();
            System.out.println(a);
            System.out.println("End request");
            System.out.println();
            Thread.sleep(2 * 1000);                    
        } 
        catch (InterruptedException e) {
             e.printStackTrace();
        }
		return a;
	}
   
}



 