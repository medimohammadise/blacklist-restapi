package com.finreach.codechallenge.filteringsystem.filteringsystem.service;

import com.finreach.codechallenge.filteringsystem.filteringsystem.dto.FilterConfigurationDTO;
import com.finreach.codechallenge.filteringsystem.filteringsystem.mapper.FilterConfigurationMapper;
import com.finreach.codechallenge.filteringsystem.filteringsystem.model.FilterConfiguration;
import com.finreach.codechallenge.filteringsystem.filteringsystem.repository.FilterConfigurationRepository;
import com.finreach.codechallenge.filteringsystem.filteringsystem.validator.IPValidatorConstraint;
import com.google.common.net.InetAddresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class FilterConfigurationService {
    Logger log = LoggerFactory.getLogger(getClass());
    FilterConfigurationRepository filterConfigurationRepository;
    FilterConfigurationMapper filterConfigurationMapper;

    public FilterConfigurationService(FilterConfigurationRepository filterConfigurationRepository,
                                      FilterConfigurationMapper filterConfigurationMapper) {
        this.filterConfigurationRepository = filterConfigurationRepository;
        this.filterConfigurationMapper = filterConfigurationMapper;

    }

    public FilterConfigurationDTO addFilterConfiguration(FilterConfigurationDTO filterConfigurationDTO) {

        return filterConfigurationMapper.filterConfigurationToDTO(filterConfigurationRepository.save(filterConfigurationMapper.filterConfigurationDTOToFilterConfiguration(filterConfigurationDTO)));
    }

    public List<FilterConfigurationDTO> getFilterConfigurationList() {
        return filterConfigurationMapper.filterConfigurationToDTOList(filterConfigurationRepository.findAll());
    }


    public boolean checkIPInBlackListOptimal(String ip) throws UnknownHostException {
        //I have to check this case here ! IPValidator did not work here haha.
        if (!InetAddresses.isInetAddress(ip)) throw new RuntimeException("you enetered invalid IP!");
        InetAddress ipAddress = InetAddress.getByName(ip);
        Long lookupIp = ipToLong(ipAddress);
        //I am using parallel stream for checking IP in range using multiple threads.Let's ask Java8 to do thats
        return filterConfigurationRepository.findAll().parallelStream().map(ipFilterConfig -> {
            try {
                //I did not consider other lazy methods, I think converting IP into the long is the best method!
                if (lookupIp >= ipToLong(InetAddress.getByName(ipFilterConfig.getFromIP()))
                        && lookupIp <= ipToLong(InetAddress.getByName(ipFilterConfig.getToIP()))) {
                    return true;
                }
            } catch (UnknownHostException e) {
                log.debug("facing error in ip checking, there might be some invalid ip already in database");
            }
            return false;
        }).filter(item -> item == true).findFirst().isPresent();

    }

    /*
       this method converts Ip into long value for optimized ip comparsion for checking in the blackList
    */
    private long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }

    public List<FilterConfigurationDTO> deleteFilterConfigurationList(FilterConfigurationDTO filterConfigurationDTO) {
        List<FilterConfiguration> filterConfigurationListToBeDeleted = filterConfigurationRepository.findByFromIPAndToIP(filterConfigurationDTO.getFromIP(), filterConfigurationDTO.getToIP());
        if (filterConfigurationListToBeDeleted.size()==0) throw new RuntimeException("This ip range does not exist in the black list");
        filterConfigurationListToBeDeleted.stream().forEach(filterConfiguration -> {
            filterConfigurationRepository.delete(filterConfiguration);
        });
        return filterConfigurationMapper.filterConfigurationToDTOList(filterConfigurationListToBeDeleted);
    }
}
