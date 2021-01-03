package goo.gl.PrettyUrl.controller;

import goo.gl.PrettyUrl.service.UrlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/")
@RestController
public class IndexController {

    private final UrlInfoService urlInfoService;

    @Autowired
    public IndexController(UrlInfoService urlInfoService) {
        this.urlInfoService = urlInfoService;
    }


    @GetMapping(path = "/{alias}")
    public void index(@PathVariable("alias") String alias, HttpServletResponse httpServletResponse) throws IOException {
        urlInfoService.redirectToUrl(alias, httpServletResponse);
    }
}
