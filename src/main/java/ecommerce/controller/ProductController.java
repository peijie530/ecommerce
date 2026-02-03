package ecommerce.controller;

import java.util.List;

import ecommerce.dto.CreateProductRequest;
import ecommerce.dto.PatchProductRequest;
import ecommerce.dto.ProductResponse;
import ecommerce.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public List<ProductResponse> list() {
		return productRepository.findAll()
				.stream()
				.map(this::toResponse)
				.toList();
	}
	
	// 2. Get One
	@GetMapping("/{id}")
	public ProductResponse getOne(@PathVariable Long id) {
		Product product = getProductOrThrow(id);
		return toResponse(product);
	}
	
	
	// 3. Create
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) // 建立資源回 201
	public ProductResponse create(@RequestBody @Valid CreateProductRequest req) {
		Product product = new Product(req.getName(), req.getPrice(), req.getStock());
		Product saved = productRepository.save(product);
		return toResponse(saved);
	}
	
	// 4. Update
	@PutMapping("/{id}")
	public ProductResponse update(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest req) {
		Product product = getProductOrThrow(id);
		
		product.setName(req.getName());
		product.setPrice(req.getPrice());
		product.setStock(req.getStock());
		
		Product saved = productRepository.save(product);
		return toResponse(saved);
	}
	
	// Patch 部分更新
	@PatchMapping("/{id}")
	public ProductResponse patch(@PathVariable Long id, @RequestBody @Valid PatchProductRequest req) {
		Product product = getProductOrThrow(id);
		
		if (req.getName() != null) {
			product.setName(req.getName());
		}
		if (req.getPrice() != null) {
			product.setPrice(req.getPrice());
		}
		if (req.getStock() != null) {
			product.setStock(req.getStock());
		}
		
		Product saved = productRepository.save(product);
		return toResponse(saved);
	}
	
	
	// 5. Delete
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)   // 刪除成功回 204
	public void delete(@PathVariable Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id);
		}
		productRepository.deleteById(id);
	}
	
	
	private ProductResponse toResponse(Product product) {
		return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
	}
	
	private Product getProductOrThrow(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id)); 
	}
}
