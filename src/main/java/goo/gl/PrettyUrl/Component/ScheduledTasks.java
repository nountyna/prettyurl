package goo.gl.PrettyUrl.Component;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import java.net.URI;


import java.net.URI;
import java.net.URISyntaxException;

import goo.gl.PrettyUrl.model.UrlInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${server.url}")
    private String serverUrl;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = serverUrl + "urlInfo";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UrlInfo> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, requestEntity, String.class);

        log.info("Status", result.getStatusCodeValue());
    }
}
