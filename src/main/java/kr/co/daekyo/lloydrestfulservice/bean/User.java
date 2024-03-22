package kr.co.daekyo.lloydrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity  // class이름으로 테이블 생성
@Table(name = "users") // 테이블 이름 지정
public class User {
    @Schema(title = "사용자 ID", description = "사용자ID는 자동생성됩니다.")
    @Id // primary key(id) 지정
    @GeneratedValue // 자동생성 컬럼
    private Integer id;

    @Schema(title = "사용자 이름", description = "사용자 이름을 입력해주세요(최소 2글자 이상).")
    @Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")
    private String name;

    @Schema(title = "등록일", description = "사용자의 등록일을 입력해주세요")
    @Past(message = "등록일은 미래의 날자를 입력하실 수 없습니다.")
    private Date joinDate;

    // @JsonIgnore  --> 대체 : @JsonIgnoreProperties(value = {"password", "ssn"})
    @Schema(title = "비밀번호", description = "사용자 비밀번호를 입력해주세요")
    private String password;
    // @JsonIgnore
    @Schema(title = "주민번호", description = "사용자 주민번호를 입력해주세요")
    private String ssn;
}
