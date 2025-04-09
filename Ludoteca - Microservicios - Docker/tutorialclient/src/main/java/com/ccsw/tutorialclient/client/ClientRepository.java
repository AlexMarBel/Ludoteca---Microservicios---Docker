package com.ccsw.tutorialclient.client;

import com.ccsw.tutorialclient.client.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    boolean existsByName(String name);
}