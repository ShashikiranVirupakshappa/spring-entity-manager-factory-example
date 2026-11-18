package com.javabro.springentitymanagerfactoryexample.controllers;

import com.javabro.springentitymanagerfactoryexample.model.Customer;
import com.javabro.springentitymanagerfactoryexample.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/V1/{customerId}")
    public Customer getCustomerByIdV1(@PathVariable Long customerId) {
        return customerService.getCustomerByIdV1(customerId);
    }

    @GetMapping("/customers/V2/{customerId}")
    public Customer getCustomerByIdV2(@PathVariable Long customerId) {
        return customerService.getCustomerByIdV2(customerId);
    }
}
