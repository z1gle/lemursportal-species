/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wcs.lemurs.util.LinkPreviewService;

/**
 *
 * @author Zacharie
 */
@RestController
public class LinkPreviewController {

    @Autowired(required = true)
    private LinkPreviewService linkPreviewService;

    @CrossOrigin
    @RequestMapping(value = "/preview", method = RequestMethod.GET, headers = "Accept=application/json")
    public Map<String, String> getLinkPreview(@RequestParam String link) throws IOException {
        Map<String, String> valiny = new HashMap<>();
        String url = link;

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", "Mozilla/5.0");

        HttpResponse response = (HttpResponse) client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        while ((line = rd.readLine()) != null) {
            List<String> tags = linkPreviewService.getAsSingleTag(line);
            for (String tag : tags) {
                if (tag.contains("property=\"og:title\"")) {
                    int debut = tag.indexOf("content=\"") + 9;
                    valiny.put("ogTitle", tag.substring(debut, tag.indexOf("\"", debut)));
                    continue;
                }
                if (tag.contains("property=\"og:image\"")) {
                    int debut = tag.indexOf("content=\"") + 9;
                    valiny.put("ogImg", tag.substring(debut, tag.indexOf("\"", debut)));
                    continue;
                }
                if (tag.contains("property=\"og:description\"")) {
                    int debut = tag.indexOf("content=\"") + 9;
                    valiny.put("ogDescription", tag.substring(debut, tag.indexOf("\"", debut)));
                    continue;
                }
                if (tag.contains("meta") && tag.contains("name=\"description\"")) {
                    int debut = tag.indexOf("content=\"") + 9;
                    valiny.put("description", tag.substring(debut, tag.indexOf("\"", debut)));
                    continue;
                }
                if (tag.contains("/title")) {
                    valiny.put("title", tag.substring(0, tag.lastIndexOf("<")));
                    continue;
                }
                if (tag.contains("link") && tag.contains("icon") && !valiny.containsKey("icon")) {
                    int debut = tag.indexOf("href=\"") + 7;
                    valiny.put("icon", tag.substring(debut, tag.indexOf("\"", debut)));
                    if (!valiny.get("icon").contains("http")) {
                        valiny.put("icon", url + "/" + valiny.get("icon"));
                    }
                }
            }
        }
        return valiny;
    }
}
