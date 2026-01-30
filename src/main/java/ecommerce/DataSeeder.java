package ecommerce;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ecommerce.entity.Product;
import ecommerce.repository.ProductRepository;

@Configuration  // 這個 class 裡面會定義一些Bean，這個檔案是工廠的設定檔
public class DataSeeder {

	@Bean // 把這個 CommandLineRunner 建立出來，並在啟動時使用它
	CommandLineRunner init(ProductRepository repo) {   // CommandLineRunner : Spring Boot 開機後，自動跑一次的 main 後續程式
		return args -> {
			if (repo.count() == 0) {
				repo.save(new Product("iPhone 15", new BigDecimal("29999.00"), 10));
				repo.save(new Product("AirPods pro", new BigDecimal("5990.00"), 30));
				repo.save(new Product("MackBook Air", new BigDecimal("45900.00"), 5));
			}
		};
	}
}
