package com.ccsw.tutorialgame.author;

import com.ccsw.tutorialgame.author.model.AuthorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-AUTHOR")
public interface AuthorClient {

    @GetMapping(value = "/author")
    List<AuthorDto> findAll();
}