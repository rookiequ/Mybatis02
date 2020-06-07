package com.user.dao;

import com.user.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface UserDAO {

    /**
     * 查询所有用户
     * 这里的List的泛型一定要写，因为用注解的方式他代表了返回值的类型
     * @return
     */
    @Select("select * from user")
    List<User> findAll();

    /**
     * 新增用户
     * @param user
     */
    @Insert("insert into user(username, birthday, sex, address) values(#{username}, #{birthday}, #{sex}, #{address})")
    void saveUser(User user);


    /**
     * 删除用户信息
     * @param id
     */
    @Delete("delete from user where id=#{id}")
    void deleteUser(Integer id);

    /**
     * 修改用户信息
     * @param user
     */
    @Update("update user set username=#{username}, birthday=#{birthday}, sex=#{sex}, address=#{address} where id=#{id}")
    void updateUser(User user);

    /**
     * 通过id获取用户信息
     * @param uid
     * @return
     */
    @Select("select * from user where id = #{uid}")
    User findById(String uid);

    /**
     * 查询用户信息，包含账号信息
     * @return
     */
    @Select("select * from user")
    @Results(id = "userAccountMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "address", property = "address"),
            @Result(column = "id", property = "accountList", many = @Many(select =
            "com.user.dao.AccountDAO.findAccountById", fetchType = FetchType.LAZY))
    })
    List<User> findUserWithAccount();
}
