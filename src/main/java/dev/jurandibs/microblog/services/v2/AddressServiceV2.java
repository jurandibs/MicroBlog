package dev.jurandibs.microblog.services.v2;

import dev.jurandibs.microblog.models.v2.AddressV2;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AddressServiceV2 {
    AddressV2 save (final AddressV2 addressV2);
    List<AddressV2> getAll();

    AddressV2 get(Long id);

    AddressV2 update(Long id, AddressV2 addressV2);

    void delete(Long id);
}
