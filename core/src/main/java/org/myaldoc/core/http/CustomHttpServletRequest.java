package org.myaldoc.core.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomHttpServletRequest extends HttpServletRequestWrapper {

  private Map<String, String> customHeaderMap;

  public CustomHttpServletRequest(HttpServletRequest request) {
    super(request);
    customHeaderMap = new HashMap<>();
  }

  public void addHeader(String name, String value) {
    customHeaderMap.put(name, value);
  }

  @Override
  public String getParameter(String name) {
    String paramValue = super.getParameter(name);
    return Objects.nonNull(paramValue) ? paramValue : customHeaderMap.get(name);
  }

  @Override
  public String getHeader(String name) {
    String headerValue = super.getHeader(name);
    return Objects.nonNull(headerValue) ? headerValue : customHeaderMap.get(name);
  }
}
