package org.myaldoc.proxyserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.*;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.myaldoc.core.http.CustomHttpServletRequest;
import org.myaldoc.proxyserver.microservices.services.MicroServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.stereotype.Component;
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

@Component
public class CustomZuulFilter extends ZuulFilter {

  private ProxyRequestHelper proxyRequestHelper = new ProxyRequestHelper();

  @Autowired
  private MicroServicesService microServicesService;

  @Override
  public String filterType() {
    return ZuulFilterUtils.ROUTE_FILTER_TYPE;
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {

    /** Récupérer le context Zuul **/
    RequestContext context = RequestContext.getCurrentContext();

    final String serviceName = context.get(ZuulFilterUtils.SERVICE_ID).toString();

    /** Récupérer la requête **/
    HttpServletRequest request = context.getRequest();

    String http = context.getZuulRequestHeaders().get(ZuulFilterUtils.HTTP);

    String endpoint = this.microServicesService.retrieveMicroService(serviceName).getUri(http);

    System.out.println("ENDPOINT : " + endpoint);

    String route = this.buildRoute(request.getRequestURI(), endpoint, serviceName);

    HttpResponse httpResponse = null;

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

      CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);

      customHttpServletRequest.addHeader(ZuulFilterUtils.AUTHORIZATION_HEADER, context.getZuulRequestHeaders().get(ZuulFilterUtils.AUTHORIZATION_HEADER));

      HttpHost httpHost = this.getHost(new URL(route));

      String verb = this.getVerb(request);

      InputStreamEntity entity = getInputStreamEntity(request);

      HttpRequest httpRequest = this.getHttpRequest(verb, route, entity);

      httpRequest.setHeaders(this.convertHeaders(this.proxyRequestHelper.buildZuulRequestHeaders(customHttpServletRequest)));

      httpResponse = this.forwardRequest(httpClient, httpHost, httpRequest);

      this.setResponse(httpResponse);

    } catch (IOException e) {
      e.printStackTrace();
    } finally {

    }

    return null;
  }

  private InputStreamEntity getInputStreamEntity(HttpServletRequest request) {
    int contentLength = request.getContentLength();
    InputStream requestEntity = getRequestBody(request);
    ContentType contentType = Objects.nonNull(request.getContentType()) ? ContentType.create(request.getContentType()) : null;
    return new InputStreamEntity(requestEntity, contentLength, contentType);
  }

  private HttpRequest getHttpRequest(String verb, String uri, HttpEntity entity) {

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

  private InputStream getRequestBody(HttpServletRequest request) {

    InputStream requestBody = null;
    try {
      requestBody = request.getInputStream();
    } catch (IOException e) {

    }

    return requestBody;
  }

  private String getVerb(HttpServletRequest request) {
    return request.getMethod().toUpperCase();
  }

  private String buildRoute(String oldEndPoint, String newEndpoint, String serviceName) {
    int index = oldEndPoint.indexOf(serviceName);
    String strippedRoute = oldEndPoint.substring(index + serviceName.length());
    return String.format("%s/%s", newEndpoint, strippedRoute);
  }

  private void setResponse(HttpResponse httpResponse) throws IOException {
    this.proxyRequestHelper.setResponse(httpResponse.getStatusLine().getStatusCode(),
            null, this.revertHeaders(httpResponse.getAllHeaders()));
  }

  private MultiValueMap<String, String> revertHeaders(Header[] headers) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    Arrays.stream(headers).forEach(header -> {
      String name = header.getName();
      if (!map.containsKey(name)) map.put(name, new ArrayList<>());
      map.get(name).add(header.getValue());
    });
    return map;
  }

  private HttpResponse forwardRequest(CloseableHttpClient httpClient, HttpHost httpHost, HttpRequest httpRequest) throws IOException {
    return httpClient.execute(httpHost, httpRequest);
  }

  private HttpHost getHost(URL url) {
    return new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
  }

  private Header[] convertHeaders(MultiValueMap<String, String> headers) {
    List<Header> list = new ArrayList<>();

    headers.keySet().forEach(key -> {
      headers.get(key).forEach(value -> {
        list.add(new BasicHeader(key, value));
      });
    });

    return list.toArray(new BasicHeader[0]);
  }


}
