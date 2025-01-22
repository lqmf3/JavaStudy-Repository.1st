package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchActiveStudents();//論理削除されていないデータのみ取得
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.findAllStudentsCourses();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setStartDate(LocalDateTime.now().toString());
      studentsCourses.setEndDate(LocalDateTime.now().plusYears(1).toString());

      repository.registerStudentCourses(studentsCourses);
    }
  }

  public StudentDetail getStudentDetailById(int id) {
    Student student = repository.findStudentById(id); // 学生情報を取得
    List<StudentsCourses> courses = repository.findCoursesByStudentId(id); // 関連コース情報を取得

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentsCourses(courses);

    return detail;
  }

  public void updateStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    //受講生がキャンセルされた場合
    if (student.isDeleted()) {
      repository.markStudentAsDeleted(student.getId());
    }else {
      repository.updateStudent(student);
    }

    // 関連するコース情報も更新する
    List<StudentsCourses> courses = studentDetail.getStudentsCourses();
    for (StudentsCourses course : courses) {
      repository.updateStudentCourses(course);
    }
  }
}