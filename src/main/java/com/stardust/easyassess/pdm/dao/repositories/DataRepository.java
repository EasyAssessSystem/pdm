package com.stardust.easyassess.pdm.dao.repositories;

import com.stardust.easyassess.pdm.common.Selection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface DataRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
    Page<T> findAll(Specification<T> spec, Pageable pageable);

    List<T> findAll(Specification<T> spec);

    T findOne(Specification<T> spec);

    String getKeyField();

    default T findBykey(String keyValue) {
        return this.findOne((root, query, cb) -> {
            Path<String> namePath = root.get(getKeyField());
            query.where(cb.equal(namePath, keyValue));
            return query.getRestriction();
        });
    }

    default Page<T> findAll(Pageable page, String field, String value) {


        /* replace this code piece with lambda expression
        return this.findAll(new Specification<T>() {

            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> namePath = root.get(field);

                query.where(cb.equal(namePath, value));

                return query.getRestriction();
            }

        }, page);
        */

        return this.findAll((root, query, cb) -> {
                                  Path<String> namePath = root.get(field);
                                  query.where(cb.equal(namePath, value));
                                  return query.getRestriction();
                              }, page);

    }

    default Page<T> findAll(Pageable page, List<Selection> selections) {

        return this.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(cb.greaterThan(root.get("id"), 0));

            for (Selection selection : selections) {
                if (selection.getProperty() == null
                        || selection.getProperty().isEmpty()) continue;
                if (selection.getValue() == null
                        || selection.getValue().toString().isEmpty()) continue;
                  predicates.add(selection.toPredicate(cb, root.get(selection.getProperty())));
            }

            query.where(predicates.toArray(new Predicate[selections.size()]));
            return query.getRestriction();
        }, page);

    }
}