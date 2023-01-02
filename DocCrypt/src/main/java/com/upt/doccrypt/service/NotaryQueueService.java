package com.upt.doccrypt.service;

import com.upt.doccrypt.model.NotaryCandidate;
import com.upt.doccrypt.model.User;

import java.util.List;

public interface NotaryQueueService {
    NotaryCandidate addAsCandidate(NotaryCandidate notaryCandidate);
    List<NotaryCandidate> getAll();
    Boolean deleteCandidate(NotaryCandidate notaryCandidate);

    Boolean addCandidateToUsers(NotaryCandidate notaryCandidate);

    NotaryCandidate getNotaryCandidateByEmail(String email);

    NotaryCandidate getNotaryCandidateById(long id);
}
