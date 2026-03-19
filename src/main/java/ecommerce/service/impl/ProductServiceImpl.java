package ecommerce.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ecommerce.dto.CreateProductRequest;
import ecommerce.dto.UpdateProductRequest;
import ecommerce.entity.Product;
import ecommerce.repository.ProductRepository;
import ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	@Transactional
	public Product createProduct(CreateProductRequest request) {
		Product product = new Product(request.getName(), request.getPrice(), request.getStock());
		return productRepository.save(product);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Product getProduct(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到該商品"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@Override
	@Transactional
	public Product updateProduct(Long id, UpdateProductRequest request) {
		Product product = getProduct(id);
		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		// 若有 imageUrl 欄位，請補上
		product.setImageUrl(request.getImageUrl());
		return productRepository.save(product);
	}
	
	

}