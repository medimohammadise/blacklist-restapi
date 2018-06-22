package com.finreach.codechallenge.filteringsystem.filteringsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
//@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class FilterConfiguration {
    @Id
    @GeneratedValue
    Long id;

    @Column
    String fromIP;

    @Column
    String toIP;


    public FilterConfiguration(){

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
