package com.stardust.easyassess.pdm.common;


import java.math.BigDecimal;
import java.util.Date;

public interface  Context {

    public Integer getInt(String paramName);

    public Integer getInt(String paramName, Integer defaultValue);

    public String getString(String paramName);

    public String getString(String paramName, String defaultValue);

    public Long getLong(String paramName);

    public Long getLong(String paramName, long defaultValue);

    public boolean getBoolean(String paramName);

    public boolean getBoolean(String paramName, boolean defaultValue);

    public Date getDate(String paramName);

    public Date getDate(String paramName, Date defaultValue);

    public BigDecimal getDecimal(String paramName);

    public BigDecimal getDecimal(String paramName, BigDecimal defaultValue);

    public Double getDouble(String paramName);

    public Double getDouble(String paramName, double defaultValue);
}
