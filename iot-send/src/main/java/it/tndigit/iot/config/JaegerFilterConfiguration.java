package it.tndigit.iot.config;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.web.servlet.filter.TracingFilter;
import io.opentracing.util.GlobalTracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class JaegerFilterConfiguration {

    //@Bean
    public TracingFilter tracingFilter(Tracer tracer) {
        Span span = GlobalTracer.get().activeSpan();
        span.setTag("codiceMessaggio", "sdfkjfkjdhsfkjh");

        return new TracingFilter(tracer);
    }
}