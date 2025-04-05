package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.StudentSearchCriteria;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること(){
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchActiveStudent()).thenReturn(studentList);
    when(repository.findAllStudentCourse()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository,times(1)).searchActiveStudent();
    verify(repository,times(1)).findAllStudentCourse();
    verify(converter,times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細の登録_正しいIDに基づいてリポジトリのfindStudentByIdとfindCourseByStudentIdが呼び出せていること(){
    int studentId = 1; //999など明らかにテストとわかる数字が良いかも
    Student student = new Student();
    List<StudentCourse> courses = new ArrayList<>();
    when(repository.findStudentById(studentId)).thenReturn(student);
    when(repository.findCourseByStudentId(studentId)).thenReturn(courses);

    StudentDetail result = sut.getStudentDetailById(studentId);

    //結果がnullでないこと
    assertNotNull(result);
    //学生情報が正しくセットされていること
    assertEquals(student, result.getStudent());
    //コース情報が正しくセットされていること
    assertEquals(courses, result.getStudentCourseList());

    verify(repository,times(1)).findStudentById(studentId);
    verify(repository,times(1)).findCourseByStudentId(studentId);
  }

  @Test
  void 受講生詳細の登録_受講生とコースがリポジトリに登録されること(){
    Student student = new Student();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
    doNothing().when(repository).registerStudent(student);
    studentCourseList.forEach(studentCourse -> doNothing().when(repository).registerStudentCourse(studentCourse));

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    for (StudentCourse studentCourse : studentCourseList){
      verify(repository, times(1)).registerStudentCourse(studentCourse);
    }
    //StudentCourseのstartDateとendDateがnullでないこと
    for (StudentCourse studentCourse : studentCourseList){
      assertNotNull(studentCourse.getStartDate());
      assertNotNull(studentCourse.getEndDate());
    }
  }

  @Test
  void 受講生詳細の更新_受講生とコースが更新されること(){
    Student student = new Student();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    doNothing().when(repository).updateStudent(student);
    studentCourseList.forEach(studentCourse -> doNothing().when(repository).updateStudentCourse(studentCourse));

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    for (StudentCourse studentCourse : studentCourseList)
      verify(repository, times(1)).updateStudentCourse(studentCourse);
  }

  @Test
  void 受講生コース情報を登録する際の初期情報を設定する_受講生コースが初期化されること(){
    Student student = new Student();
    student.setId(1);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    StudentCourse studentCourse = new StudentCourse();
    studentCourseList.add(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.registerStudent(studentDetail);

    //studentIDが設定されること
    assertEquals(student.getId(), studentCourse.getStudentId());
    //startDate が現在の日付に設定されていること
    assertNotNull(studentCourse.getStartDate());
    LocalDateTime expectedStartDate = LocalDateTime.now().toLocalDate().atStartOfDay();
    assertTrue(studentCourse.getStartDate().toLocalDate().isEqual(expectedStartDate.toLocalDate()));

    //endDate が1年後の日付に設定されていること
    assertNotNull(studentCourse.getEndDate());
    LocalDateTime expectedEndDate = LocalDateTime.now().plusYears(1).toLocalDate().atStartOfDay();
    assertTrue(studentCourse.getEndDate().toLocalDate().isEqual(expectedEndDate.toLocalDate()));
  }

  @Test
  void 受講生検索_条件が正しくリポジトリに渡されること() {
    StudentSearchCriteria criteria = new StudentSearchCriteria();
    criteria.setName("Taro");
    criteria.setAgeFrom(20);
    criteria.setAgeTo(30);

    List<Student> mockStudents = new ArrayList<>();
    when(repository.searchStudents(eq(criteria))).thenReturn(mockStudents);

    List<StudentDetail> result = sut.searchStudents(criteria);

    // リポジトリが正しく呼ばれていること
    verify(repository, times(1)).searchStudents(eq(criteria));

    assertEquals(mockStudents, result);
  }
}