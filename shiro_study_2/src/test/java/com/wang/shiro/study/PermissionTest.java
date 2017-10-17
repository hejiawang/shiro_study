package com.wang.shiro.study;

import org.junit.Assert;
import org.junit.Test;

public class PermissionTest extends BaseTest {

	@Test  
	public void testIsPermitted() {  
	    login("classpath:shiro-permission.ini", "zhang", "123");  
	    //判断拥有权限：user:create  
	    Assert.assertTrue(subject().isPermitted("user:create"));  
	    //判断拥有权限：user:update and user:delete  
	    Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));  
	    //判断没有权限：user:view  
	    Assert.assertFalse(subject().isPermitted("user:view"));  
	}   
	
	@Test//(expected = UnauthorizedException.class)  
	public void testCheckPermission () {  
	    login("classpath:shiro-permission.ini", "zhang", "123");  
	    //断言拥有权限：user:create  
	    subject().checkPermission("user:create");  
	    //断言拥有权限：user:delete and user:update  
	    subject().checkPermissions("user:delete", "user:update");  
	    //断言拥有权限：user:view 失败抛出异常  
	    //subject().checkPermissions("user:view");  
	}   
	
	@Test
	public void test() {  
	    login("classpath:shiro-permission.ini", "zhang", "123"); 
	    
	    subject().checkPermissions("system:user:update");  
	    subject().checkPermissions("system:user:update,delete");  
	    
	    subject().checkPermissions("system:user:create,delete,update:view");
	    subject().checkPermissions("system:user:*");  
	    subject().checkPermissions("system:user");
	    
	    subject().checkPermissions("roletest");
	}
}
