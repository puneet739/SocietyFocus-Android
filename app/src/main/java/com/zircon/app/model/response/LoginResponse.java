package com.zircon.app.model.response;

import com.zircon.app.model.User;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class LoginResponse extends BaseResponse {

    public LoginBody body;

    public class LoginBody {
        public long created;
        public String token;
        public UserDetail userDetails;
    }

    public static class UserDetail {
        public User user;
    }

}


