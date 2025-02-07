package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです
 * 受講生の検索や登録、更新処理を行います
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です 全件検索を行うので条件指定は行いません
   *
   * @return 受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchActiveStudent();
    List<StudentCourse> StudentCourseList = repository.findAllStudentCourse();
    return converter.convertStudentDetails(studentList, StudentCourseList);
  }

  /**
   * 受講生詳細の登録を行います。受講生と受講生コース情報を個別で登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   * @param studentDetail　受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student);
      repository.registerStudentCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   * @param studentsCourses　受講生コース情報
   * @param student　受講生
   */
  private void initStudentsCourse(StudentCourse studentsCourses, Student student) {
    LocalDateTime now = LocalDateTime.now();

    studentsCourses.setStudentId(student.getId());
    studentsCourses.setStartDate(now.toString());
    studentsCourses.setEndDate(now.plusYears(1).toString());
  }

  /**
   * 受講生詳細検索です IDに紐づく受講生情報を取得した後、その受講生に紐づく受講性コース情報を取得して設定します
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail getStudentDetailById(int id) {
    Student student = repository.findStudentById(id); // 学生情報を取得
    List<StudentCourse> courses = repository.findCourseByStudentId(id); // 関連コース情報を取得
    return new StudentDetail(student, courses);
  }

  /**
   * 受講生詳細の更新を行います。受講生と受講生コース情報をそれぞれ更新します。
   * @param studentDetail　受講生詳細
   */
    public void updateStudent(StudentDetail studentDetail){
      repository.updateStudent(studentDetail.getStudent());
      studentDetail.getStudentCourseList().forEach(repository::updateStudentCourse);
    }
  }