package com.datasources.master.service.menu;
import com.datasources.master.entity.MenuChildren;

import java.util.List;

public interface MenuChildrenService {
    List<MenuChildren> queryMenuByParentId(Integer parentId);
}
