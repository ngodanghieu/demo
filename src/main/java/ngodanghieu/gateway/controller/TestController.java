package ngodanghieu.gateway.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import ngodanghieu.gateway.filter.FilterConstant;
import ngodanghieu.gateway.model.response.ResponseConstant;
import ngodanghieu.gateway.model.response.ResponseDTO;
import org.springframework.http.*;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class TestController {

    @SneakyThrows
    @RequestMapping(value = "/api/test" , method = RequestMethod.GET)
    public ResponseEntity<?> testResponse(HttpServletRequest request){
        String user_name = (String) request.getAttribute("user_name");
        HttpHeaders headers = new HttpHeaders();
        headers.add(FilterConstant.X_API_KEY,"ops-pay-key");
        headers.add(FilterConstant.SECRET,"ops-pay-secre");
        HttpEntity<Object> requestEntity = new HttpEntity<>( headers);
        String serverUrl = "http://192.168.137.203:8080/pay-maker-checker";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, String.class);

        }catch (HttpStatusCodeException ex){
        System.out.println(ex.getResponseBodyAsString());
            return new ResponseEntity<ResponseDTO>(ResponseConstant.responseError401(user_name ), HttpStatus.UNAUTHORIZED);
        }
//        URL obj = new URL("http://192.168.137.203:8080/pay-maker-checker");
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type",
//                "application/x-www-form-urlencoded");
//        con.setRequestProperty(FilterConstant.X_API_KEY,"ops-pay-key");
//        con.setRequestProperty(FilterConstant.SECRET,"ops-pay-secret");
//
//        con.setDoOutput(true);
//        OutputStream os = con.getOutputStream();
//        os.flush();
//        os.close();
//
//        if (con.getResponseCode() == HttpStatus.OK.value()){
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//            return new ResponseEntity<ResponseDTO>(ResponseConstant.responseOK(user_name, new Gson().fromJson(response.toString(),ResponseDTO.class)), HttpStatus.OK);
//        }else {
//
//            return new ResponseEntity<ResponseDTO>(ResponseConstant.responseError401(user_name ), HttpStatus.UNAUTHORIZED);
//        }
            return new ResponseEntity<ResponseDTO>(ResponseConstant.responseError401(user_name ), HttpStatus.UNAUTHORIZED);

    }


}
