package com.datasources.master.service;

import com.datasources.common.util.DataGrid;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public abstract class BaseService<T>
{

	@Autowired
	protected Mapper<T> mapper;

	public boolean insertSelective(T entity)
	{
		return this.mapper.insertSelective(entity) > 0 ? true : false;
	}

	public boolean deleteByPrimaryKey(Object key)
	{
		return this.mapper.deleteByPrimaryKey(key) > 0 ? true : false;
	}

	public boolean updateByPrimaryKey(T entity)
	{
		return this.mapper.updateByPrimaryKeySelective(entity) > 0 ? true : false;
	}

	public void batchDeleteByPrimaryKey(Object ids[])
	{
		if (ids != null && ids.length > 0)
			for (int i = 0; i < ids.length; i++)
			{
				deleteByPrimaryKey(ids[i]);
			}
	}

	public T selectByPrimaryKey(Object key)
	{
		return this.mapper.selectByPrimaryKey(key);
	}

	/**
	 * 查询单个对象：如果多条记录则会抛出异常
	 * 
	 * @param entity
	 * @return
	 */
	public T findByObject(T entity)
	{
		return this.mapper.selectOne(entity);
	}

	public List<T> queryExampleForList(Object example)
	{
		return this.mapper.selectByExample(example);
	}

	public List<T> queryObjectForList(String order)
	{
		PageHelper.orderBy(order);
		return this.mapper.selectAll();
	}

	public List<T> queryObjectForList()
	{
		return this.mapper.selectAll();
	}

	public List<T> queryObjectForList(T entity)
	{
		return this.mapper.select(entity);
	}
	public PageInfo<T> queryForDataGrid(DataGrid grid)
	{
		return this.queryForDataGrid(grid, null);
	}
	public PageInfo<T> queryForDataGrid(DataGrid grid, T entity)
	{
		PageHelper.startPage(grid.getPageNum(), grid.getPageSize());
		return new PageInfo<T>(mapper.select(entity));
	}

}
