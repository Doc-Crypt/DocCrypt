package com.upt.doccrypt.service;

import com.upt.doccrypt.model.NotaryCandidate;
import com.upt.doccrypt.model.user.Notary;
import com.upt.doccrypt.model.user.User;

import java.util.List;

public interface NotaryService {
    Notary register(Notary notary);
    List<Notary> getAll();
    Notary findByUsername(String username);
    Boolean containUserByEmail(String email);
    Notary getUserByEmail(String email);
}
