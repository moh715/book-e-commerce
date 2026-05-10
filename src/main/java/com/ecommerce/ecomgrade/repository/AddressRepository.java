package com.ecommerce.ecomgrade.repository;

import com.ecommerce.ecomgrade.model.Address;
import com.ecommerce.ecomgrade.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
