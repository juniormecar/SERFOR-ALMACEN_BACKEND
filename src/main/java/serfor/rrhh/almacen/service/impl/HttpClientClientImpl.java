package serfor.rrhh.almacen.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import serfor.rrhh.almacen.service.HttpClientClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class HttpClientClientImpl   implements HttpClientClient {


    @Override
    public HttpResponse<String> generateResponse(String uri,List<String> headers, String url, Map<String, String> params, String body) throws IOException, InterruptedException {
        url = uri+url;
        log.info("url {} ",url);
        HttpClient client = HttpClient.newBuilder().build();
        MultiValueMap<String, String> parameters = generateParameters(params);
        return client.send(
                generateMethodPost(
                        headers,
                        generateUri(url, parameters),
                        body
                ),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    @Override
    public HttpResponse<String> generateResponseGet(String uri,List<String> headers, String url, Map<String, String> params) throws IOException, InterruptedException {
        url = uri+url;
        HttpClient client = HttpClient.newBuilder().build();
        MultiValueMap<String, String> parameters = generateParameters(params);
        return client.send(
                generateMethodGet(
                        headers,
                        generateUri(url, parameters)
                ),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private String generateUri(String uri, MultiValueMap<String, String> queryParams) {
        return UriComponentsBuilder
                .fromUriString(uri)
                .queryParams(queryParams)
                .build().toUriString();
    }

    private MultiValueMap<String, String> generateParameters(Map<String, String> params) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        params.forEach((k, v) -> queryParams.add(k, v));
        return queryParams;
    }

    private HttpRequest generateMethodPost(List<String> headers, String urlApi, String body ) {

        headers.add("Content-Type");
        headers.add("application/json");


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlApi))
                .headers(headers.toArray(String[]::new))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return request;
    }

    private HttpRequest generateMethodGet(List<String> headers, String urlApi ) {

        headers.add("Content-Type");
        headers.add("application/json");


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlApi))
                .headers(headers.toArray(String[]::new))
                .GET()
                .build();

        return request;
    }

}
