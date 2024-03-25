package kr.co.daekyo.lloydrestfulservice.repository;

import kr.co.daekyo.lloydrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
