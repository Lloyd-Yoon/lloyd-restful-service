package kr.co.daekyo.lloydrestfulservice.repository;

import kr.co.daekyo.lloydrestfulservice.bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
