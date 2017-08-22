package com.xdzl.security.service;

import com.xdzl.security.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByUsername(String username);
}
