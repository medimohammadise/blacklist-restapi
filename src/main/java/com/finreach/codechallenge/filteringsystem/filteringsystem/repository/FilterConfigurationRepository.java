package com.finreach.codechallenge.filteringsystem.filteringsystem.repository;

import com.finreach.codechallenge.filteringsystem.filteringsystem.model.FilterConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@org.springframework.stereotype.Repository
public interface FilterConfigurationRepository extends JpaRepository<FilterConfiguration,Long> {
    /*we are using this method when we want to delete*/
    List<FilterConfiguration> findByFromIPAndToIP(String fromIP, String toIP);
}
