package raisetech.StudentManagement.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


/**
 *
 * 受講生の登録や検索、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {
  private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

  private StudentService service;


  @Autowired
  public StudentController(StudentService service) {

    this.service = service;

  }

  /**
   * 受講生詳細一覧検索です
   * 全件検索を行うので条件指定は行いません
   * @return　受講生詳細一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細の登録を行います
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生検索です
   * IDに紐づく任意の受講生の情報を取得します
   * @param id　受講生ID
   * @return　受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @Min(1) @Max(999) int id) {
    return service.getStudentDetailById(id);
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新もここで行います（論理削除）
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
