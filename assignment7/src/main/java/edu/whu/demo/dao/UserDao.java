package edu.whu.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.demo.domain.User;
import edu.whu.demo.domain.UserDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 查询用户及其角色
     * @return
     */
    @Select("select * from user where id =  #{id}")
    @Results({@Result(id = true, property = "id", column = "id"),
            @Result(property = "roles", column = "id",
                    many = @Many(select = "edu.whu.demo.dao.RoleDao.findRolesByUser"))})
    public UserDto getUser(String id);


}
