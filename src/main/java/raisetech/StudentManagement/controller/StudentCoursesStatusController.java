package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.StudentCoursesStatus;
import raisetech.StudentManagement.exception.StudentNotFoundException;
import raisetech.StudentManagement.service.StudentCoursesStatusService;

@RestController
@RequestMapping("/courseStatus")
public class StudentCoursesStatusController {

  private final StudentCoursesStatusService service;

  @Autowired
  public StudentCoursesStatusController(StudentCoursesStatusService service){
    this.service = service;
  }

  @Operation(
      summary = "受講生のコース申し込み状況の取得",
      description = "指定された受講生コースIDに基づき、受講生のコース申し込み状況を取得します。"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "成功",
          content = @Content(schema = @Schema(implementation = StudentCoursesStatus.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "受講生コースIDに該当するコース申し込み状況が見つからない場合"
      )
  })

  // 受講生コースIDに基づくコース申し込み状況を取得
  @GetMapping("/{studentCourseId}")
  public ResponseEntity<?> getStudentCourseStatus(@PathVariable @Min(1) int studentCourseId) {
    List<StudentCoursesStatus> statusList = service.getStudentCourseStatusByStudentCourseId(studentCourseId);

    // 該当するコース申し込み状況が見つからない場合、404エラーを返す
    if (statusList == null || statusList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("受講生コースIDに該当するコース申し込み状況が見つかりませんでした。");
    }

    // 該当するコース申し込み状況があれば、その情報を返す
    return ResponseEntity.ok(statusList);
  }

  // StudentNotFoundExceptionを処理する例外ハンドラ
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex){
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage()); // 404
  }

  // 全ての受講生コース申し込み状況を取得するエンドポイント
  @GetMapping
  public ResponseEntity<List<StudentCoursesStatus>> getAllStudentCourses() {
    List<StudentCoursesStatus> allCoursesStatus = service.getAllStudentCourses();
    return ResponseEntity.ok(allCoursesStatus);
  }
}
