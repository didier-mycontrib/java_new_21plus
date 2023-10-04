package tp.withWebFlux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WithWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithWebFluxApplication.class, args);
		System.out.println("http://localhost:8181/withWebFlux/index.html");
	}

}
