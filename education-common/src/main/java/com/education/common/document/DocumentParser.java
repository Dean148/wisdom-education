package com.education.common.document;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/30 19:31
 */
public interface DocumentParser<T> {

    default List<T> getDataList() {
        return null;
    }
}
