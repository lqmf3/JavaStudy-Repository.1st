package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSearchCriteria {
  //　検索条件
  private String name;
  private String region;
  private Integer ageFrom; // 年齢（最小）
  private Integer ageTo; //　年齢（最大）
  private String gender;
}
