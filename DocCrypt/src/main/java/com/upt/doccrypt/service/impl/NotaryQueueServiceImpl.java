package com.upt.doccrypt.service.impl;

import com.upt.doccrypt.model.NotaryCandidate;
import com.upt.doccrypt.model.Role;
import com.upt.doccrypt.model.User;
import com.upt.doccrypt.repository.NotaryQueueRepository;
import com.upt.doccrypt.repository.RoleRepository;
import com.upt.doccrypt.repository.UserRepository;
import com.upt.doccrypt.service.NotaryQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class NotaryQueueServiceImpl implements NotaryQueueService {
    private final NotaryQueueRepository notaryQueueRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public NotaryQueueServiceImpl(NotaryQueueRepository notaryQueueRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.notaryQueueRepository = notaryQueueRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public NotaryCandidate addAsCandidate(NotaryCandidate notaryCandidate) {
        notaryCandidate.setPassword(passwordEncoder.encode(notaryCandidate.getPassword()));
        log.info("Added as candidate - notary: {} successfully registered", notaryCandidate);
        return notaryQueueRepository.save(notaryCandidate);
    }

    @Override
    public List<NotaryCandidate> getAll() {
        return notaryQueueRepository.findAll();
    }

    @Override
    public Boolean deleteCandidate(NotaryCandidate notaryCandidate) {
        if(notaryQueueRepository.findById(notaryCandidate.getId()) != null) {
            notaryQueueRepository.delete(notaryCandidate); return true;
        }else{
            log.info("Doesn't exist this candidate", notaryCandidate);
            return false;
        }
    }

    @Override
    public Boolean addCandidateToUsers(NotaryCandidate notaryCandidate) {
        if(userRepository.findByEmail(notaryCandidate.getEmail()) != null) {
            log.info("Email for candidate: {} already is taken", notaryCandidate);
            return false;
        }
        if(userRepository.findByUsername(notaryCandidate.getUsername()) !=null){
            notaryCandidate.setUsername(notaryCandidate.getUsername() + UUID.randomUUID());
        }

        Role roleUser = roleRepository.findByName("ROLE_NOTARY");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        User user = notaryCandidate.toUser();
        user.setRoles(userRoles);
        userRepository.save(user);
        log.info("IN register - notary: {} successfully registered", notaryCandidate);
        notaryQueueRepository.delete(notaryCandidate);
        return true;
    }

    @Override
    public NotaryCandidate getNotaryCandidateByEmail(String email) {
        return notaryQueueRepository.findByEmail(email);
    }

    @Override
    public NotaryCandidate getNotaryCandidateById(long id) {
        return notaryQueueRepository.findById(id);
    }
}
