package com.stardust.easyassess.pdm.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

@Deprecated
public class Selection {

    public enum Operand {
        AND(" and "), OR(" or "), EMPTY("");

        private String op = "";

        Operand(String op) {
            this.op = op;
        }

        public String getOperand() {
            return this.op;
        }

    }

    public enum Operator {
        EQUAL("="),NOT_EQUAL("!="),GREATER(">"),GREATER_EQUAL(">="),LESS("<"),LESS_EQUAL("<="),LIKE(" like "),IS_NULL( "" );

        private String opt = "";

        Operator(String opt) {
            this.opt = opt;
        }

        public String getOperator() {
            return this.opt;
        }

    }

    private Operand operand;
    private Operator operator;
    private String property;
    private Object value;
    private String paramName = "";

    @Autowired
    private GenericConversionService conversionService;

    public Selection() {

    }

    public Selection(final String p, final Operator opt) {
        this(p, opt, null, Operand.EMPTY);
    }

    public Selection(final String p, final Operator opt, final Object v) {
        this(p, opt, v, Operand.EMPTY);
    }

    public Selection(final String p, final Operator opt, final Object v, final String paramName) {
        this(p, opt, v, Operand.EMPTY);
        this.setParameterName(paramName);
    }

    public Selection(final String p, final Operator opt, final Object v, final Operand op) {
        property = p;
        operator = opt;
        value = v;
        operand = op;
        paramName = property;
    }

    public Selection(final String p, final Operator opt, final Object v, final Operand op, final String paramName) {
        property = p;
        operator = opt;
        value = v;
        operand = op;
        this.setParameterName(paramName);
    }

    public void setParameterName(String n) {
        paramName = n;
    }

    public String getParameterName(){
        return paramName;
    }

    public Operand getOperand(){
        return this.operand;
    }

    public Operator getOperator(){
        return this.operator;
    }

    public String getProperty(){
        return this.property;
    }

    public Object getValue(){
        return this.value;
    }

    public Predicate toPredicate(CriteriaBuilder cb, Path<String> namePath) {
        Predicate predicate = null;

        switch (this.getOperator()) {
            case NOT_EQUAL:
                predicate = cb.notEqual(namePath, this.getValue());
                break;
            case GREATER:
                predicate = cb.greaterThan(namePath, namePath.getJavaType().cast(this.getValue()));
                break;
            case LESS:
                predicate = cb.lessThan(namePath, namePath.getJavaType().cast(this.getValue()));
                break;
            case GREATER_EQUAL:
                predicate = cb.greaterThanOrEqualTo(namePath, namePath.getJavaType().cast(this.getValue()));
                break;
            case LESS_EQUAL:
                predicate = cb.lessThanOrEqualTo(namePath, namePath.getJavaType().cast(this.getValue()));
                break;
            case LIKE:
                if (namePath.getJavaType().equals(String.class)) {
                    predicate = cb.like(namePath, "%" + this.getValue() + "%");
                } else {
                    predicate = cb.equal(namePath, this.getValue());
                }
                break;
            case IS_NULL:
                predicate = cb.isNull(namePath);
                break;
            case EQUAL:
            default:
                predicate = cb.equal(namePath, this.getValue());
                break;
        }

        if (this.getOperand().equals(Operand.AND)) {
            cb.and(predicate);
        } else if (this.getOperand().equals(Operand.OR)) {
            cb.or(predicate);
        }

        return predicate;
    }

    @Override
    public String toString() {
        return operand + "(" + property + operator + value + ")";
    }
}


