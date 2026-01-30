package ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ecommerce.entity.Product;
import ecommerce.repository.ProductRepository;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// 1. List
	@GetMapping
	public List<Product> list() {
		return productRepository.findAll();
	}
	
	// 2. Get One
	@GetMapping("/{id}")
	public Product getOne(@PathVariable Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id));
	}
	
	// 3. Create
	@PostMapping
	public Product create(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	// 4. Update
	@PutMapping("/{id}")
	public Product update(@PathVariable Long id, @RequestBody Product req) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id));
		
		product.setName(req.getName());
		product.setPrice(req.getPrice());
		product.setStock(req.getStock());
		
		return productRepository.save(product);
	}
	
	// 5. Delete
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
		}
		productRepository.deleteById(id);
	}
}
