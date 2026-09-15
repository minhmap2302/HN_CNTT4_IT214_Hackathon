package org.example.bookservice.client;

import org.example.bookservice.dto.AuthorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "author-service")
public interface AuthorClient {

    @GetMapping("/api/authors/{id}")
    AuthorResponse getAuthorById(@PathVariable("id") Long id);
}
