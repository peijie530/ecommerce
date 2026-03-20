package ecommerce.controller;

import java.util.List;

import ecommerce.dto.CreateProductRequest;
import ecommerce.dto.PatchProductRequest;
import ecommerce.dto.ProductResponse;
import ecommerce.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import ecommerce.entity.Product;

import ecommerce.service.ProductService;



@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}


	@GetMapping
	public List<ProductResponse> list() {
		return productService.getAllProducts()
				.stream() // 把集合放上「資料處理輸送帶」
				.map(this::toResponse) // 一對一轉換，每個元素都丟進後回傳新元素、把每個元素丟給 toResponse 這個方法
				.toList();
	}
	
	
	@GetMapping("/{id}")
	public ProductResponse getOne(@PathVariable Long id) {
		Product product = productService.getProduct(id);
		return toResponse(product);
	}
	
	// 新增商品 (僅限管理員)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")  // 需要 admin 權限才能呼叫此方法
	@ResponseStatus(HttpStatus.CREATED)  // 成功回傳 201
	public ProductResponse create(@RequestBody @Valid CreateProductRequest req) {
		Product product = productService.createProduct(req);
		return toResponse(product);
	}
	
	// 全量更新 (僅限管理員)
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ProductResponse update(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest req) {
		Product product = productService.updateProduct(id, req);
		return toResponse(product);
	}
	
	// // 局部更新 (僅限管理員)
	@PatchMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ProductResponse patch(@PathVariable Long id, @RequestBody @Valid PatchProductRequest req) {
		Product product = productService.patchProduct(id, req);
		return toResponse(product);
	}
	
	
	// 刪除商品 (僅限管理員)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)  // 成功回傳 204
	public void delete(@PathVariable Long id) {
		productService.deleteProduct(id);
	}
	
	// 輔助方法：將 Entity 轉為 Response DTO
    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(), 
            product.getName(), 
            product.getPrice(), 
            product.getStock(),
            product.getImageUrl() 
        );
    }
	
	
}
