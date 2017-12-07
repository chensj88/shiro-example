package cn.com.winning.shiro.ch01.trealm;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
/**
 * 自定义realm认证策略
 *  只能一个认证成功
 * @author thinkpad
 *
 */
public class OnlyOneAuthebtucationStrategy extends AbstractAuthenticationStrategy {

	@Override
	public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate)
			throws AuthenticationException {
		return   aggregate ;
	}

	@Override
	public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo,
			AuthenticationInfo aggregateInfo, Throwable arg4) throws AuthenticationException {
		 AuthenticationInfo info;
	        if (singleRealmInfo == null) {
	            info = aggregateInfo;
	        } else {
	            if (aggregateInfo == null) {
	                info = singleRealmInfo;
	            } else {
	                info = merge(singleRealmInfo, aggregateInfo);
	                 //只能够一个认证成功
	                if(info.getPrincipals().getRealmNames().size() > 1) {
	                    System.out.println(info.getPrincipals().getRealmNames());
	                    throw new AuthenticationException("Authentication token of type [" + token.getClass() + "] " +
	                            "could not be authenticated by any configured realms.  Please ensure that only one realm can " +
	                            "authenticate these tokens.");
	                }
	            }
	        }
	        return info;
	}

	@Override
	public AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token)
			throws AuthenticationException {
		return new SimpleAuthenticationInfo();
	}

	@Override
	public AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate)
			throws AuthenticationException {
		return  aggregate ;
	}
   
	
}
