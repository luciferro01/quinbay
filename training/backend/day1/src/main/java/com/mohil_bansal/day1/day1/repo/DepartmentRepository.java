package com.mohil_bansal.day1.day1.repo;

import com.mohil_bansal.day1.day1.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
