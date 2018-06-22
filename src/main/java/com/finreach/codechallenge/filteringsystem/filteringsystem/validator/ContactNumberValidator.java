package com.finreach.codechallenge.filteringsystem.filteringsystem.validator;

import com.google.common.net.InetAddresses;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContactNumberValidator implements ConstraintValidator<IPValidatorConstraint, String>{
    @Override
    public void initialize(IPValidatorConstraint ip) {
    }

    @Override
    public boolean isValid(String ip, ConstraintValidatorContext cxt) {

       return InetAddresses.isInetAddress(ip);
    }
}
