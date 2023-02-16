package com.capg.campsite.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.campsite.entity.User;
import com.capg.campsite.exception.ResourceNotFoundException;
import com.capg.campsite.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	private static final Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);


	@Override
	public List<User> getAllUsers() throws ResourceNotFoundException {
		if (userRepository.findAll().isEmpty()) {
			throw new ResourceNotFoundException("Users Not Found in db");
		}
		logger.info("get all users");
		return userRepository.findAll();
	}

	@Override
	public User addUser(User user) throws Exception {
		if (!userRepository.findById(user.getUserId()).isEmpty()) {
			throw new Exception("User already exists with id : " + user.getUserId());
		}
		logger.info("add user");
		return userRepository.save(user);
	}
	@Override
	public User updateUser(User user) throws ResourceNotFoundException {
		User u = userRepository.findById(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + user.getUserId()));
		userRepository.delete(u);
		logger.info("update user");
		return userRepository.save(user);
	}

	@Override
	public User getUserById(long userId) throws ResourceNotFoundException {
		logger.info("get user by Id");
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not exists with id : " + userId));
	}

	@Override
	public void deleteUser(long userId) throws ResourceNotFoundException {
		logger.info("delete user");
		User u = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
		userRepository.delete(u);

	}
}
