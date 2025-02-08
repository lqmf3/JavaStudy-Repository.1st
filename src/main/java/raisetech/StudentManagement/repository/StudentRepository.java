package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報と紐づけるRepositoryです
 */

@Mapper
public interface StudentRepository {

  /**
   *受講生の全件検索を行います
   * @return 全権検索した受講生情報の一覧
   */
  List<Student> searchActiveStudent();

  /**
   * 受講生のコース情報の全件検索を行います
   * @return　受講生のコース情報（全件）
   */
  List<StudentCourse> findAllStudentCourse();

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   * @param student　受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   * @param studentCourse　受講コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生の検索を行います
   * @param id 受講生ID
   * @return　受講生
   */
  Student findStudentById(@Param("id") int id);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します
   * @param id　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> findCourseByStudentId(@Param("id") int id);

  /**
   * 受講生を更新します。
   * @param student　受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   * @param course　受講生コース情報
   */
  void updateStudentCourse(StudentCourse course);
  void markStudentAsDeleted(@Param("id")int id);//論理削除処理
}
