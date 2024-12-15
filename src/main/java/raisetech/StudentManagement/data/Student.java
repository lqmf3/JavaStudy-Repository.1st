package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private String id;
  private String name;
  private String nickname;
  private String email;
  private String region;
  private int age;
  private String gender;
  private String remark;
  private  boolean is_deleted;
  }

