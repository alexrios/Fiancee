package com.alexrios;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class DummyController {

    private boolean called = false;

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void foo() {
        called = true;
    }

    public boolean wasCalled() {
        return called;
    }
}
