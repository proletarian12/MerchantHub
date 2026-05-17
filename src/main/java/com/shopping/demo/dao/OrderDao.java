package com.shopping.demo.dao;

import com.shopping.demo.entity.Orders;
import com.shopping.demo.entity.OrderItem;
import com.shopping.demo.util.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 订单行映射器
     */
    private RowMapper<Orders> orderRowMapper = new RowMapper<Orders>() {
        @Override
        public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
            Orders order = new Orders();
            order.setId(rs.getInt("id"));
            order.setOrderNo(rs.getString("order_no"));
            order.setUserId(rs.getInt("user_id"));
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setPaymentMethod(rs.getString("payment_method"));
            order.setStatus(rs.getInt("status"));
            order.setAddressId(rs.getInt("address_id"));
            order.setCreateTime(rs.getTimestamp("create_time"));
            order.setPayTime(rs.getTimestamp("pay_time"));
            return order;
        }
    };

    /**
     * 保存订单
     */
    public int save(Orders order) {
        String sql = "INSERT INTO orders (order_no, user_id, total_amount, payment_method, status, address_id, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getOrderNo());
            ps.setInt(2, order.getUserId());
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setString(4, order.getPaymentMethod());
            ps.setInt(5, order.getStatus());
            ps.setObject(6, order.getAddressId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    /**
     * 保存订单项
     */
    public void saveOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (order_id, product_id, product_name, product_image, price, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                orderItem.getOrderId(),
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getProductImage(),
                orderItem.getPrice(),
                orderItem.getQuantity()
        );
    }

    /**
     * 根据ID查询订单
     */
    public Orders findById(Integer orderId, Integer userId) {
        String sql = "SELECT * FROM orders WHERE id = ? AND user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, orderRowMapper, orderId, userId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 更新订单状态
     */
    public void updateStatus(Integer orderId, Integer status) {
        String sql = "UPDATE orders SET status = ?, pay_time = NOW() WHERE id = ?";
        jdbcTemplate.update(sql, status, orderId);
    }
}