package com.stardust.easyassess.pdm.dao;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface DataRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

}