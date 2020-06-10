package com.grenader.reactive.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHistory {

    private String userId;
    private Map<Long, String> record;
    private String status;

}
