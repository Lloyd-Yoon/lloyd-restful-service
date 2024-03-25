package kr.co.daekyo.lloydrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  //default 생성자(인자를 갖지 않은 생성자) 처리
@Entity  // class이름으로 테이블 생성
public class Post {
    @Id // primary key(id) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성 컬럼
    private Integer id;

    private String description;

    // n : 1 관계(이용자가 여러 포스트 작성) ,
    // 지연로딩 : 사용자데이타 조회시 post데이타를 즉시가져오는것이 아니라 post가 로딩되는 시점에 가져오기 위함
    // 연과관계가 있는 엔터티는 디폴트로 EAGER(즉시로딩임)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
