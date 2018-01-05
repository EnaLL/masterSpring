package com.smart.dao;

import com.smart.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunxl on 2018/1/5.
 */
@Repository
class UserDao {
    private JdbcTemplate jdbcTemplate;

    private final static String MATCH_COUNT_SQL = " SELECT count(*) FROM t_user WHERE user_name = ? and password = ? ";
    private final static String UPDATE_LOGIN_INTFO_SQL = " UPDATE t_user SET last+visit = ?, last_ip = ?, credits = ? WHERE user_id = ? ";
    private final static String QUERY_BY_USERNAME = " SELECT user_id, user_name, credits FROM t_user WHERE user_name = ? ";

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMatchCount(String userName, String password){
        String sqlStr = " SELECT count(*) FROM t_user WHERE user_name = ? and password = ? ";
        return jdbcTemplate.queryForObject(MATCH_COUNT_SQL, new Object[]{userName, password}, Integer.class);
    }

    public User findUserByUserName(final String userName){
        final User user = new User();
        jdbcTemplate.query(QUERY_BY_USERNAME, new Object[]{ userName },
                new RowCallbackHandler(){
                    public void processRow(ResultSet rs) throws SQLException{
                        user.setUserId(rs.getInt("user_id"));
                        user.setUserName(userName);
                        user.setCreadits(rs.getInt("credits"));
                    }
                });
        return user;
    }
}
