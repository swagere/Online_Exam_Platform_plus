package com.kve.dubbo_interface.service;

import org.springframework.mail.MailSendException;

/**
 * common
 */

public interface SendMailService {

    void sendEmail(String receiver) throws MailSendException;

    Boolean verification(String email, String unverifiedCode);
}
