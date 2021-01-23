package com.kve.dubbo_provider_personal.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kve.dubbo_interface.config.exception.CustomException;
import com.kve.dubbo_interface.config.exception.CustomExceptionType;
import com.kve.dubbo_interface.service.AuthorityCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * provider_personal
 */
@Slf4j
@Service
@Component
public class AuthorityCheckServiceImpl implements AuthorityCheckService {

    @Override
    public void checkStudentAuthority(Object user) {
        Map userInfo = (Map)user;
        int authority = Integer.parseInt(userInfo.get("authority").toString());
        if (authority != 0) {
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "该用户无权限");
        }
    }

    @Override
    public void checkTeacherAuthority(Object user) {
        Map userInfo = (Map)user;
        int authority = Integer.parseInt(userInfo.get("authority").toString());
        if (authority != 1) {
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "该用户无权限");
        }
    }

    @Override
    public void checkLoginStatus(Object user) {
        Map userInfo = (Map)user;
        int authority = Integer.parseInt(userInfo.get("authority").toString());
        if (authority != 1 && authority != 0) {
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "该用户无权限");
        }
    }
}
