package org.myaldoc.proxyserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.myaldoc.core.http.CustomHttpServletRequest;
import org.myaldoc.proxyserver.microservices.services.MicroServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;

@Component
@Slf4j
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
    final RequestContext context = RequestContext.getCurrentContext();

    /** Récupérer la requête **/
    final HttpServletRequest request = context.getRequest();

    /** Récupérer le nom du micro-service appelé **/
    final String serviceName = context.get(ZuulFilterUtils.SERVICE_ID).toString();

    /** Récupérer le protocol **/
    final String protocol = context.getZuulRequestHeaders().get(ZuulFilterUtils.HTTP);

    /** Récupérer le endpoint **/
    final String endpoint = this.microServicesService.retrieveMicroService(serviceName).getUri(protocol);

    /** Reconstruire l'url à appeler **/
    final String url = ZuulFilterUtils.buildRoute(request.getRequestURI(), endpoint, serviceName);

    /** Initialiser une répose http**/
    HttpResponse httpResponse = null;

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

      /** Instancier un wrapper de requête http**/
      CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);

      /** ajouter le paramètre de requête "Authorization" **/
      customHttpServletRequest.addHeader(ZuulFilterUtils.AUTHORIZATION_HEADER, context.getZuulRequestHeaders().get(ZuulFilterUtils.AUTHORIZATION_HEADER));

      /** Construire un httpHost**/
      HttpHost httpHost = ZuulFilterUtils.getHost(new URL(url));

      /** Récuperer le type de la méthode http**/
      String httpMethod = ZuulFilterUtils.getMethod(request);

      /** Récupérer le contenu de la requête**/
      InputStreamEntity entity = ZuulFilterUtils.getInputStreamEntity(request);

      /** Reconstruire la requête http**/
      HttpRequest httpRequest = ZuulFilterUtils.getHttpRequest(httpMethod, url, entity);

      /** Reconstruire les entêtes de requête**/
      Header[] headers = ZuulFilterUtils.convertHeaders(this.proxyRequestHelper.buildZuulRequestHeaders(customHttpServletRequest));

      /** Ajouter les nouvelles entêtes à la nouvelle requête**/
      httpRequest.setHeaders(headers);

      /** Reconstruire la réponse http**/
      httpResponse = ZuulFilterUtils.forwardRequest(httpClient, httpHost, httpRequest);

      /** Passer la réponse au proxy Zuul**/
      ZuulFilterUtils.setResponse(httpResponse, this.proxyRequestHelper);

    } catch (IOException e) {

      log.error("[ZUUL FILTER] " + e.getMessage());

    } finally {

      log.info("[ZUUL FILTER] : request from {0}", url);

    }

    return null;
  }


}
