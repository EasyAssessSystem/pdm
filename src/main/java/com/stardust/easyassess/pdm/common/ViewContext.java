package com.stardust.easyassess.pdm.common;

import org.apache.commons.collections.map.HashedMap;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewContext implements Context {

    protected Map<String, Object> parameters = new HashMap<String, Object>();

    public ViewContext() {

    }

    @PostConstruct
    protected abstract void init();

    public Integer getInt(String paramName) {
        return getInt(paramName, 0);
    }

    public Integer getInt(String paramName, Integer defaultValue) {
        Object value = parameters.get(paramName);
        int results = defaultValue;
        try {
            String [] values =  (String [])value;
            results = Integer.parseInt(values[0]);
        } catch (Exception e) {
        }
        return results;
    }

    public String getString(String paramName) {
        return getString(paramName, "");
    }

    public String getString(String paramName, String defaultValue) {
        Object value = parameters.get(paramName);
        String results = defaultValue;
        try {
            String [] values =  (String [])value;
            results = values[0];
        } catch (Exception e) {
        }
        return results;
    }

    public Long getLong(String paramName) {
        return getLong(paramName, 0);
    }

    public Long getLong(String paramName, long defaultValue) {
        Object value = parameters.get(paramName);
        long results = defaultValue;
        try {
            String [] values =  (String [])value;
            results = Long.parseLong(values[0]);
        } catch (Exception e) {
        }
        return results;
    }

    public boolean getBoolean(String paramName) {
        return getBoolean(paramName, false);
    }

    public boolean getBoolean(String paramName, boolean defaultValue) {
        Object value = parameters.get(paramName);
        boolean results = defaultValue;
        try {
            String [] values =  (String [])value;
            results = Boolean.parseBoolean(values[0]);
        } catch (Exception e) {
        }
        return results;
    }

    public Date getDate(String paramName) {
        return getDate(paramName, null);
    }

    public Date getDate(String paramName, Date defaultValue) {
        Object value = parameters.get(paramName);
        Date results = defaultValue;
        try {
            String [] values =  (String [])value;
            results = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(values[0]);
        } catch (Exception e) {
        }
        return results;
    }

    public BigDecimal getDecimal(String paramName) {
        return getDecimal(paramName, new BigDecimal(0));
    }

    public BigDecimal getDecimal(String paramName, BigDecimal defaultValue) {
        Object value = parameters.get(paramName);
        BigDecimal results = defaultValue;
        try {
            String [] values =  (String [])value;
            results = new BigDecimal(values[0]);
        } catch (Exception e) {
        }
        return results;
    }

    public Double getDouble(String paramName) {
        return getDouble(paramName, 0);
    }

    public Double getDouble(String paramName, double defaultValue) {
        Object value = parameters.get(paramName);
        double results = defaultValue;
        try {
            String [] values =  (String [])value;
            results = Double.parseDouble(values[0]);
        } catch (Exception e) {
        }
        return results;
    }
}
