package kr.co.daekyo.lloydrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data  // set, get 함수 자동생성 어노테이션(lombok)
@AllArgsConstructor // 생성자 자동생성 어노테이션(lombok)
public class HelloWorldBean {
    private String message;
//    public HelloWorldBean(String message) {
//        this.message = message;
//    }
}
