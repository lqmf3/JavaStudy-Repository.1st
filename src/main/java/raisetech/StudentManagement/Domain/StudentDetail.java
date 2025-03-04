package raisetech.StudentManagement.Domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourseList;
  private boolean isDeleted;

  public StudentDetail(Student student, List<StudentCourse> courses) {
    this.student = student;
    this.studentCourseList = courses;
    this.isDeleted = false;
  }


  public void markAsDeleted() {
    if (this.student != null) {
      this.student.setDeleted(true);  // studentのisDeletedをtrueに設定
    }
  }
}