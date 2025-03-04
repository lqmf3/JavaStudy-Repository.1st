package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    when(service.searchStudentList()).thenReturn(List.of(new StudentDetail()));

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと(){
    Student student = new Student(27,"FukuzawaSaku","Saku","sakusaku@example.com",null,28,"Famele", " ",false);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生IDを指定して受講生詳細が取得できること()throws Exception{
    Student student = new Student(15,"YamadaYuka","Yuka","y_yamada@example.com",null,22,"Female"," ",false);
    StudentCourse studentCourse = new StudentCourse(21,15," Web Development Basics","2025-02-01 17:47:43","2026-02-01");

    StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourse));

    when(service.getStudentDetailById(15)).thenReturn(studentDetail);

    mockMvc.perform(get("/student/15"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().json("""
            {
                    "student": {
                        "id": 15,
                        "name": "YamadaYuka",
                        "nickname": "Yuka",
                        "email": "y_yamada@example.com",
                        "region": null,
                        "age": 22,
                        "gender": "Female",
                        "remark": " ",
                        "deleted": false
                    },
                    "studentCourseList": [
                        {
                            "id": 21,
                            "studentId": 15,
                            "courseName": " Web Development Basics",
                            "startDate": "2025-02-01 17:47:43",
                            "endDate": "2026-02-01"
                        }
                    ],
                    "deleted": false
                }"""));

    verify(service, times(1)).getStudentDetailById(15);
  }

  @Test
  void 存在しないIDを指定した場合に404エラーが返ること() throws Exception {
    when(service.getStudentDetailById(999)).thenReturn(null);

    mockMvc.perform(get("/student/999"))
        .andExpect(status().isNotFound())  // 404エラーを期待
        .andExpect(content().string("指定したIDの受講生は見つかりませんでした。"));

    verify(service, times(1)).getStudentDetailById(999);
  }

  @Test
  void 受講生詳細があり受講履歴が空であること() throws Exception {
    Student student = new Student(27,"FukuzawaSaku","Saku","sakusaku@example.com",null,28,"Female", " ",false);

    StudentDetail studentDetail = new StudentDetail(student, List.of());  // 空のコースリスト

    when(service.getStudentDetailById(27)).thenReturn(studentDetail);

    mockMvc.perform(get("/student/27"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().json("""
            {
                "student": {
                    "id": 27,
                    "name": "FukuzawaSaku",
                    "nickname": "Saku",
                    "email": "sakusaku@example.com",
                    "region": null,
                    "gender": "Female",
                    "remark": " ",
                    "deleted": false
                },
                "studentCourseList": [],
                "deleted": false
            }
            """));

    verify(service, times(1)).getStudentDetailById(27);
  }
}