package org.myaldoc.core.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyaldocUser implements UserDetails {

  private String username;
  private String password;
  private Boolean enabled;

  @Getter
  @Setter
  private Collection<MyaldocRole> roles = new TreeSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toList());
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return this.password;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
}
