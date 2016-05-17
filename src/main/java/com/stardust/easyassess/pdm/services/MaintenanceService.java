package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.Selection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaintenanceService<T> {
    T get(Long id);
    T get(String key);

    Page<T> list(int page, int size, String sortBy);
    Page<T> list(int page, int size, String sortBy, List<Selection> selections);
}