package com.api.pessoa.domain.repository;

import com.api.pessoa.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByPerson_IdAndAndIsMainAddress(Long personId, Boolean isMainAddress);
}
