package com.grenader.reactive.server.service;


import com.grenader.reactive.server.model.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProfileService {

    Map<String, Profile> profiles = new HashMap<>();

    @PostConstruct
    public void setUp() {
        profiles.put("1", new Profile("1", "test1@test.com"));
        profiles.put("2", new Profile("2", "test2@test.com"));
    }

    public Mono<Collection<Profile>> all() {
        return Mono.fromCallable(() -> this.profiles.values());
    }

    public Mono<Profile> get(String id) {
        final Profile profile = this.profiles.get(id);
        if (profile == null)
            return Mono.empty();

        return Mono.fromCallable(() -> profile);
    }

    public Mono<Profile> update(String id, String email) {
        Profile profile = this.profiles.get(id);
        if (profile != null) {
            profile.setEmail(email);
            return Mono.fromCallable(() -> profile);
        } else
            return Mono.empty();
    }

    public Mono<Profile> delete(String id) {
        final Profile profile = this.profiles.get(id);
        if (profile == null)
            return Mono.empty();
        this.profiles.remove(id);
        return Mono.fromCallable(() -> profile);
    }

    public Profile create(Profile profile) {
        this.profiles.put(profile.getId(), profile);
        return profile;
    }

    public Mono<Profile> create(String email) {
        final String profileId = UUID.randomUUID().toString();
        final Profile profile = new Profile(profileId, email);
        this.profiles.put(profileId, profile);

        return Mono.fromCallable(() -> profile);
    }
}
