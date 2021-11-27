package com.education.business.session;

import com.education.auth.session.UserSession;

/**
 * @author zengjintao
 * @create_at 2021年11月27日 0027 14:56
 * @since version 1.6.7
 */
public class StudentSession extends UserSession {

    public StudentSession(String userId) {
        super(userId);
    }
}
