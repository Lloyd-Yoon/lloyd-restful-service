package kr.co.daekyo.lloydrestfulservice.controller;

import kr.co.daekyo.lloydrestfulservice.bean.HelloWorldBean;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController (MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    // GET
    // URI - /hello-world
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld () {
        return("Hello World!");
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean () {
        return new HelloWorldBean("Hello World"); // class객체가 반환되면 출력시 json포맷(name : value)으로 표현됨
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanVariable (@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name)); // class객체가 반환되면 출력시 json포맷(name : value)으로 표현됨
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized (
        @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }

}
