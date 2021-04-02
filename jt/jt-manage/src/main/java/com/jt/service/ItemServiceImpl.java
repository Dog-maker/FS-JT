package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;


	@Override
	public EasyUITable findObjectByPage(int page, int rows) {
//		long total = itemMapper.selectCount(null);
//		int startpage = page-1;
//		List<Item> items = new ArrayList<>();
//		items = itemMapper.findObject(startpage,rows);
//		return new EasyUITable(total,items);

		//利用MP采取分页查询
		IPage myPage = new Page(page,rows);
		QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("updated");
		myPage = itemMapper.selectPage(myPage, queryWrapper);
		return new EasyUITable(myPage.getTotal(),myPage.getRecords());
	}

	@Override
	@Transactional	//事务的开启
	public void saveItem(Item item, ItemDesc itemDesc) {
		item.setStatus(1);
		itemMapper.insert(item);
		itemDesc.setItemId(item.getId());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId());
		itemDescMapper.updateById(itemDesc);
	}

	@Override
	public void deleteItemByIds(Long[] ids) {
		for (Long id : ids){
			itemDescMapper.deleteById(id);
			itemMapper.deleteById(id);
		}


	}

	@Override
	public void updateStatus(Integer status, Long[] ids) {
		Item item = new Item();
		item.setStatus(status);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.in("id", ids);
		itemMapper.update(item,queryWrapper);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		ItemDesc item = itemDescMapper.selectById(itemId);
		return item;
	}
}
