package goo.gl.PrettyUrl;

import java.net.URI;
import java.net.URISyntaxException;

import goo.gl.PrettyUrl.dao.IUrlInfoDao;
import goo.gl.PrettyUrl.model.UrlInfo;
import goo.gl.PrettyUrl.model.UrlParam;
import goo.gl.PrettyUrl.service.UrlInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrettyUrlServicesTests {

	@Autowired
	private UrlInfoService urlService;
	@MockBean
	private IUrlInfoDao urlInfoDao;

	@Test
	public void testGenerateUrlWithoutAlias() {
		UrlParam urlParam = new UrlParam("", "https://www.google.com/");
		when(urlInfoDao.getShortenUrl(urlParam)).thenReturn("localhost:8080/48s8df8fifi");
		String shortUrl = urlService.getShortenUrl(urlParam);
		Assert.assertEquals("localhost:8080/48s8df8fifi", shortUrl);
	}

	@Test
	public void testGenerateUrlWithinAlias() {
		UrlParam urlParam = new UrlParam("prettyurl", "https://www.google.com/");
		when(urlInfoDao.getShortenUrl(urlParam)).thenReturn("localhost:8080/prettyurl");
		String shortUrl = urlService.getShortenUrl(urlParam);
		Assert.assertEquals("localhost:8080/prettyurl", shortUrl);
	}
}
