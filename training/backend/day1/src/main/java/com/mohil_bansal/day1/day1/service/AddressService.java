package com.mohil_bansal.day1.day1.service;


import com.mohil_bansal.day1.day1.DTO.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    AddressDTO getAddressById(Long id);

    List<AddressDTO> getAllAddress();

    AddressDTO updateAddress(AddressDTO AddressDTO, Long id);

    AddressDTO deleteAddress(Long id);

    AddressDTO createAddress(AddressDTO AddressDTO);

}
