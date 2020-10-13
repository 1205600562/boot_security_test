package com.boot.security.server.service.impl;

import com.boot.security.server.dao.PermissionDao;
import com.boot.security.server.dto.LoginUser;
import com.boot.security.server.model.Permission;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.model.SysUser.Status;
import com.boot.security.server.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	//UserDetailsService-----实现用户详情接口security自带的

	@Autowired
	private UserService userService;
	//权限
	@Autowired
	private PermissionDao permissionDao;




	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//查询用户
		SysUser sysUser = userService.getUser(username);
		if (sysUser == null) {
			//凭证没有发现  用户不存在
			throw new AuthenticationCredentialsNotFoundException("用户名不存在");
			//AuthenticationCredentialsNotFoundException（认证凭证未找到）
			// 继承AuthenticationException（认证方式异常）
		} else if (sysUser.getStatus() == Status.LOCKED) {

			throw new LockedException("用户被锁定,请联系管理员");

		} else if (sysUser.getStatus() == Status.DISABLED) {

			throw new DisabledException("用户已禁用");



		//赋值属性--BeanUtils.copyProperties("转换前的类", "转换后的类");
		LoginUser loginUser = new LoginUser();
		BeanUtils.copyProperties(sysUser, loginUser);

		List<Permission> permissions = permissionDao.listByUserId(sysUser.getId());
		loginUser.setPermissions(permissions);

		return loginUser;
	}


}
