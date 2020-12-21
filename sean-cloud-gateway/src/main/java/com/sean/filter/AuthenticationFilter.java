package com.sean.filter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 *  请求资源前，先通过此过滤器进行用户信息解析和校验 转发
 */
@Component
public class AuthenticationFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public Object run() throws ZuulException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof OAuth2Authentication)) {
			return null;
		}
		// 获取用户名
		Object principal = authentication.getPrincipal();
		// 获取用户所拥有的权限
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Set<String> authoritySet = AuthorityUtils.authorityListToSet(authorities);
		// 获取详情
		Object details = authentication.getDetails();
		
		Map<String, Object> ret = new HashMap<>();
		ret.put("principal", principal);
		ret.put("authorities", authorities);
		ret.put("details", details);
		
		// 获取请求上下文
		RequestContext context = RequestContext.getCurrentContext();
		//将用户&权限信息，转换成json，再通过base64进行编码
		String base64Context = Base64Utils.encodeToString(JSON.toJSONString(ret).getBytes());
		// 添加到请求头
		context.addZuulRequestHeader("auth-token", base64Context);
		return null;
	}

}
