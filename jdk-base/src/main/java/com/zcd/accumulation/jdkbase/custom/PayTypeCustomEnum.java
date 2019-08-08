package com.zcd.accumulation.jdkbase.custom;

/**
 *     枚举的构造方法只能使用 private 访问控制符
 *     所有枚举都自动包含两个预定义方法：values()和valueOf()。它们的一般形式如下所示：
 *     public static enum-type [ ] values( )           //返回一个枚举数组
 *     public static enum-type valueOf(String str )    //返回一个枚举
 *     values()方法返回一个包含枚举常量列表的数组，valueOf()方法返回与传递到参数str 的字符串相对
 */
public enum PayTypeCustomEnum {

    /**
     * 付款.
     */
    PAY(1, "付款"),

    /**
     * 退款.
     */
    REFOUND(2, "退款");

    /**
     * 值.
     */
    private final int value;

    /**
     * 描述.
     */
    private final String desc;

    /**
     * 构造函数.
     *
     * @param value
     *            值
     * @param desc
     *            描述
     */
    // 构造器必须是私有的
    private PayTypeCustomEnum(int value, String desc) {
        this.desc = desc;
        this.value = value;
    }

    /**
     * 设置value.
     *
     * @return 返回value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     * 设置desc.
     *
     * @return 返回desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 通过描述匹配.
     *
     * @param desc
     *            desc
     * @return PayTypeEnum
     */
    public static PayTypeCustomEnum matchByDesc(String desc) {
        for (PayTypeCustomEnum payTypeEnum : PayTypeCustomEnum.values()) {
            if (desc.equals(payTypeEnum.getDesc())) {
                return payTypeEnum;
            }
        }
        throw new IllegalArgumentException("PayTypeEnum not find!");
    }

    /**
     * 通过值匹配.
     *
     * @param value
     *            value
     * @return PayTypeEnum
     */
    public static PayTypeCustomEnum matchByValue(int value) {
        for (PayTypeCustomEnum payTypeEnum : PayTypeCustomEnum.values()) {
            if (value == payTypeEnum.getValue()) {
                return payTypeEnum;
            }
        }
        throw new IllegalArgumentException("PayTypeEnum not find!");
    }
}
