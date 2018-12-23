package org.myaldoc.proxyserver.login.endpoints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 23/12/2018
 * @Class purposes : .......
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String type;
    private String expirationTime;
}
