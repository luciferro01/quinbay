package com.mohil_bansal.day1.day1.repo;

import com.mohil_bansal.day1.day1.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
