package com.example.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum AuditEnum {

    /**
     * 草稿或仅保存
     */
    unAudit(0, "草稿"),
    /**
     * 审核中
     */
    auditing(1, "审核中"),
    /**
     * 审核通过
     */
    auditSuccess(2, "通过审核"),
    /**
     * 未通过审核
     */
    auditFail(3, "未通过审核");

    /**
     * 描述：编码
     */
    private int code;

    /**
     * 描述：中文释义
     */
    private String explain;

    AuditEnum(int code, String explain) {
        this.code  =code;
        this.explain = explain;
    }

    public int getCode() {
        return code;
    }

    public String getExplain() {
        return explain;
    }

    /**
     * 判断所传数值是否包含于枚举当中
     *
     * @param code
     * @return
     */
    public static boolean isOutRangeSex(Integer code) {
        List<Integer> auditEnumList = toList().stream().map(Enum::ordinal).collect(Collectors.toList());

        if (auditEnumList.contains(code)) {
            return true;
        }

        return false;
    }

    /**
     * 获得所有枚举类型到list
     * @return
     */
    public static List<AuditEnum> toList() {
        List<AuditEnum> list = new ArrayList<>();
        AuditEnum[] values = values();
        Collections.addAll(list, values);

        return list;
    }

    public static AuditEnum findAuditEnumByCode(int code) {
        for (AuditEnum auditEnum : AuditEnum.values()) {
            if (code == auditEnum.getCode()) {
                return auditEnum;
            }
        }

        return null;
    }

    public static String getExplainByCode(int code) {
        AuditEnum auditEnum = findAuditEnumByCode(code);

        if(auditEnum != null){
            return auditEnum.getExplain();
        }

        return "";
    }
}
