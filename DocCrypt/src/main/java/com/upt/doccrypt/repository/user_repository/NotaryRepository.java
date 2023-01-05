package com.upt.doccrypt.repository.user_repository;


import com.upt.doccrypt.model.user.Notary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaryRepository extends JpaRepository<Notary, Long> {
    Notary findByEmail(String email);
    Notary findByUsername(String username);
    Notary findById(long id);
    Boolean existsByUsername(String username);
}
