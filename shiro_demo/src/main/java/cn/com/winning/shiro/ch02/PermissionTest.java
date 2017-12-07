package cn.com.winning.shiro.ch02;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
/**
 * 权限信息测试
 * @author thinkpad
 * 
 * Shiro提供了isPermitted和isPermittedAll用于判断用户是否拥有某个权限或所有权限
 *
 */
public class PermissionTest {

	
	@Test
	public void testPermission(){
		login("shiro-permission.ini", "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		//Shiro提供了isPermitted和isPermittedAll用于判断用户是否拥有某个权限或所有权限
		System.out.println(subject.isPermitted("user:create"));
		System.out.println(subject.isPermitted("user:delete"));
		System.out.println(subject.isPermittedAll("user:create","user:delete"));
		subject.logout();
		login("shiro-permission.ini", "wang", "123");
	    subject = SecurityUtils.getSubject();
		System.out.println(subject.isPermitted("user:create"));
		System.out.println(subject.isPermitted("user:delete"));
	}
	
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
}
