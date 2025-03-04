package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {
  @Min(value = 1, message = "IDは1以上でなければなりません。")
  private int id;

  @NotBlank(message = "名前は必須です。")
  private String name;

  private String nickname;
  private String email;
  private String region;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;

  public Student(){

  }

  public Student(int id, String name, String nickname, String email, String region, int age,
      String gender, String remark, boolean isDeleted) {
    this.id = id;
    this.name = name;
    this.nickname = nickname;
    this.email = email;
    this.region = region;
    this.age = age;
    this.gender = gender;
    this.remark = remark;
    this.isDeleted = isDeleted;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
}



