package com.ecommerce.ecomgrade.service.impl;

import com.ecommerce.ecomgrade.exception.APIException;
import com.ecommerce.ecomgrade.model.Address;
import com.ecommerce.ecomgrade.model.User;
import com.ecommerce.ecomgrade.payload.request.AddressRequest;
import com.ecommerce.ecomgrade.repository.AddressRepository;
import com.ecommerce.ecomgrade.security.SecurityUtils;
import com.ecommerce.ecomgrade.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired private AddressRepository addressRepository;
    @Autowired private SecurityUtils securityUtils;

    @Override
    public List<Address> getAddressesForCurrentUser() {
        return addressRepository.findByUser(securityUtils.getCurrentUser());
    }

    @Override
    public Address addAddress(AddressRequest req) {
        User user = securityUtils.getCurrentUser();
        Address address = new Address();
        address.setFullName(req.getFullName());
        address.setStreet(req.getStreet());
        address.setCity(req.getCity());
        address.setState(req.getState());
        address.setZipCode(req.getZipCode());
        address.setCountry(req.getCountry());
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new APIException("Address not found."));
        if (!address.getUser().getId().equals(securityUtils.getCurrentUser().getId()))
            throw new APIException("You cannot delete this address.");
        addressRepository.deleteById(id);
    }
}
