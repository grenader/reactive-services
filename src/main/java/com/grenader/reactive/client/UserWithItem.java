package com.grenader.reactive.client;

import com.grenader.reactive.server.model.Profile;
import com.grenader.reactive.server.model.User;
import com.grenader.reactive.server.model.UserHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserWithItem {

    private User user;
    private Profile profile;
    private UserHistory history;

}
