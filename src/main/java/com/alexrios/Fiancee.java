package com.alexrios;

import com.google.common.base.Splitter;
import net.jodah.concurrentunit.Waiter;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.System.lineSeparator;

@Component
public class Fiancee implements ApplicationListener<RequestHandledEvent> {

    private Waiter waiter;
    private HttpMethod method;
    private String url;

    public Fiancee withURL(String url) {
        this.url = url;
        return this;
    }

    public Fiancee withMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public void waitFor(long timeout, TimeUnit unit) throws TimeoutException {
        checkArgument(timeout >= 0, "Negative timeout? Are you sure?" + lineSeparator() + " protip: zero is forever");
        checkArgument(!isNullOrEmpty(url), "You should have fill URL");
        checkNotNull(method, "You should have fill method." + lineSeparator() + "Available methods: " + HttpMethod.values());
        waiter = new Waiter();
        waiter.await(timeout, unit);
    }

    @Override
    public void onApplicationEvent(RequestHandledEvent event) {
        if (isMethod(event, "[" + method + "]") && isURL(event, "[" + url + "/]")) {
            waiter.resume();
        }
    }

    private boolean isMethod(RequestHandledEvent event, String method) {
        Map<String, String> map = Splitter.on(";").omitEmptyStrings().trimResults().withKeyValueSeparator("=").split(event.getDescription());
        return map.get("method").equals(method);
    }

    private boolean isURL(RequestHandledEvent event, String uri) {
        Map<String, String> map = Splitter.on(";").omitEmptyStrings().trimResults().withKeyValueSeparator("=").split(event.getDescription());
        return map.get("url").equals(uri);
    }

}
