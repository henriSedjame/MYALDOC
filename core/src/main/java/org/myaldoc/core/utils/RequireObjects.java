package org.myaldoc.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.myaldoc.core.exceptions.ExceptionBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequireObjects {

  public static <T extends Exception> void requireNotNull(Object object, Class<T> exceptionClass, String message) throws T {
    try {
      Objects.requireNonNull(object);
    } catch (NullPointerException e) {
      try {
        throw exceptionClass.getConstructor(String.class).newInstance(message);
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
        log.error(ex.getMessage());
      }
    }
  }

  public static <T extends Exception> void requireNotNull(List<Object> objects, ExceptionBuilder exceptionBuilder, String message) throws T {
    boolean haveNull = false;
    Throwable t = null;

    for (Object object : objects) {
      try {
        Objects.requireNonNull(object);
      } catch (NullPointerException e) {
        haveNull = true;
        t = e;
      }
    }

    if (haveNull) exceptionBuilder.addException(message, t);
  }

  public static <T extends Exception> void expect(Object actualValue, Object expectedValue, Class<T> exceptionClass, String message) throws T {
    if (!Objects.equals(actualValue, expectedValue)) {
      try {
        message = message.concat(" ( la valeur attendue est : " + expectedValue.toString() + ". La valeur actuelle est : " + actualValue.toString());
        throw exceptionClass.getConstructor(String.class).newInstance(message);
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
        log.error(ex.getMessage());
      }
    }
  }

  public static <T extends Exception, S> void require(S s, Predicate<S> predicate, Class<T> exceptionClass, String message) throws T {
    if (!predicate.test(s)) {
      try {
        throw exceptionClass.getConstructor(String.class).newInstance(message);
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
        log.error(ex.getMessage());
      }
    }
  }
}
