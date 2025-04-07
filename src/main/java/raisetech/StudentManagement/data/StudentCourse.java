package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId;
  private String courseName;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public StudentCourse(int id, int studentId, String courseName, LocalDateTime startDate, LocalDateTime endDate) {
    this.id = id;
    this.studentId = studentId;
    this.courseName = courseName;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public StudentCourse(){
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StudentCourse that = (StudentCourse) o;
    return id == that.id &&
           studentId == that.studentId &&
           Objects.equals(courseName, that.courseName) &&
           Objects.equals(startDate, that.startDate) &&
           Objects.equals(endDate, that.endDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, studentId, courseName, startDate, endDate);
  }
}
