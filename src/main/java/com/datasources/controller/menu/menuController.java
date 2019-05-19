package com.datasources.controller.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class menuController {
    @RequestMapping("/menu/menuList")
    public String menuList(Model model, HttpServletRequest request)throws Exception{
        return "views/menu/menuList";
    }
}
