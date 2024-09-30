package com.dexbackend.dexbackend.pageanalyzer.utilities;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebCrawler {
    ArrayList<String> visited = new ArrayList<String>();
   Map<String, String> responseMap = new HashMap<>();
   String globalBaseUrl;

    public Map<String, String> startCrawling(String url) {
        trustAllCertificates();
        globalBaseUrl = url;
        try {
            clearDS();
            crawl(1, url, visited);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseMap;
    }

    private void clearDS() {
        visited.clear();
        responseMap.clear();
    }

    private  Document request(String url, ArrayList<String> visitedUrl){
        try{
            Connection connection = Jsoup.connect(url);
            Document doc = connection.get();

            if(connection.response().statusCode() == 200 && url.contains(globalBaseUrl)){
                visitedUrl.add(url);
                responseMap.put(url, doc.title());
                return doc;
            }
            return (Document) doc.text("Not accessible");
        }
        catch(IOException e){
            return null;
        }
    }

    private void crawl(int level, String url, ArrayList<String> visited){
        if(level <= 5){
            Document doc = request(url, visited);

            if(doc != null){
                for(Element link: doc.select("a[href]")){
                    String next_link = link.absUrl("href");
                    if(visited.contains(next_link) ==  false){
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }

    private  void trustAllCertificates() {
        try {
            // Create a trust manager that trusts all certificates
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
