package com.datasources.controller.role;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RoleController {
    @RequestMapping("/role/roleList")
    public String roleList(Model model, HttpServletRequest request){
        return "views/role/roleList";
    }
}
