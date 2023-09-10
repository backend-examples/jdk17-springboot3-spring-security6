package com.example.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pagination {

    /**
     * 可以自定义分页字段名称，当前页字段名称默认是pageIndex
     * @return
     */
    String pageIndex() default "pageIndex";

    /**
     * 可以自定义分页字段名称，每页数量名称默认是pageSize
     *
     * @return
     */
    String pageSize() default "pageSize";

    /**
     * 分页后，map集的键名
     * @return
     */
    String targetParameter() default "pageList";

    /**
     * 可以自定义分页字段名称，排序字段名称默认是sort
     *
     * @return
     */
    String sortField() default "sort";

    /**
     * 也可以通过注解指定排序字段
     * @return
     */
    String sortItem() default "";
}

