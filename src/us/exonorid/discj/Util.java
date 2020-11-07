package us.exonorid.discj;

import com.roxstudio.utils.CUrl;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class Util {
	static class HTTPResponse {
		private String response;
		private int code;
		
		public HTTPResponse(String response, int code) {
			this.response = response;
			this.code = code;
		}
		
		public String getResponse() {
			return response;
		}
		
		public int getCode() {
			return code;
		}
	}
	
	//Sends a GET request (this is here so we can get the correct gateway address)
	public static HTTPResponse get(String path, String token) {
		CUrl request = new CUrl("https://discord.com/api" + path).header("Authorization: Bot " + token).header("Content-Type: application/json");
		String response = new String(request.exec());
		return new HTTPResponse(new String(request.exec()), request.getHttpCode());
	}
	
	//Get the gateway URL for connecting
	public static URI getGateway(String token) {
		HTTPResponse response = Util.get("/gateway/bot", token);
		if(response.getCode() != 200) {
			System.err.println("Could not get gateway address.");
			System.err.println(response.getResponse());
		} else {
			JSONObject responseJSON = new JSONObject(response.getResponse());
			try {
				return new URI(responseJSON.getString("url"));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
