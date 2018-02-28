package util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	
	public WebUtil() {
    }

    public static String getServerUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        return basePath;
    }
}
