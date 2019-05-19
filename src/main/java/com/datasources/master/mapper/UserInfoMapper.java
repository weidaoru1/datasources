package com.datasources.master.mapper;
import com.datasources.master.entity.UserInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface UserInfoMapper extends Mapper<UserInfo> {
    UserInfo userCheck(Map<String, Object> map);
}
