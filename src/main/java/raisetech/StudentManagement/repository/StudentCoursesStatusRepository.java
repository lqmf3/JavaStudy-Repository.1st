package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.StudentCoursesStatus;
import raisetech.StudentManagement.mybatis.StatusEnumTypeHandler;

@Mapper
public interface StudentCoursesStatusRepository {


  // 受講生コースIDに基づくコース申し込み状況を検索
  @Select("SELECT * FROM student_course_status WHERE student_course_id = #{studentCourseId}")
  @Results({
      @Result(property = "status", column = "status", typeHandler = StatusEnumTypeHandler.class),
      @Result(property = "studentCourseId", column = "student_course_id")
  })
  List<StudentCoursesStatus> findByStudentCourseId(@Param("studentCourseId") int studentCourseId);

  // 全ての受講生コース申し込み状況を取得するメソッドを追加
  @Select("SELECT * FROM student_course_status")
  @Results({
      @Result(property = "status", column = "status", typeHandler = StatusEnumTypeHandler.class),
      @Result(property = "studentCourseId", column = "student_course_id")
  })
  List<StudentCoursesStatus> findAllStudentCoursesStatus();
}

