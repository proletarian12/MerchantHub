package com.shopping.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JdbcUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取数据库连接
     */
    public Connection getConnection() throws SQLException {
        return jdbcTemplate.getDataSource().getConnection();
    }

    /**
     * 关闭连接
     */
    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行更新操作
     */
    public int executeUpdate(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    /**
     * 查询单个对象
     */
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rowMapper.mapRow(rs), params);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 行映射器接口
     */
    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}