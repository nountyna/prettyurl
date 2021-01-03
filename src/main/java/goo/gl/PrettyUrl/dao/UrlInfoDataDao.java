package goo.gl.PrettyUrl.dao;

import goo.gl.PrettyUrl.exception.APIRequestResponseException;
import goo.gl.PrettyUrl.model.UrlInfo;
import goo.gl.PrettyUrl.model.UrlParam;
import goo.gl.PrettyUrl.utils.Helper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Repository("dataDao")
public class UrlInfoDataDao implements IUrlInfoDao {

    private static List<UrlInfo> urlInfoData = new ArrayList<UrlInfo>();

    @Value("${default.pretty.url.length}")
    private int defaultUrlLength;

    @Value("${default.timeout.in.minute}")
    private int defaulTimeOutInMinute;

    @Value("${server.url}")
    private String serverUrl;

    @Override
    public List<UrlInfo> getAllUrlInfos() {
        return urlInfoData;
    }

    public String getShortenUrl(UrlParam urlParam) {
        String alias = urlParam.getAlias();
        if (alias != null && !alias.isEmpty()) {
            String finalAlias = alias;
            boolean isExisting = urlInfoData.stream().filter(url -> url.getAlias().equals(finalAlias)).map(UrlInfo::getAlias).count() > 0;
            if (isExisting) {
                throw new APIRequestResponseException("The alias is existed.");
            }
        } else {
            alias = Helper.generateRandomString(defaultUrlLength);
        }

        String fullUrl = urlParam.getFullUrl();
        String finalAlias1 = alias;
        String shortenUrl = urlInfoData.stream()
                .filter(url -> url.getFullUrl().equals(fullUrl) && url.getAlias().equals(finalAlias1))
                .map(UrlInfo::getAlias).findFirst().orElse(null);
        if (shortenUrl == null) {
            insertUrlInfo(alias, fullUrl);
            return serverUrl + alias;
        }
        return serverUrl + shortenUrl;
    }

    @Override
    public int removeTimeOutUrls() {
        Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
        List<UrlInfo> deletingUrls = urlInfoData.stream()
                .filter(url -> Helper.addMinuteToDate(url.getCreatedDate(), defaulTimeOutInMinute).compareTo(currentTimeStamp) < 0 || Helper.addMinuteToDate(url.getCreatedDate(), defaulTimeOutInMinute).compareTo(currentTimeStamp) == 0)
                .collect(Collectors.toList());
        if (!deletingUrls.isEmpty()) {
            urlInfoData.removeAll(deletingUrls);
        }

        return 1;
    }

    @Override
    public String getFullUrlByAlias(String alias) {
        String fullUrl = urlInfoData.stream().filter(url -> url.getAlias().equals(alias)).map(UrlInfo :: getFullUrl).findFirst().orElse("");
        if (fullUrl.isEmpty()) {
            throw new APIRequestResponseException("Link is expired or doesn't exist");
        }
        return fullUrl;
    }

    private int insertUrlInfo(String alias, String fullUrl) {
        urlInfoData.add(new UrlInfo(alias, fullUrl));
        return 1;
    }
}
