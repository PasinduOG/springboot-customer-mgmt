package com.java.example.controller;

import com.java.example.dto.ApiResponseDto;
import com.java.example.model.Customer;
import com.java.example.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Customer Controller", description = "To manage customer details")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerRepository repository;

    @PostMapping("/add-customer")
    ResponseEntity<ApiResponseDto> addCustomer(@RequestBody Customer customer) {
        try {
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponseDto("Customer data is required", false)
                );
            }
            repository.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponseDto("Customer Added Successfully", true, customer));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto("Failed to save customer: " + e.getMessage(), false));
        }
    }
}
