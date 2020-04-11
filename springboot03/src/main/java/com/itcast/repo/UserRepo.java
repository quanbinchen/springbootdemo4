package com.itcast.repo;

import com.itcast.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * 参数一 T :当前需要映射的实体
 * 参数二 ID :当前映射的实体中的OID的类型
 *
 */
public interface UserRepo extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {


}
