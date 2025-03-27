package org.lessons.java.ticket_platform.service;

import org.lessons.java.ticket_platform.model.Operator;
import org.lessons.java.ticket_platform.model.User;
import org.lessons.java.ticket_platform.repository.OperatorRepository;
import org.lessons.java.ticket_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private UserRepository userRepository;

    public Operator getLoggedOperator(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return operatorRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Operator not found for user: " + username));
    }

}
