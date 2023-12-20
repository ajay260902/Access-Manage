package com.jwt.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.demo.email.ForgetPassword;
import com.jwt.demo.entity.User;
import com.jwt.demo.entity.UserDAO;

import jakarta.mail.MessagingException;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;
	@Autowired
	ForgetPassword forgetPassword;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getUsers() {
		String sql = "SELECT * FROM user_table";
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	public User getOneUser(String email) {
//    	String emailString="chavdaajay129@gmail.com";
		String sql = "SELECT * FROM demo_db.user_table WHERE email = ?";
//        StringBuilder sqlBuilder = new St
		return jdbcTemplate.queryForObject(sql, new Object[] { email }, new UserRowMapper());
	}

	public User createUser(User user) {
		user.setUserId(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		String sql = "INSERT INTO user_table (user_id, name, email, password) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getUserId(), user.getName(), user.getEmail(), user.getPassword());

		return user;
	}

	private static class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	}

	public String forgetPassword(String email) {
		// TODO Auto-generated method stub
      User user =userDAO.findByEmail(email).orElseThrow(()->new RuntimeException("User not found with email" + email));
    String otp=  forgetPassword.sendSetPasswordEmail(email);
		return "pls check mail and generated OTP is "+ otp;
	}

	public String setPassword(String email, String newPassword) {
		User user =userDAO.findByEmail(email).orElseThrow(()->new RuntimeException("User not found with email" + email));
	      user.setPassword(passwordEncoder.encode(newPassword));
	      userDAO.save(user);
		return "Your new password is updated";
	}

	
}
