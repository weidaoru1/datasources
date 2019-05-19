package com.datasources.slaver.service.impl;
import com.datasources.common.util.Constants;
import com.datasources.common.util.RedisUtils;
import com.datasources.slaver.entity.UserSlaverInfo;
import com.datasources.slaver.mapper.UserSlaverMapper;
import com.datasources.slaver.service.UserSlaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
@Service
public class UserSlaverServiceImpl implements UserSlaverService {
    @Autowired
    UserSlaverMapper userSlaverMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Override
    public int addUserInfo(UserSlaverInfo userInfo) {
        int  res = userSlaverMapper.insertSelective(userInfo);
        if (RedisUtils.redisOpen()){
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.opsForValue().set(Constants.USER_KEY + userInfo.getId(),userInfo);
        }
        return res;
    }
}
