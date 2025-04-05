package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {


  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全体検索が行えること(){
    List<Student> actual = sut.searchActiveStudent();
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生のコース情報の全体検索が行えること(){
    List<StudentCourse> actual = sut.findAllStudentCourse();
    assertThat(actual.size()).isEqualTo(8);
  }

  @Test
  void 受講生の登録が行えること() {
    // IDを自動生成に任せるため、idの設定を省略
    Student student = new Student();
    student.setName("EnamiKoji");
    student.setNickname("Koji");
    student.setEmail("Koji@example.com");
    student.setRegion("Tokyo");
    student.setAge(31);
    student.setGender("Male");
    student.setRemark(null);
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.searchActiveStudent();

    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  void  受講生コース情報の登録が行えること(){
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName("Advanced Java Programming");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));
    studentCourse.setStudentId(2);

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actualCourses = sut.findAllStudentCourse();

    assertThat(actualCourses.size()).isEqualTo(9);
  }

  @Test
  void 受講生の検索が行えること(){
    List<Student> students = sut.searchActiveStudent();
    assertThat(students).isNotNull();
    assertThat(students.size()).isGreaterThan(0);
  }

  @Test
  void 受講生IDに紐づく受講生コース情報の検索が行えること(){
    int studentId = 1;
    List<StudentCourse> courses = sut.findCourseByStudentId(studentId);
    assertThat(courses).isNotNull();
    assertThat(courses.size()).isGreaterThan(0);
  }

  @Test
  void 受講生コース情報の開始日を更新できること(){
    List<StudentCourse> courses = sut.findCourseByStudentId(1);
    StudentCourse studentCourse = courses.get(0);

    LocalDateTime newStartDate = LocalDateTime.of(2024, 1, 15, 0, 0, 0);
    studentCourse.setStartDate(newStartDate);

    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> updatedCourses = sut.findCourseByStudentId(1);

    assertThat(updatedCourses.get(0).getStartDate()).isEqualTo(newStartDate);
  }

  @Test
  void 受講生コース情報のコース名を更新できること(){
    List<StudentCourse> courses = sut.findCourseByStudentId(1);
    StudentCourse studentCourse = courses.get(0);

    studentCourse.setCourseName("Advanced Web Development");

    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> updatedCourses = sut.findCourseByStudentId(1);

    assertThat(updatedCourses.get(0).getCourseName()).isEqualTo("Advanced Web Development");
  }

  @Test
  void 指定した名前の受講生を検索できること() {
    // データベースには「SasakiNatsu」が登録されているとする。
    // 名前で検索
    List<Student> result = sut.findByName("SasakiNatsu");

    // 結果の検証
    assertThat(result.size()).isEqualTo(1);
    assertThat(result.get(0).getName()).isEqualTo("SasakiNatsu");
    assertThat(result.get(0).getNickname()).isEqualTo("Natsu");
    assertThat(result.get(0).getEmail()).isEqualTo("Natu@example.com");
    assertThat(result.get(0).getRegion()).isEqualTo("Shizuoka");
    assertThat(result.get(0).getAge()).isEqualTo(29);
    assertThat(result.get(0).getGender()).isEqualTo("Male");
    assertThat(result.get(0).getRemark()).isEqualTo(null);
    assertThat(result.get(0).isDeleted()).isEqualTo(false);
  }

  @Test
  void 指定した住所の受講生を検索できること() {
    // データベースには「SasakiNatsu」が登録されているとする。
    // 住所で検索
    List<Student> result = sut.findByRegion("Shizuoka");

    // 結果の検証
    assertThat(result.size()).isEqualTo(1);
    assertThat(result.get(0).getName()).isEqualTo("SasakiNatsu");
    assertThat(result.get(0).getNickname()).isEqualTo("Natsu");
    assertThat(result.get(0).getEmail()).isEqualTo("Natu@example.com");
    assertThat(result.get(0).getRegion()).isEqualTo("Shizuoka");
    assertThat(result.get(0).getAge()).isEqualTo(29);
    assertThat(result.get(0).getGender()).isEqualTo("Male");
    assertThat(result.get(0).getRemark()).isEqualTo(null);
    assertThat(result.get(0).isDeleted()).isEqualTo(false);
  }

  @Test
  void 年齢範囲を指定して受講生を検索できること() {
    // 年齢範囲を指定して検索
    List<Student> result = sut.findByAgeBetween(20, 30);

    // 検索結果の検証
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).getName()).isEqualTo("SasakiNatsu");
    assertThat(result.get(1).getName()).isEqualTo("NatsukawaRimi");

    // 範囲外の年齢を指定して検索（結果は空）
    List<Student> result2 = sut.findByAgeBetween(40, 50);

    // 検索結果が空であることを確認
    assertThat(result2).isEmpty();
  }
}