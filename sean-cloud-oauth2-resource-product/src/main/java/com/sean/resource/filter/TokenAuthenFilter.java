package com.sean.resource.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/*
 * 获取gateway转发过来的请求头中的保存的token
 */
@Component
public class TokenAuthenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authToken = request.getHeader("auth-token");
		if(StringUtils.isNotEmpty(authToken)) {
			System.out.println("商品资源服务器--TokenAuthenFilter " + authToken);
			// 非空，则Base64解析token
			String authTokenJson = new String(Base64Utils.decodeFromString(authToken));
			// token转换为JSON对象
			JSONObject jsonObject = JSON.parseObject(authTokenJson);
			
			// 用户名
			Object principal = jsonObject.get("principal");
			// 请求详情
			Object details = jsonObject.get("details");
			// 用户拥有权限
			String authorities = ArrayUtils.toString(jsonObject.getJSONArray("authorities").toArray());
			List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
			
			// 创建一个Authentication对象，SpringSecurity就会自动进行权限判断
			UsernamePasswordAuthenticationToken authenticationToken 
				= new UsernamePasswordAuthenticationToken(principal, null, authorityList);
			authenticationToken.setDetails(details);

			// 将UsernamePasswordAuthenticationToken对象，传给上下文，则自动权限判断
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		// 放行
		filterChain.doFilter(request, response);
	}

	
}
