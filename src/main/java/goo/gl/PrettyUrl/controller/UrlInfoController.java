package goo.gl.PrettyUrl.controller;

import goo.gl.PrettyUrl.exception.APIRequestResponseException;
import goo.gl.PrettyUrl.model.UrlInfo;
import goo.gl.PrettyUrl.model.UrlParam;
import goo.gl.PrettyUrl.service.UrlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/urlInfo")
@RestController
public class UrlInfoController {
    private final UrlInfoService urlInfoService;

    @Autowired
    public UrlInfoController(UrlInfoService urlInfoService) {
        this.urlInfoService = urlInfoService;
    }

    @PostMapping
    public String GetShorten(@RequestBody UrlParam param) throws APIRequestResponseException {
        return urlInfoService.getShortenUrl(param);
    }


    @GetMapping
    public List<UrlInfo> getAllUrlInfos() {
        return urlInfoService.getAllUrlInfos();
    }

    @DeleteMapping()
    public int removeTimeOutUrls() {
        return urlInfoService.removeTimeOutUrls();
    }


}
