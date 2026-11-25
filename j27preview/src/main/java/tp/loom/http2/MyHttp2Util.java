package tp.loom.http2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.type.CollectionType;

public class MyHttp2Util {
	public static MyHttp2Util INSTANCE = new MyHttp2Util();
	
	private HttpClient client = HttpClient.newHttpClient();
	JsonMapper jsonMapper = JsonMapper.builder()
			.enable(SerializationFeature.INDENT_OUTPUT)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.build();
	
	
	public MyHttp2Util() {
		super();
	}
	
	public String fetchAsJsonString(String url){
		String jsonString=null;
		try {
			HttpRequest req =
					HttpRequest.newBuilder(URI.create(url))
					.header("User-Agent","Java")
					.GET()
					.build();
			HttpResponse<String> resp = 
					client.send(req, BodyHandlers.ofString());
			if(resp.statusCode()==200) {
				jsonString=resp.body();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}


	public <T> T fetch(String url,Class<T> dataClass){
		T result=null;
		try {
			String jsonString=fetchAsJsonString(url);
			if(jsonString!=null)
			    result=jsonMapper.readValue(jsonString, dataClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public <T> List<T> fetchList(String url,Class<T> dataClass){
		List<T> result=null;
		try {
			String jsonString=fetchAsJsonString(url);
			 CollectionType javaType = jsonMapper.getTypeFactory()
				      .constructCollectionType(List.class, dataClass);
			if(jsonString!=null)
			    result=jsonMapper.readValue(jsonString, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
