package com.example.untitled25.repository;

import com.example.untitled25.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 이건 왜 붙였더라?????
@RequiredArgsConstructor
public class TeacherRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Teacher> mapper = (resultSet, rowNum) ->
            Teacher.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .build(); // @Builder 를 사용해서 이렇게 작성이 가능 (취향차이)

    public List<Teacher> findAll() {
        return jdbcTemplate.query("SELECT * FROM teacher ORDER BY name", mapper);
    } // 전체를 찾을때는 쿼리

    public Teacher findById(int id) { // 그냥 업데이트하는게아니라 기존의 값을 기반으로 업데이트하기위해 만듬
        return jdbcTemplate.queryForObject("SELECT * FROM teacher WHERE id = ?", mapper, id);
    } // 하나를 찾을때는 쿼리포오브젝트

    public int update(Teacher teacher) {
        return jdbcTemplate.update(
                "UPDATE teacher SET name = ? WHERE id = ?", teacher.getName(), teacher.getId()
        );                                                     // 바꿀 이름         // 기준이되는 아이디
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM teacher WHERE id = ?", id);
    }

    public int save(Teacher teacher) {
        return jdbcTemplate.update(
                "INSERT INTO teacher (name) VALUES (?)", teacher.getName()
        );
    }
}
