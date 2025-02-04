package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
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
   * 受講生一覧検索です 全件検索を行うので条件指定は行いません
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchActiveStudents();
    List<StudentsCourses> StudentsCoursesList = repository.findAllStudentsCourses();
    return converter.convertStudentDetails(studentList, StudentsCoursesList);
  }


  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setStartDate(LocalDateTime.now().toString());
      studentsCourses.setEndDate(LocalDateTime.now().plusYears(1).toString());

      repository.registerStudentCourses(studentsCourses);
    }
    return studentDetail;
  }

  /**
   * 受講生検索です IDに紐づく受講生情報を取得した後、その受講生に紐づく受講性コース情報を取得して設定します
   *
   * @param id 受講生ID
   * @return 受講生
   */
  public StudentDetail getStudentDetailById(int id) {
    Student student = repository.findStudentById(id); // 学生情報を取得
    List<StudentsCourses> courses = repository.findCoursesByStudentId(id); // 関連コース情報を取得
    return new StudentDetail(student, courses);
  }

    public void updateStudent(StudentDetail studentDetail){
      repository.updateStudent(studentDetail.getStudent());
      for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
        repository.updateStudentCourses(studentsCourse);
      }
    }
  }