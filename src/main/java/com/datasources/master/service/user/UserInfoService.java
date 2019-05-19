package com.datasources.master.service.user;
import com.datasources.common.util.DataGrid;
import com.datasources.master.entity.UserInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserInfoService {
    int addUserInfo(UserInfo userInfo);
    int updateUserInfo(UserInfo userInfo);
    UserInfo userCheck(String userName, String password);
    public PageInfo<UserInfo> queryForDataGrid(DataGrid grid);
    List<UserInfo> queryUserList(UserInfo userInfo);
    UserInfo queryById(Integer id);
    boolean deleteById(Integer id);
}
