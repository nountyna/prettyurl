package goo.gl.PrettyUrl;

import java.net.URI;
import java.net.URISyntaxException;
import goo.gl.PrettyUrl.model.UrlInfo;
import goo.gl.PrettyUrl.utils.Helper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrettyUrlApiTests {
    @Value("${server.url}")
    private String serverUrl;

    @Test
    public void testGenerateUrlWithoutAlias() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = serverUrl + "urlInfo";
        URI uri = new URI(baseUrl);
        UrlInfo urlInfo = new UrlInfo(null, "https://www.google.com");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<UrlInfo> request = new HttpEntity<>(urlInfo, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains(serverUrl));
    }

    @Test
    public void testGenerateUrlWithAlias() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = serverUrl + "urlInfo";
        URI uri = new URI(baseUrl);
        String alias = Helper.generateRandomString(10);// have to generate randomly otherwise will throw exception when has the same alias
        UrlInfo urlInfo = new UrlInfo(alias, "https://www.google.com");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<UrlInfo> request = new HttpEntity<>(urlInfo, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains(serverUrl + alias));

    }

    @Test
    public void testGenerateUrlWithSameAlias() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = serverUrl + "urlInfo";
        URI uri = new URI(baseUrl);
        String alias = "testing";
        UrlInfo urlInfo = new UrlInfo(alias, "https://www.google.com");

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<UrlInfo> request = new HttpEntity<>(urlInfo, headers);
            restTemplate.postForEntity(uri, request, String.class);

            UrlInfo seccondUrlInfo = new UrlInfo(alias, "https://stackoverflow.com/");
            HttpEntity<UrlInfo> secondRequest = new HttpEntity<>(seccondUrlInfo, headers);
            restTemplate.postForEntity(uri, secondRequest, String.class);

        } catch (Exception ex) {
            Assert.assertEquals(true, ex.getMessage().contains("The alias is existed."));
        }
    }

    @Test
    public void testBadUrl() throws URISyntaxException {

            RestTemplate restTemplate = new RestTemplate();
            final String baseUrl = serverUrl + "urlInfo";
            URI uri = new URI(baseUrl);
            String alias = null;
            UrlInfo urlInfo = new UrlInfo(alias, "httpts://www.gle.com");

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<UrlInfo> request = new HttpEntity<>(urlInfo, headers);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        } catch (Exception ex) {
            Assert.assertEquals(true, ex.getMessage().contains("Url is invalid"));
        }
    }

    @Test
    public void testRedirectToFullUrl() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = serverUrl + "urlInfo";
        URI uri = new URI(baseUrl);
        String alias = Helper.generateRandomString(10);
        UrlInfo urlInfo = new UrlInfo(alias, "https://stackoverflow.com/");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UrlInfo> request = new HttpEntity<>(urlInfo, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        // redirect to full url
        HttpHeaders redirectHeaders = new HttpHeaders();
        URI serverUri = new URI(serverUrl + alias);
        HttpEntity<UrlInfo> requestEntity = new HttpEntity<>(null, redirectHeaders);
        ResponseEntity<String> redirectResult = restTemplate.exchange(serverUri, HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(302, redirectResult.getStatusCodeValue());
    }
}
