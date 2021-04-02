package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.SysResult;
import com.jt.vo.EasyUITable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/item")
@RestController
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/query")
	public EasyUITable findPage(int page, int rows){
		return itemService.findObjectByPage(page,rows);
	}

	@RequestMapping("/save")
	public SysResult saveItem(Item item, ItemDesc itemDesc){
			itemService.saveItem(item,itemDesc);
			return SysResult.success();
	}

	/**
	 * url地址:/item/update
	 * 请求参数:全部form表单信息
	 * 返回值:Sysresult
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc){
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}

	/**
	 * url地址:/item/delete
	 * 请求参数:全部form表单信息
	 * 返回值:Sysresult
	 */
	@RequestMapping("/delete")
	public SysResult deleteItemByIds(Long[] ids){
		itemService.deleteItemByIds(ids);
		return SysResult.success();
	}


	/**
	 * url地址:/item/updata
	 * 请求参数:全部form表单信息
	 * 返回值:Sysresult
	 */
	@RequestMapping("/updateStatus/{status}")
	public SysResult updateStatus(@PathVariable Integer status, Long... ids ){
		itemService.updateStatus(status,ids);
		return SysResult.success();
	}

	/**
	 * 根据ItemId查询ItemDesc对象
	 * url:http://localhost:8091/item/query/item/desc/1474391989
	 * 参数: restFul形式
	 * 返回值: SysResult对象
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId){

		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		return SysResult.success(itemDesc); //200 返回业务数据
	}



}
