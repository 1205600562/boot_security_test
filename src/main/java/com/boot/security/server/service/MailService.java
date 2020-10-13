package com.boot.security.server.service;

import com.boot.security.server.model.Mail;

import java.util.List;

public interface MailService {

	void save(Mail mail, List<String> toUser);
}
