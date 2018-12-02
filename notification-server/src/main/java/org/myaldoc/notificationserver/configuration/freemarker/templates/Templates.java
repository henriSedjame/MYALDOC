package org.myaldoc.notificationserver.configuration.freemarker.templates;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */

@Getter
@Setter
public class Templates {
    private Map<String, Object> variables = new HashMap<>();
}
