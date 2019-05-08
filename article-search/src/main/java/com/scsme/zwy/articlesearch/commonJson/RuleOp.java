package com.scsme.zwy.articlesearch.commonJson;


public enum RuleOp {

    EqualTo("EqualTo", "=="),
    NotEqualTo("NotEqualTo", "!="),
    Like("Like", "%"),
    NotLike("NotLike", "!%"),
    GreaterThan("GreaterThan", ">"),
    GreaterThanOrEqualTo("GreaterThanOrEqualTo", ">="),
    LessThan("LessThan", "<"),
    LessThanOrEqualTo("LessThanOrEqualTo", "<="),
    In("In", "{}"),
    NotIn("NotIn", "!{}"),
    Between("Between", "<>"),
    NotBetween("NotBetween", "><"),
    IsNotNull("IsNotNull", "!null"),
    IsNull("IsNull", "null");

    private String name;
    private String index;

    RuleOp(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(String index) {
        for (RuleOp c : RuleOp.values()) {
            if (c.getIndex() .equals(index) ) {
                return c.name;
            }
        }
        return null;
    }

    public static String getIndex(String name) {
        for (RuleOp c : RuleOp.values()) {
            if (c.getName() .equals(name) ) {
                return c.index;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
