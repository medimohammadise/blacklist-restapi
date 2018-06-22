package com.finreach.codechallenge.filteringsystem.filteringsystem.rest;

import com.finreach.codechallenge.filteringsystem.filteringsystem.dto.FilterConfigurationDTO;
import com.finreach.codechallenge.filteringsystem.filteringsystem.service.FilterConfigurationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("/api/filterConfiguration")
public class FilterConfigurationResource {
   FilterConfigurationService filterConfigurationService;
   public FilterConfigurationResource(FilterConfigurationService filterConfigurationService){
      this.filterConfigurationService=filterConfigurationService;

   }
   @RequestMapping(method=RequestMethod.PUT)
   public FilterConfigurationDTO saveFilterConfiguration(@Validated @RequestBody FilterConfigurationDTO filterConfigurationDTO){
       return filterConfigurationService.addFilterConfiguration(filterConfigurationDTO);

      
   }

   @RequestMapping(method=RequestMethod.GET)
   public List<FilterConfigurationDTO> getFilterConfigurationList(){
      return filterConfigurationService.getFilterConfigurationList();

   }


   @RequestMapping(method=RequestMethod.GET,value = "/{lookupIp}")
   public Boolean checkIpInBlackListOptimalMethod(@PathVariable String lookupIp) throws UnknownHostException {
      return filterConfigurationService.checkIPInBlackListOptimal(lookupIp);

   }


   @RequestMapping(method=RequestMethod.DELETE)
   public  List<FilterConfigurationDTO> getFilterConfigurationList(@Validated @RequestBody FilterConfigurationDTO filterConfigurationDTO){
      return filterConfigurationService.deleteFilterConfigurationList(filterConfigurationDTO);

   }
}
