package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ。
 *
 * 全権検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */

@Mapper
public interface StudentRepository {

  /**
   *全権検索します。
   *
   * @return 全権検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> findAllStudentsCourses();

  // データベースに学生を挿入する処理
  @Insert("INSERT INTO students (name, nickname, email, region, age, gender, remark) " +
      "VALUES (#{name}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark})")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name) VALUES (#{studentId}, #{courseName})")
  void insertStudentCourse(@Param("studentId") String studentId, @Param("courseName") String courseName);
}
