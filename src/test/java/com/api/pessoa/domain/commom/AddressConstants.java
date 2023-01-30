package com.api.pessoa.domain.commom;

import com.api.pessoa.domain.model.Address;
import com.api.pessoa.domain.model.dto.AddressDTO;

import java.util.ArrayList;
import java.util.List;

import static com.api.pessoa.domain.commom.PersonConstants.PERSON_ENTITY_ID;

public class AddressConstants {

    public final static Address ADDRESS_ENTITY = new Address("City Land", "Street for", "6489233", "259", Boolean.FALSE);

    public final static AddressDTO ADDRESS = new AddressDTO("City Land", "Street for", "6489233", "259", Boolean.FALSE);

    public final static Address ADDRESS_ENTITY_ID = new Address(1L, "City Land", "Street for", "6489233", "259", Boolean.FALSE, PERSON_ENTITY_ID);

    public final static AddressDTO ADDRESS_ID = new AddressDTO(1L, "City Land", "Street for", "6489233", "259", Boolean.FALSE, 1L);

    public final static List<Address> ADDRESSES_ENTITY = new ArrayList<Address>() {{
        add(ADDRESS_ENTITY_ID);
    }};
}
