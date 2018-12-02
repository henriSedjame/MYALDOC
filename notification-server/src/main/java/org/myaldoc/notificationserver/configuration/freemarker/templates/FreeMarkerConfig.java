package org.myaldoc.notificationserver.configuration.freemarker.templates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.util.Map;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */

@Configuration
public class FreeMarkerConfig {

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates");
        return bean;
    }

    @Bean("accountCreationemailTemplate")
    public Templates accountCreationemailTemplate() {
        Templates template = new Templates();
        final Map<String, Object> variables = template.getVariables();
        variables.put("Name", "Henri SEDJAME");
        variables.put("location", "Niort");
        return template;
    }

}
