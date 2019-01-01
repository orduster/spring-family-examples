package com.ordust;

import com.ordust.util.JsoupUtil;
import org.junit.Test;

/**
 * 测试方法
 */
public class test {

    @Test
    public void demo(){
        //创建测试的 Html代码
        String testHtml = "<div class='div'style='height: 100px;'>div 标签的内容 </div><p class='div'style='width: 50px;'>p 标签的内容 </p>";
        String result = JsoupUtil.clean(testHtml);
        System.out.println(result);
    }
}
