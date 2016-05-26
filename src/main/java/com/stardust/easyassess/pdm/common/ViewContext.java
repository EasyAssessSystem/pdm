package com.stardust.easyassess.pdm.common;

import com.stardust.easyassess.core.context.AbstractContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewContext extends AbstractContext {

    protected Map<String, Object> parameters = new HashMap<String, Object>();

    public ViewContext() {

    }

    @Override
    protected Object getter(String s) {
        return parameters.get(s);
    }

    @Override
    protected void setter(String s, Object o) {
        parameters.put(s, o);
    }

    @PostConstruct
    protected abstract void init();

    @Override
    public void clear() {
        parameters.clear();
    }

    @Override
    public void remove(String s) {
        parameters.remove(s);
    }
}
