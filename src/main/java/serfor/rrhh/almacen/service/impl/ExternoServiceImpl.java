package serfor.rrhh.almacen.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.UbigeoRepository;
import serfor.rrhh.almacen.service.ExternoService;
import serfor.rrhh.almacen.service.HttpClientClient;
import serfor.rrhh.almacen.service.UbigeoService;
import org.springframework.beans.factory.annotation.Value;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
@Log4j2
@Service
public class ExternoServiceImpl implements ExternoService {

    @Value("${login.api.url}")
    private String url;

    @Autowired
    private HttpClientClient httpClientClient;

    private final Gson gson = new Gson();

    @Override
    public ResponseLogin getLogin(RequestLogin param) throws Exception {

        HttpResponse<String> response = null;
        ResponseLogin responseLogin = null;
        List<String> headers = new ArrayList<>();
        headers.add("Access-Control-Allow-Headers");
        headers.add("X-Custom-Header");
        Gson gsonUtil = new Gson();
        String bodyRes = gsonUtil.toJson(param);
        try {

            response = httpClientClient.generateResponse(
                    url,
                    headers,
                    "/auth/login",
                    new HashMap<>(),
                    bodyRes);

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                responseLogin = gson.fromJson(response.body(), ResponseLogin.class);
            }
            log.info("Response CursaLab users: " + response.body());
        } catch (Exception e) {
            log.error("Exception CursaLab users: " + e.getMessage(), e);
        }

        return responseLogin;
    }

}
