package com.datasources.master.mapper;

import com.datasources.master.entity.MenuChildren;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper //把maper 变成spring容器中的bean
public interface MenuChildrenMapper extends Mapper<MenuChildren> {
    List<MenuChildren> queryMenuByParentId(Integer parentId);
}
