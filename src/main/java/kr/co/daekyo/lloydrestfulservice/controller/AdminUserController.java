package kr.co.daekyo.lloydrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kr.co.daekyo.lloydrestfulservice.bean.AdminUser;
import kr.co.daekyo.lloydrestfulservice.bean.AdminUserV2;
import kr.co.daekyo.lloydrestfulservice.bean.User;
import kr.co.daekyo.lloydrestfulservice.dao.UserDaoService;
import kr.co.daekyo.lloydrestfulservice.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/admin")  // 모든 하위 URL에 "/admin" 추가
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    // /admin/users/{id}   : @RequestMapping("/admin")에서 처리
    //@GetMapping("/v1/users/{id}")  // 트위터에서 사용
    //@GetMapping(value = "/users/{id}", params = "version=1") //아마존에서 사용
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") // MS에서 사용
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json") // 깃허브에서 사용 
    public MappingJacksonValue retriveAllUsers4Admin (@PathVariable int id) {
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%d] not found", id));
        } else {
            // adminUser.setId(user.getId());  // 귀찮은 작업 - 이거 대신 사용
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }


    // @GetMapping("/v1/users")  // /admin/users
    //@GetMapping(value = "/users", params = "version=1")  // /admin/users
//    @GetMapping(value = "/users", headers = "X-API-VERSION=1")  // /admin/users
    @GetMapping(value = "/users", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retriveAllAdminUsers () {

        List<User> users = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<>();

        AdminUser adminUser = null;

        for(User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }

    //@GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers="X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retriveAllUsers4AdminV2 (@PathVariable int id) {
        User user = service.findOne(id);

        AdminUserV2 adminUserV2 = new AdminUserV2();
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%d] not found", id));
        } else {
            // adminUser.setId(user.getId());  // 귀찮은 작업 - 이거 대신 사용
            BeanUtils.copyProperties(user, adminUserV2);
            adminUserV2.setGrade("VIP"); //
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn", "grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUserV2);
        mapping.setFilters(filters);

        return mapping;
    }
}
