package cn.com.winning.shiro.ch02;

import java.util.Arrays;

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

public class RoleTest {

	private void login(String configFile,String username,String password){
		//1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager  
	    Factory<org.apache.shiro.mgt.SecurityManager> factory =  
	            new IniSecurityManagerFactory("classpath:"+configFile);  
	  
	    //2、得到SecurityManager实例 并绑定给SecurityUtils  
	    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();  
	    SecurityUtils.setSecurityManager(securityManager);  
	  
	    //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）  
	    Subject subject = SecurityUtils.getSubject();  
	    UsernamePasswordToken token = new UsernamePasswordToken(username,password);  
	  
	    subject.login(token);  
	}
	
	@Test
	public void testUserHasRole(){
		login("shiro-role.ini","zhang","123");
		Subject subject = SecurityUtils.getSubject();  
		//拥有哪些角色
		Assert.assertEquals(true,subject.hasAllRoles(Arrays.asList("role1","role2")));
		//拥有哪一个角色
		Assert.assertEquals(true,subject.hasRole("role1"));
		//检查拥有某个角色
		subject.checkRole("role1");
	}
	
	
	@Test
	public void testAllDFailfullStrategyWithSussess(){
		login("shiro-authenticator-all-fail.ini","zhang","123");
	}
}
