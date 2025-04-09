package com.ccsw.tutorialgame.category;

import com.ccsw.tutorialgame.category.model.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-CATEGORY")
public interface CategoryClient {

    @GetMapping(value = "/category")
    List<CategoryDto> findAll();
}