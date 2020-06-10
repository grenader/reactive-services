package com.grenader.reactive.server.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Profile {
    private String id;
    private String userId;
    private String email;

    public Profile(String id, String email) {
        this.id = id;
        this.email = email;
    }
}