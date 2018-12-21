package org.myaldoc.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
@ConfigurationProperties("jwt.password.encoder")
public class MyaldocPasswordEncoder implements PasswordEncoder {

  private String secret;
  private Integer iteration;
  private Integer keyLength;

  @Override
  public String encode(CharSequence cs) {

    try {
      byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
              .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), this.secret.getBytes(), this.iteration, this.keyLength))
              .getEncoded();

      return Base64.getEncoder().encodeToString(result);

    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public boolean matches(CharSequence cs, String s) {
    return encode(cs).equals(s);
  }
}
