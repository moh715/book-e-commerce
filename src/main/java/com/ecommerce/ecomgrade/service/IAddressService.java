package com.ecommerce.ecomgrade.service;

import com.ecommerce.ecomgrade.model.Address;
import com.ecommerce.ecomgrade.payload.request.AddressRequest;
import java.util.List;

public interface IAddressService {
    List<Address> getAddressesForCurrentUser();
    Address addAddress(AddressRequest request);
    void deleteAddress(Long id);
}
