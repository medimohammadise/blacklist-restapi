package com.finreach.codechallenge.filteringsystem.filteringsystem.dto;

import com.finreach.codechallenge.filteringsystem.filteringsystem.validator.IPValidatorConstraint;

import javax.validation.constraints.NotNull;

//@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class FilterConfigurationDTO {
    @NotNull
    @IPValidatorConstraint
    String fromIP;

    @NotNull
    @IPValidatorConstraint
    String toIP;

    public FilterConfigurationDTO(){

    }

    public FilterConfigurationDTO(String fromIP, String toIP) {
        this.fromIP = fromIP;
        this.toIP = toIP;
    }

    public String getFromIP() {
        return fromIP;
    }

    public void setFromIP(String fromIP) {
        this.fromIP = fromIP;
    }

    public String getToIP() {
        return toIP;
    }

    public void setToIP(String toIP) {
        this.toIP = toIP;
    }

}
