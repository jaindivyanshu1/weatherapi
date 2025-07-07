package com.example.weatherapi.repository;

import com.example.weatherapi.model.PincodeLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PincodeLocationRepository extends JpaRepository<PincodeLocation, String> {
}
