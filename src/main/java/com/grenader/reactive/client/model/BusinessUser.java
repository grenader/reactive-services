package com.grenader.reactive.client.model;

import com.grenader.reactive.server.model.Profile;
import com.grenader.reactive.server.model.User;
import com.grenader.reactive.server.model.UserHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BusinessUser {
    private User user;
    private Profile profile;
    private UserHistory history;
}
