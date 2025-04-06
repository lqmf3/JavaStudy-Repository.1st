package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.StudentSearchCriteria;
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
    Student student = new Student(15, "YamadaYuka", "Yuka", "y_yamada@example.com", null, 22, "Female", " ", false);
    LocalDateTime startDate = LocalDateTime.of(2025, 2, 1, 17, 47, 43);
    LocalDateTime endDate = startDate.plusYears(1);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String formattedStartDate = startDate.format(formatter);
    String formattedEndDate = endDate.format(formatter);

    StudentCourse studentCourse = new StudentCourse(21, 15, " Web Development Basics", startDate, endDate);
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
                        "startDate": "%s",
                        "endDate": "%s"
                    }
                ],
                "deleted": false
            }""".formatted(formattedStartDate, formattedEndDate)));

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


  @Test
  void 受講生検索が条件に基づいて正しく動作すること() throws Exception {
    // モックデータ
    StudentSearchCriteria criteria = new StudentSearchCriteria();
    criteria.setName("AoyamaHaruka");
    criteria.setRegion("Tokyo");

    // モックデータを設定
    Student student = new Student(1, "AoyamaHaruka", "Haru", "Haruka@example.com", "Tokyo", 31, "Female", "GOOD", false);
    StudentDetail studentDetail = new StudentDetail(student, List.of());  // 空の受講履歴

    // サービスメソッドのモック設定（引数にany()を使用）
    when(service.searchStudents(any(StudentSearchCriteria.class))).thenReturn(List.of(studentDetail));

    // GETリクエストのテスト（クエリパラメータで検索条件を送る）
    mockMvc.perform(MockMvcRequestBuilders.get("/students/search")
            .param("name", "AoyamaHaruka")
            .param("region", "Tokyo"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(""" 
              [{
                  "student": {
                      "id": 1,
                      "name": "AoyamaHaruka",
                      "nickname": "Haru",
                      "email": "Haruka@example.com",
                      "region": "Tokyo",
                      "age": 31,
                      "gender": "Female",
                      "remark": "GOOD",
                      "deleted": false
                  },
                  "studentCourseList": []
              }]
          """));

    // ArgumentCaptorを使って引数をキャプチャ
    ArgumentCaptor<StudentSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(StudentSearchCriteria.class);
    verify(service).searchStudents(criteriaCaptor.capture());

    // キャプチャした引数が期待通りであることを検証
    StudentSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertEquals("AoyamaHaruka", capturedCriteria.getName());
    assertEquals("Tokyo", capturedCriteria.getRegion());
  }


  @Test
  void 検索条件に一致する受講生がいない場合に空のリストが返される() throws Exception {
    // 検索条件
    StudentSearchCriteria criteria = new StudentSearchCriteria();
    criteria.setName("YamadaTaro");
    criteria.setRegion("Kumamoto");

    // サービスのモック設定（該当する受講生がいない）
    when(service.searchStudents(criteria)).thenReturn(Collections.emptyList());

    // GETリクエストのテスト（空のリストが返されることを確認）
    mockMvc.perform(MockMvcRequestBuilders.get("/students/search")
            .param("name", "YamadaTaro")
            .param("region", "Kumamoto"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("[]"));

    // ArgumentCaptorを使って引数をキャプチャ
    ArgumentCaptor<StudentSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(StudentSearchCriteria.class);
    verify(service).searchStudents(criteriaCaptor.capture());

    // キャプチャした引数が期待通りであることを検証
    StudentSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertEquals("YamadaTaro", capturedCriteria.getName());
    assertEquals("Kumamoto", capturedCriteria.getRegion());
  }

  @Test
  void 地域指定で検索_一致する受講生がいない場合に空のリストが返される() throws Exception {
    // 検索条件
    StudentSearchCriteria criteria = new StudentSearchCriteria();
    criteria.setName("AoyamaHaruka");
    criteria.setRegion("Kyoto");

    // サービスのモック設定（該当する受講生がいない）
    when(service.searchStudents(any(StudentSearchCriteria.class))).thenReturn(Collections.emptyList());

    // GETリクエストのテスト（空のリストが返されることを確認）
    mockMvc.perform(MockMvcRequestBuilders.get("/students/search")
            .param("name", "AoyamaHaruka")
            .param("region", "Kyoto"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("[]"));

    // 引数をキャプチャして検証
    ArgumentCaptor<StudentSearchCriteria> captor = ArgumentCaptor.forClass(StudentSearchCriteria.class);
    verify(service).searchStudents(captor.capture());

    // キャプチャした引数が期待通りかを確認
    StudentSearchCriteria capturedCriteria = captor.getValue();
    assertEquals("AoyamaHaruka", capturedCriteria.getName());
    assertEquals("Kyoto", capturedCriteria.getRegion());
  }
}
