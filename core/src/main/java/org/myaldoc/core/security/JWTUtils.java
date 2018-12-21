package org.myaldoc.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.myaldoc.core.security.models.MyaldocRole;
import org.myaldoc.core.security.models.MyaldocUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties("jwt.configuration")
public class JWTUtils implements Serializable {

  private String secret;
  private String expiration;


  /**
   * GENERER LE TOKEN
   *
   * @param user
   * @return
   */
  public String generateToken(MyaldocUser user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", user.getRoles().stream().map(MyaldocRole::name));
    claims.put("enable", user.isEnabled());
    return this.doGenerateToken(claims, user.getUsername());
  }

  /**
   * RECUPERER LES INFOS DU TOKEN
   *
   * @param token
   * @return
   */
  public Claims getAllClaimsFromToken(String token) {
    return Jwts
            .parser()
            .setSigningKey(this.secret)
            .parseClaimsJws(token)
            .getBody();
  }

  /**
   * RECUPERER LE USERNAME
   *
   * @param token
   * @return
   */
  public String getUsernameFromToken(String token) {
    return this.getAllClaimsFromToken(token)
            .getSubject();
  }

  /**
   * RECUPERER LA DATE D'EXPIRATION DU TOKEN
   *
   * @param token
   * @return
   */
  public Date getExpirationDateFromToken(String token) {
    return this.getAllClaimsFromToken(token)
            .getExpiration();
  }

  /**
   * VERIFIER QUE LE TOKEN EST VALIDE
   *
   * @param token
   * @return
   */
  public Boolean validateToken(String token) {
    return !this.isTokenExpired(token);
  }

  /**
   * PRIVATE METHODES
   **/
  private Boolean isTokenExpired(String token) {
    return this.getExpirationDateFromToken(token).before(new Date());
  }

  private String doGenerateToken(Map<String, Object> claims, String username) {
    Long expirationTimeLong = Long.parseLong(expiration);

    final Date createdDate = new Date();
    final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

    return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, this.secret)
            .compact();
  }

}
