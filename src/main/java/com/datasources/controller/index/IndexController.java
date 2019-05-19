package com.datasources.controller.index;
import com.datasources.master.entity.MenuChildren;
import com.datasources.master.entity.MenuInfo;
import com.datasources.master.service.menu.MenuChildrenService;
import com.datasources.master.service.menu.MenuInfoService;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    MenuInfoService menuInfoService;
    @Resource
    MenuChildrenService menuChildrenService;
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request)throws Exception{
        JSONArray jsonArray = new JSONArray();
        List<MenuInfo> menuInfoList = menuInfoService.queryMenuList();
        if (menuInfoList != null && menuInfoList.size() > 0){
            for (MenuInfo menuInfo : menuInfoList){
                JSONObject json = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                List<MenuChildren> menuChildren = menuChildrenService.queryMenuByParentId(menuInfo.getId());
                if (menuChildren != null && menuChildren.size() > 0){
                    for (MenuChildren menu : menuChildren){
                        JSONObject json1 = new JSONObject();
                        json1.put("name",menu.getName());
                        json1.put("url",menu.getUrl());
                        jsonArray1.put(json1);
                    }
                }
                json.put("name",menuInfo.getName());
                json.put("children",jsonArray1);
                jsonArray.put(json);
            }
        }
        model.addAttribute("menuList",jsonArray);
        return "index/index";
    }
    @RequestMapping("/main")
    public String main(Model model, HttpServletRequest request)throws Exception{
        return "index/main";
    }
    @RequestMapping(value = "/login/menuList", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public @ResponseBody
    String menuList(HttpServletRequest request)throws Exception{
        JSONArray jsonArray = new JSONArray();
        List<MenuInfo> menuInfoList = menuInfoService.queryMenuList();
        if (menuInfoList != null && menuInfoList.size() > 0){
            for (MenuInfo menuInfo : menuInfoList){
                JSONObject json = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                List<MenuChildren> menuChildren = menuChildrenService.queryMenuByParentId(menuInfo.getId());
                if (menuChildren != null && menuChildren.size() > 0){
                    for (MenuChildren menu : menuChildren){
                        JSONObject json1 = new JSONObject();
                        json1.put("name",menu.getName());
                        json1.put("url",menu.getUrl());
                        jsonArray1.put(json1);
                    }
                }
                json.put("name",menuInfo.getName());
                json.put("children",jsonArray1);
                jsonArray.put(json);
            }
        }
        return jsonArray.toString();
    }
}
