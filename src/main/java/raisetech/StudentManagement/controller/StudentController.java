package raisetech.StudentManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.naming.Binding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.Domain.StudentDetail;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {

    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCoursesList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  private static List<StudentDetail> getStudentDetails(List<StudentDetail> studentDetails) {
    return studentDetails;
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    //　新規受講生情報を登録する処理を実装する。
    //　コース情報も一緒に実装できるように実装する。コースは単体で良い。

    if (studentDetail.getStudentsCourses() == null || studentDetail.getStudentsCourses().isEmpty()) {
      result.rejectValue("studentsCourses", "error.studentsCourses", "コース情報を入力してください。");
      return "registerStudent";
      }
    System.out.println(studentDetail.getStudent().getName() +"さんが新規受講生として登録されました");

      return "redirect:/studentList";
    }
  }