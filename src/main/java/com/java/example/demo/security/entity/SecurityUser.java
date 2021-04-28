package com.java.example.demo.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class SecurityUser implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private transient User currentUserInfo;
	
	private List<String> permissionValueList;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String permissionValue : permissionValueList) {
            if(!StringUtils.hasLength(permissionValue)) continue;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
            authorities.add(authority);
        }
        return authorities;			
	}

	@Override
	public String getPassword() {
		return currentUserInfo.getPassword();
	}

	@Override
	public String getUsername() {
		return currentUserInfo.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<String> getPermissionValueList() {
		return permissionValueList;
	}

	public void setPermissionValueList(List<String> permissionValueList) {
		this.permissionValueList = permissionValueList;
	}

	public User getCurrentUserInfo() {
		return currentUserInfo;
	}

	public void setCurrentUserInfo(User currentUserInfo) {
		this.currentUserInfo = currentUserInfo;
	}
	
	

}
