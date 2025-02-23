package com.mohil_bansal.day1.day1.service.impl;

import com.mohil_bansal.day1.day1.DTO.AddressDTO;
import com.mohil_bansal.day1.day1.DTO.AddressDTO;
import com.mohil_bansal.day1.day1.entity.Address;
import com.mohil_bansal.day1.day1.entity.Address;
import com.mohil_bansal.day1.day1.repo.AddressRepository;
import com.mohil_bansal.day1.day1.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;


    @Override
    public AddressDTO getAddressById(Long id) {
        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(addressRepository.findById(id).get(), addressDTO);
        return addressDTO;
    }

    @Override
    public List<AddressDTO> getAllAddress() {
        List<Address> Address = addressRepository.findAll();
        List<AddressDTO> AddressDTOList = new ArrayList<>();
        Address.forEach(n -> {
            AddressDTO AddressDTO = new AddressDTO();
            BeanUtils.copyProperties(Address, AddressDTO);
            AddressDTOList.add(AddressDTO);
        });
        return AddressDTOList;
    }

    @Override
    public AddressDTO createAddress(AddressDTO Address) {
        System.out.println(Address);
        Address entityAdress = new Address();
        BeanUtils.copyProperties(Address, entityAdress);
        addressRepository.save(entityAdress);
        return Address;
    }

    @Override
    public AddressDTO updateAddress(AddressDTO departmentDTO, Long id) {
        Address address = addressRepository.findById(id).get();
        AddressDTO addressDTO = new AddressDTO();
        if (address != null) {
            addressRepository.save(address);
        }
        BeanUtils.copyProperties(address, addressDTO);
        return addressDTO;
    }

    @Override
    public AddressDTO deleteAddress(Long id) {

        Address address = addressRepository.findById(id).get();
        AddressDTO addressDTO = new AddressDTO();
        if (address != null) {
            addressRepository.delete(address);
        }
        BeanUtils.copyProperties(address, addressDTO);
        return addressDTO;
    }
}