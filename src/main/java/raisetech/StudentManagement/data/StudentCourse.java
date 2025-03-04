package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId;
  private String courseName;
  private String startDate;
  private String endDate;

  public StudentCourse(int id, int studentId, String courseName, String startDate, String endDate) {
    this.id = id;
    this.studentId = studentId;
    this.courseName = courseName;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public StudentCourse(){
  }
}
