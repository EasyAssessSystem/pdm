package com.stardust.easyassess.pdm.dao;

import com.stardust.easyassess.pdm.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
