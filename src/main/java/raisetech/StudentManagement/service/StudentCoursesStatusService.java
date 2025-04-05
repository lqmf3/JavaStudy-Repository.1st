package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.StudentCoursesStatus;
import raisetech.StudentManagement.exception.StudentNotFoundException;
import raisetech.StudentManagement.repository.StudentCoursesStatusRepository;

@Service
public class StudentCoursesStatusService {

  private final StudentCoursesStatusRepository repository;

  @Autowired
  public StudentCoursesStatusService(StudentCoursesStatusRepository repository) {
    this.repository = repository;
  }

  /**
   * 受講生コースIDに基づき、コースの申し込み状況を取得
   * @param studentCourseId 受講生コースID
   * @return コース申し込み状況のリスト
   * @throws StudentNotFoundException 受講生コースIDが見つからない場合
   */
  public List<StudentCoursesStatus> getStudentCourseStatusByStudentCourseId(int studentCourseId) {
    // 受講生コースIDでコース申し込み状況を取得
    List<StudentCoursesStatus> statuses = repository.findByStudentCourseId(studentCourseId);
    if (statuses.isEmpty()) {
      throw new StudentNotFoundException("受講生コースID " + studentCourseId + " に該当するコース申し込み状況が見つかりませんでした。");
    }
    return statuses;
  }

  /**
   * 全ての受講生コース申し込み状況を取得
   * @return 受講生コース申し込み状況のリスト
   */
  public List<StudentCoursesStatus> getAllStudentCourses() {
    return repository.findAllStudentCoursesStatus();
  }
}
