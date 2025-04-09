package com.ccsw.tutorialloan.game;

import com.ccsw.tutorialloan.game.model.GameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-GAME")
public interface GameClient {

    @GetMapping(value = "/game")
    List<GameDto> findAll();
}