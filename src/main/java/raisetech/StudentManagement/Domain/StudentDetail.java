package raisetech.StudentManagement.Domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Getter
@Setter

public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;
  private boolean isDeleted;

  public void markAsDeleted() {
    if (this.student != null) {
      this.student.setDeleted(true);  // studentのisDeletedをtrueに設定
    }
  }
}