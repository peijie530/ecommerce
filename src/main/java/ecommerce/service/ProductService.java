package ecommerce.service;

import java.util.List;

import ecommerce.dto.CreateProductRequest;
import ecommerce.dto.PatchProductRequest;
import ecommerce.dto.UpdateProductRequest;
import ecommerce.entity.Product;


public interface ProductService {

	Product createProduct(CreateProductRequest request);
	Product getProduct(Long id);
	List<Product> getAllProducts();
	Product updateProduct(Long id, UpdateProductRequest request);
	Product patchProduct(Long id, PatchProductRequest request);
	void deleteProduct(Long id);
}
