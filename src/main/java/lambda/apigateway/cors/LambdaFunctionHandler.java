package lambda.apigateway.cors;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.model.GetSubscriptionAttributesRequest;

public class LambdaFunctionHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {
	@SuppressWarnings({ "unchecked", "restriction" })
	@Override
	public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String[] arr = {"https://your domain name.com"
							 };
			context.getLogger().log("Input: " + input);
			String localhost = "localhost";
			JSONObject jsonobj = new JSONObject(input);
			Map<String, Object> Headers = new HashMap<String, Object>();
			String str = (String) ((Map) jsonobj.get("headers")).get("origin");
			String body = (String) ((Map) jsonobj.get("headers")).get("body");
			String verifiedOrgin = "";
			for (int i = 0; i < arr.length; i++) {
				context.getLogger().log("str: " + str + "   arr[i]:" + arr[i]);
				if (arr[i].equals(str)) {
					verifiedOrgin = arr[i];
					break;
				}
				else if(str.contains(localhost)){
					verifiedOrgin = str;
					break;
				}
			}
			if (verifiedOrgin != "") {
				response.put("statusCode", 200);
				Headers.put("Access-Control-Allow-Origin", verifiedOrgin);
				Headers.put("Access-Control-Allow-Headers",
						"Content-Type,Access-Control-Allow-Origin,access-control-allow-origin,access-control-expose-headers,authorization,access-control-allow-credentials,access-control-allow-methods,cache-control,Accept,authorizationToken,Accept-Encoding,Content-Length,Connection,User-Agent");
				Headers.put("Access-Control-Allow-Credentials", "true");
				Headers.put("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
				response.put("headers", Headers);
				response.put("body", body);
				context.getLogger().log("Response: " + response);
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}
}
