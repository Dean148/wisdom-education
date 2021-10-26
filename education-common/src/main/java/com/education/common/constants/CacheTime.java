package com.education.common.constants;

/**
 * @author zengjintao
 * @create_at 2021年10月8日 0008 15:17
 * @since version 1.6.5
 */
public interface CacheTime {

    Integer ONE_HOUR_SECOND = 3600;

    Integer ONE_HOUR_MILLIS = 3600 * 1000;
    Integer ONE_WEEK_SECOND = 7 * 24 * 60 * 60;
    Integer ONE_WEEK_MILLIS = ONE_WEEK_SECOND * 1000;

    Integer TWO_SECOND_MILLIS = 2 * 60 * 1000;
    Integer ONE_DAY_SECOND = 24 * 60 * 60;
}
