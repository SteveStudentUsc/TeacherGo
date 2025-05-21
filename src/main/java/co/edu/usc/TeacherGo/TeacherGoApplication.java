package co.edu.usc.TeacherGo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("co.edu.usc.TeacherGo.repository")

public class TeacherGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherGoApplication.class, args);}

}
