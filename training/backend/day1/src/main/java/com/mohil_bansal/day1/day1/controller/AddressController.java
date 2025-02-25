package com.mohil_bansal.day1.day1.controller;

import com.mohil_bansal.day1.day1.DTO.AddressDTO;
import com.mohil_bansal.day1.day1.DTO.StudentDTO;
import com.mohil_bansal.day1.day1.service.AddressService;
import com.mohil_bansal.day1.day1.service.impl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressServiceImpl service;


    @GetMapping("/getAllAddress")
    public List<AddressDTO> getAllAddress(){
        return service.getAllAddress();
    }

    @PostMapping(value = "/createAddress",consumes = "application/json")
    public AddressDTO createAddress(@RequestBody AddressDTO address) {
        System.out.println("here");
        return service.createAddress(address);
    }

    @GetMapping("/getAddressById")
    public AddressDTO getAddressById(@RequestParam String id){

        return service.getAddressById(Long.parseLong(id));
    }

    @DeleteMapping("/deleteAddress/{id}")
    public AddressDTO deleteAddress(@RequestParam Long id){
        return service.deleteAddress(id);

    }

}

