package com.shopping.demo.service;

import com.shopping.demo.dao.AddressDao;
import com.shopping.demo.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;

    /**
     * 根据用户ID查询地址列表
     */
    public List<Address> findByUserId(Integer userId) {
        return addressDao.findByUserId(userId);
    }

    /**
     * 保存地址
     */
    public int save(Address address) {
        return addressDao.save(address);
    }

    /**
     * 更新地址
     */
    public void update(Address address) {
        addressDao.update(address);
    }

    /**
     * 删除地址
     */
    public void delete(Integer id, Integer userId) {
        addressDao.delete(id, userId);
    }

    /**
     * 清除用户的默认地址
     */
    public void clearDefaultAddress(Integer userId) {
        addressDao.clearDefaultAddress(userId);
    }

    /**
     * 设置默认地址
     */
    public void setDefaultAddress(Integer id, Integer userId) {
        addressDao.setDefaultAddress(id, userId);
    }
}