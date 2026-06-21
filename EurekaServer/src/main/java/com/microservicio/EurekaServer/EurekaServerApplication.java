package com.microservicio.EurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
		System.out.println("-------------------------------------------------");
		System.out.println("EUREKA SERVER ESTA CORRIENDO EN: http://localhost:8761");
		System.out.println("Abre esa url en el navegador para ver el dashboard de Eureka Server");
		System.out.println("-------------------------------------------------");
	}

}
