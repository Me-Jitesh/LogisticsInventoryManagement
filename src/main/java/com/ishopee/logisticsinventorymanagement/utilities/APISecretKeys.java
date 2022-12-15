package com.ishopee.logisticsinventorymanagement.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class APISecretKeys {
    // Replace The API Key, Get From https://www.ip2location.io/#ip2locationlite
    @Value("${api.secret.keys.ip2location}")
    private String IP2LOCATION_KEY;

    public String getIP2LocationKey() {
        return IP2LOCATION_KEY;
    }
}
