package com.finreach.codechallenge.filteringsystem.filteringsystem.mapper;


import com.finreach.codechallenge.filteringsystem.filteringsystem.dto.FilterConfigurationDTO;
import com.finreach.codechallenge.filteringsystem.filteringsystem.model.FilterConfiguration;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring", uses = {})
public interface FilterConfigurationMapper {
     FilterConfigurationDTO filterConfigurationToDTO(FilterConfiguration filterConfiguration);
     FilterConfiguration filterConfigurationDTOToFilterConfiguration(FilterConfigurationDTO filterConfigurationDTO);

     List<FilterConfigurationDTO> filterConfigurationToDTOList(List<FilterConfiguration> filterConfigurationList);
}
