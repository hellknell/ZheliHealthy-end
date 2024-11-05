package com.Yu.his.generator.help;

public class Criteria extends GeneratedCriteria {
    private MyBatisWrapper help;
    //true 表示and false表示or
    private boolean andOrOr = true;
    protected Criteria() {
        super();
    }
    public MyBatisWrapper getHelp() {
        return help;
    }
    public void setHelp(MyBatisWrapper help) {
        this.help = help;
    }
    public boolean isAndOrOr() {
        return andOrOr;
    }
    public void setAndOrOr(boolean andOrOr) {
        this.andOrOr = andOrOr;
    }
}