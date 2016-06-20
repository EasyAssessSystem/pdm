package com.stardust.easyassess.pdm.common;


import java.util.List;
import com.stardust.easyassess.core.query.Selection;


public interface ListSelectionBuilder {
    List<Selection> buildSelections(List<Selection> selections, ViewContext context);
}
