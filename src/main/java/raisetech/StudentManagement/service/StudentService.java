package raisetech.StudentManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

  public List<Student> search30yearOldStudents() {
    return repository.search().stream().filter(student -> student.getAge() >= 30 && student.getAge() <= 39).toList();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.findAllStudentsCourses();
  }
  public List<StudentsCourses> searchAdvancedJavaProgrammingCourses() {
    return repository.findAllStudentsCourses().stream().filter(course -> "Advanced Java Programming".equals(course.getCourseName())).toList();
  }
}