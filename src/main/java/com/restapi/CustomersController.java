package com.restapi;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @GetMapping
    public String getCustomer(){
        return "http GET request to get the customer details";
    }

    @GetMapping(path = "/{customerId}")
    public String getCustomerId(@PathVariable String customerId){
        return "GET request to get the customerId " + customerId ;
    }

    @PostMapping
    public String createCustomer(){
        return "http POST request to create the customer";
    }
    @PutMapping
    public String updateCustomer(){
        return "http GET request to update the customer details";
    }
    @DeleteMapping
    public String deleteCustomer(){
        return "http GET request to delete the customer";
    }

}
