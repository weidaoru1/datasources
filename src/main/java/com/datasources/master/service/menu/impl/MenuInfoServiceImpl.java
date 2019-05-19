package com.datasources.master.service.menu.impl;
import com.datasources.master.entity.MenuInfo;
import com.datasources.master.service.BaseService;
import com.datasources.master.service.menu.MenuInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuInfoServiceImpl extends BaseService<MenuInfo> implements MenuInfoService {
    @Override
    public List<MenuInfo> queryMenuList() {
        return super.queryObjectForList("num");
    }
}
