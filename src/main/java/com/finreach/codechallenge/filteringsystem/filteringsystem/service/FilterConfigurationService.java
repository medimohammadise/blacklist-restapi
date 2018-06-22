package com.finreach.codechallenge.filteringsystem.filteringsystem.service;

import com.finreach.codechallenge.filteringsystem.filteringsystem.dto.FilterConfigurationDTO;
import com.finreach.codechallenge.filteringsystem.filteringsystem.mapper.FilterConfigurationMapper;
import com.finreach.codechallenge.filteringsystem.filteringsystem.model.FilterConfiguration;
import com.finreach.codechallenge.filteringsystem.filteringsystem.repository.FilterConfigurationRepository;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class FilterConfigurationService {
   FilterConfigurationRepository filterConfigurationRepository;
   FilterConfigurationMapper filterConfigurationMapper;
   public FilterConfigurationService(FilterConfigurationRepository filterConfigurationRepository,
                                     FilterConfigurationMapper filterConfigurationMapper){
      this.filterConfigurationRepository=filterConfigurationRepository;
      this.filterConfigurationMapper=filterConfigurationMapper;

   }
   public FilterConfigurationDTO addFilterConfiguration(FilterConfigurationDTO filterConfigurationDTO){

              return  filterConfigurationMapper.filterConfigurationToDTO(filterConfigurationRepository.save(filterConfigurationMapper.filterConfigurationDTOToFilterConfiguration(filterConfigurationDTO)));
   }
    public List<FilterConfigurationDTO> getFilterConfigurationList(){
        return filterConfigurationMapper.filterConfigurationToDTOList(filterConfigurationRepository.findAll());
    }

    public boolean checkIPInBlackListOptimal(String ip) throws UnknownHostException {
        InetAddress ipAddress=InetAddress.getByName(ip);
        Long lookupIp=ipToLong(ipAddress);
        return filterConfigurationRepository.findAll().parallelStream().map(ipFilterConfig->{
            try {
                if (lookupIp>=ipToLong(InetAddress.getByName(ipFilterConfig.getFromIP()))
                        && lookupIp<=ipToLong(InetAddress.getByName(ipFilterConfig.getToIP()))) {
                    return true;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return false;
        }).filter(item->item==true).findFirst().isPresent();

    }


    public long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }

    public List<FilterConfigurationDTO> deleteFilterConfigurationList(FilterConfigurationDTO filterConfigurationDTO) {
        List<FilterConfiguration> filterConfigurationListToBeDeleted=filterConfigurationRepository.findByFromIPAndToIP(filterConfigurationDTO.getFromIP(),filterConfigurationDTO.getToIP());
        filterConfigurationListToBeDeleted.stream().forEach(filterConfiguration-> {
            filterConfigurationRepository.delete(filterConfiguration);
        });
        return filterConfigurationMapper.filterConfigurationToDTOList(filterConfigurationListToBeDeleted);
    }
}
