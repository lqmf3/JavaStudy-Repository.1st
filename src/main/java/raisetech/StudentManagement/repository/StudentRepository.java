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
 * 受講生テーブルと受講生コース情報と紐づけるRepositoryです
 */

@Mapper
public interface StudentRepository {

  /**
   *受講生の全件検索を行います
   * @return 全権検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students WHERE is_deleted = FALSE") // is_deleted = FALSE の受講生を取得
  List<Student> searchActiveStudents();

  /**
   * 受講生のコース情報の全件検索を行います
   * @return　受講生のコース情報（全件）
   */
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

  /**
   * 受講生の検索を行います
   * @param id 受講生ID
   * @return　受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(@Param("id") int id);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します
   * @param id　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{id}")
  List<StudentsCourses> findCoursesByStudentId(@Param("id") int id);

  @Update("UPDATE students " +
      "SET name = #{name}, nickname = #{nickname}, email = #{email}, region = #{region}, " +
      "age = #{age}, gender = #{gender}, remark = #{remark} , is_deleted = #{isDeleted} " + "WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses " +
      "SET course_name = #{courseName} WHERE student_id = #{id}")
  void updateStudentCourses(StudentsCourses course);

  @Update("UPDATE students SET is_deleted = TRUE WHERE id = #{id}")
  void markStudentAsDeleted(@Param("id")int id);//論理削除処理
}
