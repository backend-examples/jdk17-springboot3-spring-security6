package com.example.aspect;

import com.example.annotation.Pagination;
import com.example.model.PageInfoModel;
import com.example.utils.ResponseResult;
import com.example.utils.ServletUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class PaginationAspect {

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.example.annotation.Pagination)")
    public void access() {

    }

    @SneakyThrows
    @Around("access()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Pagination pagination = getPaginationAnnotation(joinPoint);
        boolean paged = true;

        if (ServletUtils.getParameterToInt(pagination.pageIndex()) == null && ServletUtils.getParameterToInt(pagination.pageSize()) == null) {
            paged = false;
        }

        Page page = startPage(paged, pagination.pageIndex(), pagination.pageSize());

        // 调用原本方法的内容并获取返回值
        Object result = joinPoint.proceed(args);

        // 返回的数据类型要保证和注解方法上的一致
        return pageResult(paged, page, pagination.targetParameter(), result);
    }

    /**
     * 获取Pagination注解
     *
     * @param joinPoint
     * @return
     */
    public Pagination getPaginationAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Pagination pagination = method.getAnnotation(Pagination.class);

        return pagination;
    }

    /**
     * 开始分页
     */
    private Page startPage(boolean paged, String pageIndexParameterName, String pageSizeParameterName) {
        if (paged) {
            int pageIndex = ServletUtils.getParameterToInt(pageIndexParameterName, 1);
            int pageSize = ServletUtils.getParameterToInt(pageSizeParameterName, 10);

            Page<Object> page = PageHelper.startPage(pageIndex, pageSize);

            return page;
        }

        return null;
    }

    /**
     * 对分页结果进行包装 如果分页成功则会返回PageResult
     *
     * @param paged 是否进行分页
     * @param page 分页信息，使用的是PageHelper.startPage()的结果，不能使用new PageInfo()获取到的数据，会导致分页数据错误
     * @param targetParameter 集合键名
     * @param result
     * @return
     */
    private Object pageResult(boolean paged, Page page, String targetParameter, Object result) {
        ResponseResult responseResult = null;
        // 列表数据 如果方法返回Page则直接使用 如果是ResponseResult则getData再封装
        Object data = null;

        if (result instanceof ResponseResult) {
            responseResult = (ResponseResult) result;
            data = ((Map) responseResult.getData()).get(targetParameter);
        }

        Map<String, Object> resultData = new HashMap<>(4);
        resultData.put(targetParameter, data);

        if (paged) {
            PageInfoModel pageInfoModel = new PageInfoModel();

            pageInfoModel.setPageIndex(page.getPageNum());
            pageInfoModel.setPageSize(page.getPageSize());
            pageInfoModel.setTotal(page.getTotal());
            pageInfoModel.setTotalPages(page.getPages());

            resultData.put("pageInfo", pageInfoModel);
        }

        responseResult.setData(resultData);

        return responseResult;
    }
}
