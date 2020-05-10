package com.spring.app.services;

import com.spring.app.dto.UserDto;
import com.spring.app.dto.create.UserCreate;
import com.spring.app.dto.criteria.UserCriteria;
import com.spring.app.dto.update.UserUpdate;
import com.spring.app.entity.User;
import com.spring.app.repository.UserRepository;
import com.spring.app.services.core.BaseCoreServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseCoreServiceImpl<UserRepository, UserCreate, UserUpdate, UserCriteria, UserDto, User, Long> {


  @Override
  public User newEntity() {
    return null;
  }

  @Override
  public UserDto newDto() {
    return null;
  }

  @Override
  public Specification<User> newSpecification(UserCriteria userCriteria) {
    return null;
  }
}
