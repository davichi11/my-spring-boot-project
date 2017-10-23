package io.renren.modules.api.service.impl;


import io.renren.common.exception.RRException;
import io.renren.modules.api.dao.UserDao;
import io.renren.modules.api.entity.UserEntity;
import io.renren.modules.api.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity queryObject(Long userId) {
        return userDao.queryObject(userId);
    }

    @Override
    public List<UserEntity> queryList(Map<String, Object> map) {
        return userDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return userDao.queryTotal(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String mobile, String password) throws Exception {
        UserEntity user = new UserEntity();
        user.setMobile(mobile);
        user.setUsername(mobile);
        user.setPassword(DigestUtils.sha256Hex(password));
        user.setCreateTime(LocalDateTime.now());
        userDao.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserEntity user) throws Exception {
        userDao.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId) throws Exception {
        userDao.delete(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] userIds) throws Exception {
        userDao.deleteBatch(userIds);
    }

    @Override
    public UserEntity queryByMobile(String mobile) {
        return userDao.queryByMobile(mobile);
    }

    @Override
    public long login(String mobile, String password) {
        UserEntity user =
                Optional.ofNullable(queryByMobile(mobile)).filter(u -> u.getPassword().equals(DigestUtils.sha256Hex(password)))
                        .orElseThrow(() -> new RRException("手机号或密码错误"));
        return user.getUserId();
    }
}
