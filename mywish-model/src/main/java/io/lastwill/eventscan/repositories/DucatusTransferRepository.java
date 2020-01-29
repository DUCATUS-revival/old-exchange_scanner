package io.lastwill.eventscan.repositories;

import io.lastwill.eventscan.model.DucatusTransfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DucatusTransferRepository extends CrudRepository<DucatusTransfer, Long> {
    List<DucatusTransfer> findAllByState(@Param("transferStatus") String status);
}
