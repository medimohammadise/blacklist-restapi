package com.finreach.codechallenge.filteringsystem.filteringsystem.validator;

import com.google.common.net.InetAddresses;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/*
   this class validates IP using guava library
 */
public class IPValidator implements ConstraintValidator<IPValidatorConstraint, String>{
    @Override
    public void initialize(IPValidatorConstraint ip) {
    }

    @Override
    public boolean isValid(String ip, ConstraintValidatorContext cxt) {
       return InetAddresses.isInetAddress(ip);
    }
}
