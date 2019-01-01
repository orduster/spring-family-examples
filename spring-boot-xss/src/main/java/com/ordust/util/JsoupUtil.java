package com.ordust.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * XSS 过滤工具类
 */
public class JsoupUtil {
    //这里白名单采用 basicWithImages
    private static final Whitelist WHITELIST = Whitelist.basicWithImages();

    //配置过滤化参数，不对代码进行格式化
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    static {
        //富文本编辑时一些样式是采用 style 来进行实现的，如红色字体 style="color:red;" 所以需要给所有(all)标签添加 style 属性
        WHITELIST.addAttributes(":all", "style");
    }

    public static String clean(String content) {
        return Jsoup.clean(content, "", WHITELIST, OUTPUT_SETTINGS);
    }

}
