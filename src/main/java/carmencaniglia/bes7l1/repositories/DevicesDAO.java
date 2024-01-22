package carmencaniglia.bes7l1.repositories;

import carmencaniglia.bes7l1.entities.Device;
import carmencaniglia.bes7l1.entities.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DevicesDAO extends JpaRepository<Device,Long> {
    Page<Device> findByState(State state, Pageable pageable);

    @Query("SELECT d FROM Device d WHERE d.user.id = :userId")
    Page<Device> findDevicesByUserId(Long userId, Pageable pageable);
}

