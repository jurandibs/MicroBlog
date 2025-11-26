package dev.jurandibs.microblog.repositories.v2;

import dev.jurandibs.microblog.models.v2.AddressV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepositoryV2 extends JpaRepository<AddressV2, Long> {

}