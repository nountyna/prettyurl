package goo.gl.PrettyUrl.model;

import javax.validation.constraints.NotBlank;

public class UrlParam {
    private String alias;

    @NotBlank()
    private String fullUrl;

    public UrlParam(String alias, String fullUrl) {
        this.alias = alias;
        this.fullUrl = fullUrl;
    }

    public String getAlias() {
        return alias;
    }

    public String getFullUrl() {
        return fullUrl;
    }
}
