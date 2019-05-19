package com.datasources.controller.user;
import com.datasources.common.util.DataGrid;
import com.datasources.common.util.MD5utils;
import com.datasources.common.util.UserConstants;
import com.datasources.master.entity.UserInfo;
import com.datasources.master.service.user.UserInfoService;
import com.datasources.slaver.entity.UserSlaverInfo;
import com.datasources.slaver.service.UserSlaverService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    UserInfoService userInfoService;
    @Resource
    UserSlaverService userSlaverService;
    @RequestMapping("/user/userList")
    public String userList(Model model, HttpServletRequest request, DataGrid dataGrid)throws Exception{
        model.addAttribute("page",userInfoService.queryForDataGrid(dataGrid));
        return "views/user/userList";
    }

    @RequestMapping("/")
    public String loginNew(Model model, HttpServletRequest request)throws Exception{
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request)throws Exception{
        return "login";
    }
    @RequestMapping("user/queryById")
    public  @ResponseBody Object queryById(HttpServletRequest request, UserInfo userInfo){
        return userInfoService.queryById(userInfo.getId());
    }
    @RequestMapping("user/deleteById")
    public  @ResponseBody String deleteById(HttpServletRequest request)throws Exception{
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("ids");
        if (StringUtils.isNotBlank(id)){
            String[] ids = id.split(";");
            for (String s : ids){
                userInfoService.deleteById(Integer.valueOf(s));
            }
        }
        jsonObject.put("msg","删除成功！");
        return jsonObject.toString();
    }
    @RequestMapping("user/save")
    public @ResponseBody String save(HttpServletRequest request, UserInfo userInfo)throws Exception{
        JSONObject jsonObject = new JSONObject();
        UserInfo u = new UserInfo();
        u.setUserName(userInfo.getUserName());
        List<UserInfo> user = userInfoService.queryUserList(u);
        if (user != null && user.size() > 0 && userInfo.getId()  == null){
            jsonObject.put("msg","该用户账号已存在！");
            jsonObject.put("status",0);
            return jsonObject.toString();
        }
        userInfo.setPassword(MD5utils.encrypt(userInfo.getPassword()));
        if (userInfo.getId() != null){
            userInfoService.updateUserInfo(userInfo);
        }else{
           userInfoService.addUserInfo(userInfo);
        }
        UserSlaverInfo userSlaverInfo = new UserSlaverInfo();
        userSlaverInfo.setUserName(userInfo.getUserName());
        userSlaverInfo.setRealName(userInfo.getRealName());
        userSlaverInfo.setTel(userInfo.getTel());
        userSlaverInfo.setDes(userInfo.getDes());
        userSlaverInfo.setPassword(userInfo.getPassword());
        userSlaverService.addUserInfo(userSlaverInfo);
        jsonObject.put("status",1);
        jsonObject.put("msg","保存成功！");
        return jsonObject.toString();
    }
    @RequestMapping("/login/check")
    public @ResponseBody String check(HttpServletRequest request)throws Exception{
        JSONObject jsonObject = new JSONObject();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        UserInfo userInfo = userInfoService.userCheck(userName,password);
        if (userInfo == null){
            jsonObject.put("status",0);
            jsonObject.put("success",false);
            return jsonObject.toString();
        }
        request.getSession().setAttribute(UserConstants.LOGIN_USER.name(),userInfo);
        request.getSession().setAttribute(UserConstants.LOGIN_USER_NAME.name(),userInfo.getUserName());
        request.getSession().setAttribute(UserConstants.LOGIN_REAL_NAME.name(),userInfo.getRealName());
        jsonObject.put("status",1);
        jsonObject.put("success",true);
        return jsonObject.toString();
    }

}
