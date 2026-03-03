package com.oceanview.reservation.service.strategy;

public interface AuthenticationStrategy {
    Object authenticate(String username, String password);
}
