package com.user.dao;

import com.user.domain.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface AccountDAO {

    /**
     * 查询所有账号信息，带有用户信息
     *      @Select("select * from account")
     *     Results可以修改别名，让类的属性和列名相对应
     *     Results通过id可以重复使用ResultMap()
     * @return
     */
    @Select("select * from account")
    @Results(id = "accountUserMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "uid", property = "uid"),
            @Result(column = "money", property = "money"),
            @Result(column = "uid", property = "user", one = @One(select =
            "com.user.dao.UserDAO.findById", fetchType = FetchType.EAGER))
    })
    List<Account> findAccountWithUser();

    /**
     * 根据id获取account信息
     * @param id
     * @return
     */
    @Select("select * from account where uid = #{id}")
    Account findAccountById(String id);
}
