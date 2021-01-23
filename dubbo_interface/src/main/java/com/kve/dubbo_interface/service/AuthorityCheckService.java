package com.kve.dubbo_interface.service;

/**
 * common
 */
public interface AuthorityCheckService {
    void checkStudentAuthority(Object user);
    void checkTeacherAuthority(Object user);
    void checkLoginStatus(Object user);
}
