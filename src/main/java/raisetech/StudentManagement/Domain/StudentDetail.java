package raisetech.StudentManagement.Domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;
  private boolean isDeleted;

  public StudentDetail(Student student, List<StudentsCourses> courses) {
    this.student = student;
    this.studentsCourses = studentsCourses;
    this.isDeleted = false;
  }


  public void markAsDeleted() {
    if (this.student != null) {
      this.student.setDeleted(true);  // studentのisDeletedをtrueに設定
    }
  }
}