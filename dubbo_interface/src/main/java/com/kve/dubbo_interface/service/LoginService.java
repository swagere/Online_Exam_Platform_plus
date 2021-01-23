package com.kve.dubbo_interface.service;

import com.kve.dubbo_interface.model.Login;

/**
 * common
 */


public interface LoginService {
    //学号或者工号加密码
    Login LoginId(Login login);

    //手机号加密码
    Login loginPhone(Login login);
}
