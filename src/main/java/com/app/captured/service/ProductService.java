package com.app.captured.service;

import java.util.List;

import com.app.captured.entity.Product;
import com.app.captured.dto.ProductDTO;
import com.app.captured.entity.ProductStatus;

public interface ProductService {

	public Product addProductToCatalog(String token, Product product);

	public Product getProductFromCatalogById(Integer id);

	public String deleteProductFromCatalog(Integer id);

	public Product updateProductInCatalog(Product product);
	
	public List<Product> getAllProductsInCatalog();
	
	public List<ProductDTO> getAllProductsOfSeller(Integer id);
	
	
	public List<ProductDTO> getProductsOfStatus(ProductStatus status);
	
	
	
	public Product updateProductQuantityWithId(Integer id,ProductDTO prodDTO);

}