package main.com.simpleonlineshop.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {

    private static final String FORMAT = "/WEB-INF/jsp/%s.jsp";

    public static String getPath(String name){

        return String.format(FORMAT,name);
    }
}
