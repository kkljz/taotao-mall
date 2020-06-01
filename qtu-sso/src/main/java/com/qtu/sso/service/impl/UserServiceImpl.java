package com.qtu.sso.service.impl;

import com.qtu.sso.entity.User;
import com.qtu.sso.mapper.UserMapper;
import com.qtu.sso.service.UserService;
import com.qtu.sso.util.RedisUtil;
import com.qtu.util.CookieUtils;
import com.qtu.util.JWT;
import com.qtu.util.JsonUtils;
import com.qtu.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * @author Hu Shengkai
 * @create 2019-12-09 16:03
 */
@Service
@Transactional
@PropertySource("classpath:/resource.properties")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${SESSION_EXPIRE_TIME}")
    private Integer SESSION_EXPIRE_TIME; //用户session保存时间
    @Value("${USER_TOKEN_KEY}")
    private String USER_TOKEN_KEY;
    @Value("${QTU_USER_TOKEN}")
    private String QTU_USER_TOKEN; //用户登录后保存的token name


    @Override
    public TaotaoResult checkData(String param, Integer type) {
        TaotaoResult result = null;
        switch (type){
            case 1:
                User user = userMapper.selectByUsername(param);
                if (user!=null){
                    result = TaotaoResult.ok(false);
                }else {
                    result = TaotaoResult.ok(true);
                }
                break;
            case 2:
                User user1 = userMapper.selectByPhone(param);
                if (user1 != null) {
                    result = TaotaoResult.ok(false);
                }else {
                    result = TaotaoResult.ok(true);
                }
                break;
            case 3:
                User user2 = userMapper.selectByEmail(param);
                if (user2 != null){
                    result = TaotaoResult.ok(false);
                }else {
                    result = TaotaoResult.ok(true);
                }
                break;
            default:
                result = TaotaoResult.build(201,"校验类型参数type不正确",false);
                break;
        }
        return result;
    }

    @Override
    public TaotaoResult insertUser(User user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null){
            return TaotaoResult.build(400,"用户不存在");
        }
        //验证密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return TaotaoResult.build(400,"用户名或密码错误");
        }

        //登录成功，把用户信息写入redis
        //生成一个用户token
        String token = UUID.randomUUID().toString();
        //保存前清除用户密码
        user.setPassword(null);

        //使用jwt工具对user对象进行加密，并转化为字符串，加密失效时间和redis存储的失效时间设置为相同的
        String userJWT = JWT.sign(user, SESSION_EXPIRE_TIME * 1000);
        //存入redis
        redisUtil.set(USER_TOKEN_KEY+":"+token, userJWT);
        //设置用户保存有效期
        redisUtil.expire(USER_TOKEN_KEY+":"+token, SESSION_EXPIRE_TIME);
        System.out.println(SESSION_EXPIRE_TIME.getClass()+" : "+SESSION_EXPIRE_TIME);
        //返回token
        return TaotaoResult.ok(USER_TOKEN_KEY+":"+token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        if (token == null){
            return TaotaoResult.build(500, "token不能为空");
        }
        //通过token从redis中取user对象
        String userJWT = (String) redisUtil.get(token);
        //对user进行解密
        User user = JWT.unsign(userJWT, User.class);
        //刷新缓存时间
        redisUtil.expire(token,SESSION_EXPIRE_TIME);
        return TaotaoResult.ok(user);
    }

    @Override
    public void logout(HttpServletRequest request , HttpServletResponse response) {
        //获得token
        String token = CookieUtils.getCookieValue(request, QTU_USER_TOKEN);
        //移除cookie中的user toke记录
        CookieUtils.deleteCookie(request,response,QTU_USER_TOKEN);
        //移除redis中的user缓存
        redisUtil.del(token);
    }
}
