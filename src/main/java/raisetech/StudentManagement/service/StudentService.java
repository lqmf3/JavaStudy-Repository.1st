package raisetech.StudentManagement.service;

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
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.findAllStudentsCourses();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    // 学生情報を保存
    repository.saveStudent(studentDetail.getStudent());

    // 各コース情報を保存
    for (StudentsCourses course : studentDetail.getStudentsCourses()) {
      repository.saveStudentCourse(studentDetail.getStudent().getId(), course.getCourseName());
    }
  }
}