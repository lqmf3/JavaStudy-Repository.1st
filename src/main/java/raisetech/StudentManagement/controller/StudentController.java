package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.data.StudentSearchCriteria;
import raisetech.StudentManagement.exception.TestException;
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
   * 受講生詳細一覧検索です。
   * 検索条件が指定されていなければ全件検索、それ以外は条件に基づいて検索します。
   *
   * @param name    検索条件
   * @param region  検索条件
   * @param ageFrom 検索条件
   * @param ageTo   検索条件
   * @param gender  検索条件
   * @return 検索結果の受講生リスト（全件または条件付き）
   */
  @Operation(summary = "受講生一覧検索", description = "受講生詳細の一覧を検索します。条件検索にも対応します。")
  @GetMapping("/studentList")
  public ResponseEntity<List<StudentDetail>> getStudentList(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String region,
      @RequestParam(required = false) Integer ageFrom,
      @RequestParam(required = false) Integer ageTo,
      @RequestParam(required = false) String gender) {

    // 検索条件を設定
    StudentSearchCriteria criteria = new StudentSearchCriteria(name, region, ageFrom, ageTo, gender);

    // 条件に基づいて検索
    List<StudentDetail> studentDetails = service.searchStudents(criteria);

    // 結果が空でもOK（空リストで正常返却）
    return ResponseEntity.ok(studentDetails);
  }

  /**
   * 受講生詳細の登録を行います
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
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
  @Operation(summary = "受講生検索", description = "受講生情報を受講生IDから検索します。")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "成功",
          content = @Content(schema = @Schema(implementation = StudentDetail.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "無効なIDが指定された場合"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "指定されたIDの受講生が見つからない場合")
  })
  @GetMapping("/student/{id}")
  public ResponseEntity<?> getStudent(@PathVariable @Min(1) @Max(999) int id) {

    StudentDetail studentDetail = service.getStudentDetailById(id);
    if (studentDetail == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("指定したIDの受講生は見つかりませんでした。");  //404
    }
    return ResponseEntity.ok(studentDetail);  //200
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新もここで行います（論理削除）
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "受講生情報の更新", description = "受講生情報の更新を行います。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
