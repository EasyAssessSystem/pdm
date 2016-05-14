package com.stardust.easyassess.pdm.dao;

import com.stardust.easyassess.pdm.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

public interface UserRepository extends DataRepository<User, Long> {

}
