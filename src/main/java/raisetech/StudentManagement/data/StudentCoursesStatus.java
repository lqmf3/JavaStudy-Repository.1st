package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生コース申し込み状況")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_course_status")
public class StudentCoursesStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull(message = "受講生コースIDを入力してください。")
  @Column(name = "student_course_id")
  private int studentCourseId;

  @NotNull(message = "受講生コースの申し込み状況を入力してください。")
  private Status status;  // Status列挙型を使用

  @Column(name = "status_date", columnDefinition = "datetime default CURRENT_TIMESTAMP")
  private LocalDateTime statusDate;

  // Status 列挙型をこのクラス内に統合
  public enum Status {
    PRE_APP("Pre-App."),
    APP("App."),
    ONGOING("Ongoing"),
    COMPLETED("Comp.");

    private final String value;

    Status(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static Status fromString(String value) {
      for (Status status : Status.values()) {
        if (status.getValue().equals(value)) {
          return status;
        }
      }
      throw new IllegalArgumentException("Unknown status: " + value);
    }
  }
}
