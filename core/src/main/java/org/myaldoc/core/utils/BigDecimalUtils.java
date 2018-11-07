package org.myaldoc.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.MathContext;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalUtils {

  private static MathContext context = new MathContext(20);

  public static BigDecimal create(String number) {
    return new BigDecimal(number, context);
  }

  public static BigDecimal add(BigDecimal number1, BigDecimal number2) {
    return number1.add(number2, context);
  }

  public static BigDecimal substract(BigDecimal number1, BigDecimal number2) {
    return number1.subtract(number2, context);
  }

  public static BigDecimal mutiply(BigDecimal number1, BigDecimal number2) {
    return number1.multiply(number2, context);
  }

  public static BigDecimal divide(BigDecimal number1, BigDecimal number2) {
    return number1.divide(number2, context);
  }
}
