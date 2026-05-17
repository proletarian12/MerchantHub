package com.shopping.demo.dao;

import com.shopping.demo.entity.Address;
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
import java.util.List;

@Repository
public class AddressDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcUtil jdbcUtil;

    /**
     * 地址行映射器
     */
    private RowMapper<Address> addressRowMapper = new RowMapper<Address>() {
        @Override
        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
            Address address = new Address();
            address.setId(rs.getInt("id"));
            address.setUserId(rs.getInt("user_id"));
            address.setName(rs.getString("name"));
            address.setPhone(rs.getString("phone"));
            address.setProvince(rs.getString("province"));
            address.setCity(rs.getString("city"));
            address.setDistrict(rs.getString("district"));
            address.setDetail(rs.getString("detail"));
            address.setIsDefault(rs.getBoolean("is_default"));

            return address;
        }
    };

    /**
     * 根据用户ID查询地址
     */
    public List<Address> findByUserId(Integer userId) {
        String sql = "SELECT * FROM address WHERE user_id = ? ORDER BY is_default DESC, create_time DESC";
        return jdbcTemplate.query(sql, addressRowMapper, userId);
    }

    /**
     * 保存地址
     */
    public int save(Address address) {
        String sql = "INSERT INTO address (user_id, name, phone, province, city, district, detail, is_default, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getName());
            ps.setString(3, address.getPhone());
            ps.setString(4, address.getProvince());
            ps.setString(5, address.getCity());
            ps.setString(6, address.getDistrict() != null ? address.getDistrict() : "");
            ps.setString(7, address.getDetail());
            ps.setBoolean(8, address.getIsDefault() != null ? address.getIsDefault() : false);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    /**
     * 更新地址
     */
    public void update(Address address) {
        String sql = "UPDATE address SET name = ?, phone = ?, province = ?, city = ?, district = ?, detail = ?, is_default = ? " +
                "WHERE id = ? AND user_id = ?";

        jdbcTemplate.update(sql,
                address.getName(),
                address.getPhone(),
                address.getProvince(),
                address.getCity(),
                address.getDistrict(),
                address.getDetail(),
                address.getIsDefault(),
                address.getId(),
                address.getUserId()
        );
    }

    /**
     * 删除地址
     */
    public void delete(Integer id, Integer userId) {
        String sql = "DELETE FROM address WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }

    /**
     * 清除用户的默认地址
     */
    public void clearDefaultAddress(Integer userId) {
        String sql = "UPDATE address SET is_default = false WHERE user_id = ? AND is_default = true";
        jdbcTemplate.update(sql, userId);
    }

    /**
     * 设置默认地址
     */
    public void setDefaultAddress(Integer id, Integer userId) {
        String sql = "UPDATE address SET is_default = true WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }
}