package org.myaldoc.core.patterns;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Patterns {

  public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+=!*()@%&]).{8,}$";
  public static final String CODE_POSTAL = "[0-9]{5}";
  public static final String NUM_TELEPHONE = "^0[^0][0-9]{8}";
  public static final String INDICATIF_TELEPHONE = "^(\\+|00)[0-9]{3}";
  public static final String SIRET = "[0-9]{14}";
}
