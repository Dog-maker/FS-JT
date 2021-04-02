package com.jt.service;

import com.jt.anno.CatchFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

//    @Autowired(required = false)
//    private JedisSentinelPool jedisSentinelPool;
    @Autowired(required = false)
    private JedisCluster jedis;

    @Override
    @CatchFind(key = "Inte_Cat",seconds = 60*60*24*77)
    public String findItemName(Long itemCatId) {
        ItemCat itemCat = itemCatMapper.selectById(itemCatId);
        return itemCat.getName();
    }


    @CatchFind(key = "Item_Cat_ParentId::",seconds = 30*24*60*60)
    @Override
    public List<EasyUITree> findTree(Long parentId) {
        List<ItemCat> easyUI = itemCatMapper.findItemTree(parentId);

        List<EasyUITree> easyUITrees = new ArrayList<>(easyUI.size());
        for (ItemCat itemCat : easyUI){
            Long id = itemCat.getId();
            String name = itemCat.getName();
            String state = itemCat.getIsParent()?"closed":"open";
            EasyUITree easyUITree = new EasyUITree(id,name,state);
            easyUITrees.add(easyUITree);
        }
        return easyUITrees;
    }


    //缓存查找
    @Override
    public List<EasyUITree> findCatchTree(Long parentId) {
//        Jedis shardedJedis = jedisSentinelPool.getResource();

        Long start = System.currentTimeMillis();
        List<EasyUITree> easyUITrees = new ArrayList<>();
        String key = "Item_Cat_ParentId:"+parentId;
        //从缓存先查询是否存在
        //若存在，则在缓存中取
        if(jedis.exists(key)){
            String json = jedis.get(key);
            easyUITrees = ObjectMapperUtil.toObject(json, easyUITrees.getClass());
            System.out.println("当前走缓存");
        }else{
            easyUITrees = findTree(parentId);
            String json = ObjectMapperUtil.toJson(easyUITrees);
            jedis.setex(key, 30*24*60*60, json);
            System.out.println("当前查询数据库");
        }
        Long end = System.currentTimeMillis();
        System.out.println("时间为:"+(end-start)+"毫秒");

//        jedis.close();
        return easyUITrees;
    }


}
