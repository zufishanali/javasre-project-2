package com.project2.restaurantApi;

import com.project2.restaurantApi.repositories.OrdersRepository;
import com.project2.restaurantApi.services.MessageRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantApiApplication {

	@Autowired
	MessageRequestService messageRequestService;

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(){
		return args -> {
			System.out.println("Hello World!");
			//messageRequestService.sendSMS();

		};
	}
}
