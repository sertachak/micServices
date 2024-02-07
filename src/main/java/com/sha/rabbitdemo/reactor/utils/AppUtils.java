package com.sha.rabbitdemo.reactor.utils;

import com.sha.rabbitdemo.reactor.dto.ProductDto;
import com.sha.rabbitdemo.reactor.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDto toProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product toProduct(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
