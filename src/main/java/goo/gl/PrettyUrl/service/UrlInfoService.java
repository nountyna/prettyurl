package goo.gl.PrettyUrl.service;

import goo.gl.PrettyUrl.dao.IUrlInfoDao;
import goo.gl.PrettyUrl.exception.APIRequestResponseException;
import goo.gl.PrettyUrl.model.UrlInfo;
import goo.gl.PrettyUrl.model.UrlParam;
import goo.gl.PrettyUrl.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class UrlInfoService {

    private final IUrlInfoDao urlInfoDao;
    @Autowired
    public UrlInfoService(@Qualifier("dataDao") IUrlInfoDao urlInfoDao1) {
        this.urlInfoDao = urlInfoDao1;
    }

//    public int insertUrlInfo(String fullUrl) {
//        return urlInfoDao.insertUrlInfo(fullUrl);
//    }
//
//    public int insertUrlInfo(String alias, String fullUrl) {
//        return urlInfoDao.insertUrlInfo(alias, fullUrl);
//    }

    public List<UrlInfo> getAllUrlInfos() {
        return urlInfoDao.getAllUrlInfos();
    }

    public String getShortenUrl(UrlParam urlParam) {
        boolean isUrlValid = Helper.isUrlValid(urlParam.getFullUrl());
        if(!isUrlValid && (urlParam.getFullUrl() == null || urlParam.getFullUrl().isEmpty())) {
            throw new APIRequestResponseException("Url is invalid or empty");
        }
        return urlInfoDao.getShortenUrl(urlParam);
    }

    public int removeTimeOutUrls() {
        return urlInfoDao.removeTimeOutUrls();
    }

    public void redirectToUrl(String alias, HttpServletResponse httpServletResponse) throws IOException {
        String fullUrl = urlInfoDao.getFullUrlByAlias(alias);
        httpServletResponse.sendRedirect(fullUrl);
    }
}
