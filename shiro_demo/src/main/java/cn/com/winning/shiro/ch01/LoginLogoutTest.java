package cn.com.winning.shiro.ch01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import junit.framework.Assert;

public class LoginLogoutTest {

	@Test
	public void testHelloWorld(){
		Factory<SecurityManager> factory = 
				new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = 
				new UsernamePasswordToken("zhang", "123");
		
		try {
			subject.login(token);
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		subject.logout();
	}
	
	@Test
	public void testRealm() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = 
				new UsernamePasswordToken("zhang", "123");
		
		try {
			subject.login(token);
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		subject.logout();
		
	}
	
	@Test
	public void testMultiRealm() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini");
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		
		try {
			subject.login(token);
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		subject.logout();
	}
	
	
	private void login(String configFile){
		//1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager  
	    Factory<org.apache.shiro.mgt.SecurityManager> factory =  
	            new IniSecurityManagerFactory("classpath:"+configFile);  
	  
	    //2、得到SecurityManager实例 并绑定给SecurityUtils  
	    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();  
	    SecurityUtils.setSecurityManager(securityManager);  
	  
	    //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）  
	    Subject subject = SecurityUtils.getSubject();  
	    UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");  
	  
	    subject.login(token);  
	}
	
	@Test
	public void testAllSuccessfullStrategyWithSussess(){
		login("shiro-authenticator-all-success.ini");
		Subject subject = SecurityUtils.getSubject();  
		  
	    //得到一个身份集合，其包含了Realm验证成功的身份信息  
	    PrincipalCollection principalCollection = subject.getPrincipals();  
	    Assert.assertEquals(2, principalCollection.asList().size()); 
	}
	
	
	@Test
	public void testAllDFailfullStrategyWithSussess(){
		login("shiro-authenticator-all-fail.ini");
		Subject subject = SecurityUtils.getSubject();  
		  
	    //得到一个身份集合，其包含了Realm验证成功的身份信息  
	    PrincipalCollection principalCollection = subject.getPrincipals();  
	    Assert.assertEquals(2, principalCollection.asList().size()); 
	}
}
