package com.wang.shiro.study;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.junit.Test;

public class PasswordTest extends BaseTest {

	@Test
    public void testPasswordServiceWithMyRealm() {
        login("classpath:shiro-passwordservice.ini", "wu", "123");
    }
	
	@Test
    public void testRetryLimitHashedCredentialsMatcherWithMyRealm() {
        /*for(int i = 1; i <= 5; i++) {
            try {
                login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini", "liu", "234");
            } catch (Exception e) {ignore}
        }*/
        login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini", "liu", "234");
    }
}
