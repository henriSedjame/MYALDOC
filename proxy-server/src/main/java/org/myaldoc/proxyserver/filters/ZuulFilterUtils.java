package org.myaldoc.proxyserver.filters;

import org.apache.http.*;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ZuulFilterUtils {


  public static final String AUTHORIZATION_HEADER = "authorization";

  public static final String SERVICE_ID = "serviceId";

  public static final String ROUTE_FILTER_TYPE = "route";

  public static final String HOST = "x-forwarded-host";

  public static final String HTTP = "x-forwarded-proto";


  /**
   * PRIVATE METHODES
   **/

  public static InputStreamEntity getInputStreamEntity(HttpServletRequest request) {
    int contentLength = request.getContentLength();
    InputStream requestEntity = getRequestBody(request);
    ContentType contentType = Objects.nonNull(request.getContentType()) ? ContentType.create(request.getContentType()) : null;
    return new InputStreamEntity(requestEntity, contentLength, contentType);
  }

  public static HttpRequest getHttpRequest(String verb, String uri, HttpEntity entity) {

    HttpRequest request = null;

    switch (verb) {
      case "POST":
        request = new HttpPost(uri);
        ((HttpPost) request).setEntity(entity);
        break;
      case "PUT":
        request = new HttpPut(uri);
        ((HttpPut) request).setEntity(entity);
        break;
      case "PATCH":
        request = new HttpPatch(uri);
        ((HttpPatch) request).setEntity(entity);
        break;
      default:
        request = new BasicHttpRequest(verb, uri);
    }

    return request;
  }

  public static InputStream getRequestBody(HttpServletRequest request) {

    InputStream requestBody = null;
    try {
      requestBody = request.getInputStream();
    } catch (IOException e) {

    }

    return requestBody;
  }

  public static String getMethod(HttpServletRequest request) {
    return request.getMethod().toUpperCase();
  }

  public static String buildRoute(String oldEndPoint, String newEndpoint, String serviceName) {
    int index = oldEndPoint.indexOf(serviceName);
    String strippedRoute = oldEndPoint.substring(index + serviceName.length());
    return String.format("%s/%s", newEndpoint, strippedRoute);
  }

  public static void setResponse(HttpResponse httpResponse, ProxyRequestHelper proxyRequestHelper) throws IOException {
    proxyRequestHelper.setResponse(httpResponse.getStatusLine().getStatusCode(),
            null, revertHeaders(httpResponse.getAllHeaders()));
  }

  public static MultiValueMap<String, String> revertHeaders(Header[] headers) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    Arrays.stream(headers).forEach(header -> {
      String name = header.getName();
      if (!map.containsKey(name)) map.put(name, new ArrayList<>());
      map.get(name).add(header.getValue());
    });
    return map;
  }

  public static HttpResponse forwardRequest(CloseableHttpClient httpClient, HttpHost httpHost, HttpRequest httpRequest) throws IOException {
    return httpClient.execute(httpHost, httpRequest);
  }

  public static HttpHost getHost(URL url) {
    return new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
  }

  public static Header[] convertHeaders(MultiValueMap<String, String> headers) {
    List<Header> list = new ArrayList<>();

    headers.keySet().forEach(key -> {
      headers.get(key).forEach(value -> {
        list.add(new BasicHeader(key, value));
      });
    });

    return list.toArray(new BasicHeader[0]);
  }


}
