package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;
  private String name;
  private String nickname;
  private String email;
  private String region;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
}



