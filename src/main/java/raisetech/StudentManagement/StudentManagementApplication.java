package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	private String name1 = "Shirai";
	private String name2 = "Aoki";
	private String age1 = "35";
	private String age2 = "20";

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo() {
		return name1 + " " + age1 + "歳" + "," + name2 + " " + age2 + "歳";
	}

	@PostMapping("/studentInfo")
	public void setStudentInfo(String name1, String age1, String name2, String age2){
		this.name1 = name1;
		this.age1 = age1;
		this.name2 = name2;
		this.age2 = age2;
	}
	@PostMapping("/studentName")
	public void upStudentName(String name1){
		this.name1 = name1;
	}
	@PostMapping("/studentAge")
	public void upStudentAge(String age2){
		this.age2 = age2;
	}
}
