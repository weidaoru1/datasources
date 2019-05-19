package com.datasources.master.service.menu.impl;
import com.datasources.master.entity.MenuChildren;
import com.datasources.master.mapper.MenuChildrenMapper;
import com.datasources.master.service.menu.MenuChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuChildrenServiceImpl implements MenuChildrenService {
    @Autowired
    MenuChildrenMapper menuChildrenMapper;
    @Override
    public List<MenuChildren> queryMenuByParentId(Integer parentId) {
        return menuChildrenMapper.queryMenuByParentId(parentId);
    }
}
