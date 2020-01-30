package io.lastwill.eventscan.repositories;

import io.lastwill.eventscan.model.UserAddressExchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface UserAddressExchangeRepository extends CrudRepository<UserAddressExchange, Long> {
    @Query("select c from UserAddressExchange c where c.ethAddress in :addresses")
    List<UserAddressExchange> findByEthAddressesList(@Param("addresses") Collection<String> addresses);

    @Query("select c from UserAddressExchange c where c.btcAddress in :addresses")
    List<UserAddressExchange> findByBtcAddressesList(@Param("addresses") Collection<String> addresses);
}
