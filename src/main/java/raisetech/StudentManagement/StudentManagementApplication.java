package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	@Autowired
	private  StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/student")
	public String getStudent(@RequestParam("name")String name) {
		Student student = repository.searchByName(name);
		return student.getName() + " " + student.getAge() + "歳";
	}

	@PostMapping("/student")
	public void registerStudent(String name, int age){
		repository.registerStudent(name, age);

	}
	@PatchMapping("/student")
	public void upStudentName(String name, int age){
		repository.updateStudent(name, age);
	}
	@DeleteMapping("/student")
	public void deleteStudent(String name){
		repository.deleteStudent(name);
	}
	@GetMapping("/students")
	public String getAllStudents(){
		List<Student> students = repository.findAll();
		return students.stream()
				.map(student -> student.getName() + "," + student.getAge() + "歳")
				.collect(Collectors.joining("},{", "{", "}"));
	}
}
