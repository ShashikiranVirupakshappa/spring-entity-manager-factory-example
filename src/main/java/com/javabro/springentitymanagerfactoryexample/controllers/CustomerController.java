package com.javabro.springentitymanagerfactoryexample.controllers;

import com.javabro.springentitymanagerfactoryexample.model.Customer;
import com.javabro.springentitymanagerfactoryexample.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
       return customerService.getAllCustomers();
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.persistCustomer(customer);
    }
}
