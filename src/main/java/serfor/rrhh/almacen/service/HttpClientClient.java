package serfor.rrhh.almacen.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public interface HttpClientClient {
    HttpResponse<String> generateResponse(String uri,List<String> headers, String url, Map<String, String> params, String body) throws IOException, InterruptedException;
    HttpResponse<String> generateResponseGet(String uri,List<String> headers, String url, Map<String, String> params) throws IOException, InterruptedException;

}
