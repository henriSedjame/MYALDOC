package org.myaldoc.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IterableUtils {

  public static Set<String> StringToSet(String source, String separator) {
    return source.isEmpty() || source.isBlank() ? new HashSet<>() : Arrays.stream(source.split(separator)).collect(Collectors.toSet());
  }

    public static String[] stringtoArray(String source, String separator) {
        return source.isEmpty() || source.isBlank() ? new String[0] : source.split(separator);
    }
}
