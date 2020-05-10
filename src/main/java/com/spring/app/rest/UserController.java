package com.spring.app.rest;

import com.spring.app.constants.PathConstant;
import com.spring.app.dto.UserDto;
import com.spring.app.dto.create.UserCreate;
import com.spring.app.dto.criteria.UserCriteria;
import com.spring.app.dto.update.UserUpdate;
import com.spring.app.entity.User;
import com.spring.app.rest.core.BaseNecessaryCoreControllerImpl;
import com.spring.app.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = PathConstant.USER_PATH)
public class UserController extends BaseNecessaryCoreControllerImpl<UserService, UserCreate,
    UserUpdate, UserCriteria, UserDto, User, Long> {

}
