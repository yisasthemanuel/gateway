package org.jlobato.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
/**
 * Se incluye una configuración de CorsOrigin para determinadas urls
 * 
 * @author Jesús Manuel Pérez
 *
 */
public class YisasGatewayApplication {

	/**
	 * Punto de arranque
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(YisasGatewayApplication.class, args);
		log.info("Yisas Gateway is up!");
	}
}
