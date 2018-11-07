package org.myaldoc.core.formatters;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JSONFormatter {
  private static final Gson GSON;
  private static final FieldNamingPolicy FIELD_NAMING_POLICY;

  static {
    FIELD_NAMING_POLICY = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
    GSON = (new GsonBuilder()).setPrettyPrinting().setFieldNamingPolicy(FIELD_NAMING_POLICY).create();
  }

  public static <T> String toJSON(T t) {
    return GSON.toJson(t);
  }
}
