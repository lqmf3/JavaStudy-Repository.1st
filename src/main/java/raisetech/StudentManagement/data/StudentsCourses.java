package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  private int id;
  private int studentId;
  private String courseName;
  private String startDate;
  private String endDate;
//  private boolean cancel;
}
