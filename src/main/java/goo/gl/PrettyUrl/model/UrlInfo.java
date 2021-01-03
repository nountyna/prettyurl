package goo.gl.PrettyUrl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class UrlInfo {
    private final UUID id;
    private final String fullUrl;
    private final Timestamp createdDate;
    private final String alias;

    public UrlInfo(@JsonProperty("alias") String alias, @JsonProperty("fullUrl") String fullUrl) {
        this.id = UUID.randomUUID();
        this.fullUrl = fullUrl;
        this.alias = alias;
        this.createdDate = new Timestamp(new Date().getTime());
    }

    public String getAlias() {
        return alias;
    }

    public UUID getId() {
        return id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

}
