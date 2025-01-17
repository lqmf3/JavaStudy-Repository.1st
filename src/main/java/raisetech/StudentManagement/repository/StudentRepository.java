package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
  @Insert("INSERT INTO students (name, nickname, email, region, age, gender, remark, is_deleted) " +
      "VALUES (#{name}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name, start_date, end_date) " + "VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourses(StudentsCourses studentsCourses);

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(@Param("id") int id);

  @Select("SELECT * FROM students_courses WHERE student_id = #{id}")
  List<StudentsCourses> findCoursesByStudentId(@Param("id") int id);

  @Update("UPDATE students " +
      "SET name = #{name}, nickname = #{nickname}, email = #{email}, region = #{region}, " +
      "age = #{age}, gender = #{gender}, remark = #{remark} " + "WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses " +
      "SET course_name = #{courseName}, start_date = #{startDate}, end_date = #{endDate} " +
      "WHERE id = #{id}")
  void updateStudentCourses(StudentsCourses course);
}
