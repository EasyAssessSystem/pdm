package com.stardust.easyassess.pdm.common;


import java.util.List;

public interface ListSelectionBuilder {
    List<Selection> buildSelections(List<Selection> selections, ViewContext context);
}
