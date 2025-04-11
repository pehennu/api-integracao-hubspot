package com.br.integracaorest.repository;

import com.br.integracaorest.model.HubspotToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<HubspotToken, Long> {
    HubspotToken findFirstByOrderByIdDesc();
}
