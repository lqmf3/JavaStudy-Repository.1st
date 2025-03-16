package raisetech.StudentManagement.controller.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

class StudentConverterTest {
  private StudentConverter studentConverter;

  @BeforeEach
  void setup(){
    studentConverter = new StudentConverter();
  }

  @Test
  void 受講生に紐づく受講生コース情報をマッピングする_2人の受講生の各受講生コース情報が正しくマッピングされている(){
    //モックの準備
    Student student1 =new Student(15,"AoyamaHaruka", "Haru", "Haruka@example.com", "Tokyo", 31, "Female", "GOOD", false);
    Student student2 =new Student(27, "FukuzawaSaku", "Saku", "saku@example.com", null, 28, "Female", "", false);

    LocalDateTime now = LocalDateTime.now();
    StudentCourse studentCourse1 = new StudentCourse(6, 15, "Introduction to SQL", now, now.plusMonths(2));
    StudentCourse studentCourse2 = new StudentCourse(10, 15, "Machine Learning Basics", now.plusMonths(4), now.plusMonths(6));
    StudentCourse studentCourse3 = new StudentCourse(22, 27, "Java Programming", now.plusMonths(14), now.plusMonths(16));

    List<Student> studentList = List.of(student1, student2);
    List<StudentCourse> studentCourseList = List.of(studentCourse1, studentCourse2, studentCourse3);

    List<StudentDetail> result = studentConverter.convertStudentDetails(studentList, studentCourseList);

    // 2人の学生がマッピングされていること
    assertEquals(2, result.size());
    // student1 (AoyamaHaruka) のコースが2つマッピングされているか
    StudentDetail studentDetail = result.get(0);
    assertEquals(student1, studentDetail.getStudent());
    assertEquals(2, studentDetail.getStudentCourseList().size());
    assertTrue(studentDetail.getStudentCourseList().contains(studentCourse1));
    assertTrue(studentDetail.getStudentCourseList().contains(studentCourse2));

    // student2 (FukuzawaSaku) のコースが1つマッピングされているか
    StudentDetail studentDetail2 = result.get(1);
    assertEquals(student2, studentDetail2.getStudent());
    assertEquals(1, studentDetail2.getStudentCourseList().size());
    assertTrue(studentDetail2.getStudentCourseList().contains(studentCourse3));
  }

  @Test
  void 受講生に紐づく受講生コース情報をマッピングする_空のリストが返されるか(){
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();

    List<StudentDetail> result = studentConverter.convertStudentDetails(studentList, studentCourseList);

    // 結果は空のリストになっているか
    assertTrue(result.isEmpty());
  }
}