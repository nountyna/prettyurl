package goo.gl.PrettyUrl.dao;

import goo.gl.PrettyUrl.model.UrlInfo;
import goo.gl.PrettyUrl.model.UrlParam;

import java.util.List;

public interface IUrlInfoDao {

//    int insertUrlInfo(String alias, String fullUrl);
//    default int insertUrlInfo(String fullUrl) {
//        return insertUrlInfo("", fullUrl);
//    }

    List<UrlInfo> getAllUrlInfos();
    //String getShortenUrl(String fullUrl);
    String getShortenUrl(UrlParam urlParam);

    int removeTimeOutUrls();

    String getFullUrlByAlias(String alias);
}
