package cl.duocuc.demo.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * ============================================================
 * EurekaServerApplication
 * ============================================================
 *
 * Que es Eureka?
 * Es el "directorio" de todos los microservicios del sistema.
 * Al arrancar, ms-productos y ms-clientes se registran aqui.
 * El API Gateway consulta este directorio para saber a donde
 * enviar cada peticion usando el nombre del servicio (lb://).
 *
 * @EnableEurekaServer: activa el servidor de registro.
 *   Sin esta anotacion, es una app Boot normal sin registro.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("================================================");
        System.out.println(" Eureka Server corriendo en: http://localhost:8761");
        System.out.println(" Dashboard: abre esa URL en el navegador");
        System.out.println("================================================");
    }

}
