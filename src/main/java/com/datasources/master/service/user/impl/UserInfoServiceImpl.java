package com.datasources.master.service.user.impl;
import com.datasources.common.util.Constants;
import com.datasources.common.util.DataGrid;
import com.datasources.common.util.MD5utils;
import com.datasources.common.util.RedisUtils;
import com.datasources.master.entity.UserInfo;
import com.datasources.master.mapper.UserInfoMapper;
import com.datasources.master.service.BaseService;
import com.datasources.master.service.user.UserInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public boolean deleteById(Integer id) {
        if (RedisUtils.redisOpen()){
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            UserInfo userInfo = (UserInfo)redisTemplate.opsForValue().get(Constants.USER_KEY + id);
            if (userInfo != null){
                //删除掉登录用户部分
                redisTemplate.delete(Constants.LOGIN_KEY + userInfo.getUserName());
            }
            redisTemplate.delete(Constants.USER_KEY + id);
            return super.deleteByPrimaryKey(id);
        }
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public List<UserInfo> queryUserList(UserInfo userInfo) {
        return super.queryObjectForList(userInfo);
    }

    @Override
    public UserInfo queryById(Integer id) {
        UserInfo userInfo = null;
        if (RedisUtils.redisOpen()){
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            userInfo = (UserInfo)redisTemplate.opsForValue().get(Constants.USER_KEY + id);
            if (userInfo == null){
                userInfo = userInfoMapper.selectByPrimaryKey(id);
                redisTemplate.opsForValue().set(Constants.USER_KEY  +  userInfo.getId(),userInfo);
            }
            return userInfo;
        }
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addUserInfo(UserInfo userInfo) {
        int  res = userInfoMapper.insertSelective(userInfo);
        if (RedisUtils.redisOpen()){
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForValue().set(Constants.USER_KEY + userInfo.getId(),userInfo);
        }
        return res;
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        if (RedisUtils.redisOpen()){
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForValue().set(Constants.USER_KEY + userInfo.getId(),userInfo);
            //更新登录用户信息
            redisTemplate.opsForValue().set(Constants.LOGIN_KEY + userInfo.getUserName(),userInfo);
        }
        return userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    public UserInfo userCheck(String userName, String password) {
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);
        map.put("password", MD5utils.encrypt(password));
        UserInfo userInfo = null;
        if (RedisUtils.redisOpen()){
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            //从缓存获取用户登录信息
            userInfo = (UserInfo)redisTemplate.opsForValue().get(Constants.LOGIN_KEY + userName);
            if (userInfo != null && MD5utils.encrypt(password).equals(userInfo.getPassword())){
                return userInfo;
            }
        }
        userInfo = userInfoMapper.userCheck(map);
        if (userInfo != null && RedisUtils.redisOpen()){
            redisTemplate.opsForValue().set(Constants.LOGIN_KEY + userName,userInfo);
        }
        return userInfo;
    }
    @Override
    public PageInfo<UserInfo> queryForDataGrid(DataGrid grid){
        return super.queryForDataGrid(grid);
    }
}
