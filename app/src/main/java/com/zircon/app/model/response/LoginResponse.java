package com.zircon.app.model.response;

import com.zircon.app.model.Society;
import com.zircon.app.model.User;

import java.util.Date;

/**
 * Created by jikoobaruah on 21/01/16.
 */
public class LoginResponse extends BaseResponse {

    public LoginBody body;

    public class LoginBody {
//        public Date created;
        public String token;
        public UserDetail userDetails;
        public Society society;
    }

    public static class UserDetail {
        public User user;
    }

}


