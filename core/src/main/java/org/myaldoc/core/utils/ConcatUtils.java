package org.myaldoc.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcatUtils {

    public static String concat(String... strings) {
        StringBuilder builder = new StringBuilder();

        for (String str : strings) {
            if (Objects.nonNull(str)) builder.append(str);
        }
        return builder.toString();
    }
}
