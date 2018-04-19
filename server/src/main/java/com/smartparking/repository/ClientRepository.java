package com.smartparking.repository;

import com.smartparking.entity.Client;
import com.smartparking.entity.Provider;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByEmail(String email);

    @Query("SELECT c from Client c where (c.firstName like %?1% or c.lastName like %?1%" +
            " or c.email like %?1%) and c.activated = true")
    List<Client> findClientsByAnyMatch(String input);

    @Query("SELECT c from Client c where c.activated = true")
    List<Client> findLimitNumberOfClients(Pageable pageable);

    @Query("SELECT p from Provider p where p.id=?1")
    Provider findProviderById(Long id);

    @Query("SELECT c from Client c where (c.firstName like %?1% or c.role=?1) and c.activated = true")
    List<Client> findClientsByRole(String input);

}