package com.Yu.his;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/5 15:51
 */
public class RuleTest {

    public static void main(String[] args) throws Exception {
        String rule = """
                    import java.math.BigDecimal;
                    p=new BigDecimal(price);
                    n=new BigDecimal(number);
                    result=p.multiply(n).subtract(new BigDecimal(number/2).multiply(new BigDecimal("0.5")).multiply(p))
                """;
        ExpressRunner expressRunner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("price", 1000);
        context.put("number", 3);

        Object execute = expressRunner.execute(rule, context, null, true, false);
        System.out.println(execute.toString());
    }
}
