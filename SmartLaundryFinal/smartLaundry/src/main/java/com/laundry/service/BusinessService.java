package com.laundry.service;

import com.laundry.entity.Business;

public interface BusinessService {

    Business getBusinessProfile();

    Business updateBusinessName(String businessName);
}