package com.ericsson.event.translator;

import io.cloudevents.spring.mvc.CloudEventHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * CloudEventHandlerConfiguration helps to handle CloudEvent inputs or outputs
 * through @RestController, and the conversion will be handled by Spring
 * "/translate/eiffel" endpoint is an example of CloudEvent input in this
 * project, Spring will handle the conversion.
 */
@Configuration
public class CloudEventHandlerConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new CloudEventHttpMessageConverter());
    }
}
