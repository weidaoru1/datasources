package com.datasources.slaver.mapper;
import com.datasources.slaver.entity.UserSlaverInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;
public interface UserSlaverMapper extends Mapper<UserSlaverInfo> {
    UserSlaverInfo userCheck(Map<String, Object> map);
}
