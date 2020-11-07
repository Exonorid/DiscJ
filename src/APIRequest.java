import com.roxstudio.utils.CUrl;

public class APIRequest {
	public static void main(String[] args) {
		if(args.length < 2) {
			System.err.println("You need 2 arguments (subpath and request type, plus data if the request is POST)");
			return;
		}
		CUrl request = new CUrl("https://discord.com/api" + args[0]).header("Authorization: Bot NzM4NDg3NDU4Nzk4NTAxOTA4.XyMoCg.9uiB01p46RKEWu207L3VRrULTzw");
		if(args[1].equalsIgnoreCase("GET"))
			request.opt("-X", "GET");
		else if(args[1].equalsIgnoreCase("POST")) {
			request.opt("-X", "POST");
			request.data(args[2]);
			request.header("Content-Type: application/json");
		}
		System.out.println(new String(request.exec()));
	}
}
