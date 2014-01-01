/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.CSDNUrl;
import domain.ResourceInfo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author yang
 */
public class Brush {

    //总共已下载页数
    private int totalPageNumbers;
    private List<ResourceInfo> lists = new ArrayList<ResourceInfo>();
    private String cookies;
    public static final String[] commentContent = new String[]{
        "资源还可以，看了一下，收获不少，谢谢！",
        "非常谢谢大神，确实实用的资源",
        "确实很好的资源啊",
        "学习学习，适合学习。",
        "对我有帮助，谢谢啦。",
        "说真的，还可以，学习了！",
        "很实用呢 蛮好的"
    };
    public static Random random = new Random();
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    private HttpRequester requester = new HttpRequester("UTF-8");

    public Brush(String user, String password) {
        this.username = user;
        this.password = password;
    }

    public Brush() {
    }

    /**
     * 
     * @param username
     * @param password
     * @return false=失败;true=成功
     * @throws Exception 这个是加密连接，HTTPS？使用Http(s)UrlConnection？
     */
    public boolean login() {
        try {
            String surl = "https://passport.csdn.net";
            String addition = "/ajax/accounthandler.ashx?t=log&u=#%s#&p=#%s#&remember=0&f=http%3A%2F%2Fwww.csdn.net%2F&rand=0.36711792764253914";
            String result = addition.replaceFirst("#%s#", this.username).replace("#%s#", this.password);
            surl += result;

            Map<String, String> propertys = new HashMap<String, String>();
            propertys.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon;)");
            propertys.put("referer", "https://passport.csdn.net/account/loginbox?callback=logined&hidethird=1&from=http%3a%2f%2fwww.csdn.net%2f");
            HttpResponse response = requester.sendGet(surl, null, propertys);

            this.cookies = response.getCookie() + "domain=csdn.net;path=/";
            //TODO:
            //System.out.println(cookies);
            if (this.cookies == null || "".equals(this.cookies)) {
                return false;
            } else {
                return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    /**
     * 查询我的下载总共有多少页，用来在遍历待评价列表
     * @param context
     * @return
     */
    private int getTotalMyDownPage(String context) {
        if (context == null) {
            return -1;
        }
        int nPos = context.lastIndexOf("尾页");
        if (nPos == -1) {
            return -1;
        }
        nPos -= 2;
        int nStartPos = context.lastIndexOf("/", nPos);
        String strTotal = context.substring(nStartPos + 1, nPos);// - nStartPos - 1);
        return Integer.parseInt(strTotal);
    }

    /**
     * 得到某个页面的内容
     * @param urlString 具体的URL！
     * @return
     * @throws IOException
     */
    private String getPageContent(String urlString) throws IOException {
        Map<String, String> propertys = new HashMap<String, String>();
        propertys.put("Cookie", cookies);
        propertys.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon;)");
        return requester.sendGet(urlString, null, propertys).getContent();

    }

    /**
     * 通过#comment来获取要评价资源的ID，加入到lists
     * @param downedPage
     * @return
     */
    public boolean parseDownResource(String downedPage) {
        int nPos = 0;
        int n = 0;
        while ((nPos = downedPage.indexOf("#comment", n)) != -1) {
            //<a href="/detail/regenesis0264/296421#comment" class="recom_plese">
            n = nPos + 1;
            int nStartPos = downedPage.lastIndexOf("/", nPos);

            String strUrl = downedPage.substring(nStartPos + 1, nPos);// - nStartPos - 1);

            ResourceInfo info = new ResourceInfo();
            info.setUrl(strUrl);
            //找资源的名字
            nStartPos = downedPage.indexOf(strUrl, nPos + 1);
            if (nStartPos == -1) {
                return false;			//可能被删除掉了
            }
            nStartPos += 2;
            nStartPos += strUrl.length();
            int nEndPos = downedPage.indexOf("</a>", nStartPos);
            String ResourceName = downedPage.substring(nStartPos, nEndPos);// - nStartPos);
            info.setName(ResourceName);
            if(!lists.contains(info)){
                lists.add(info);
            }
        }
        return true;
    }

    /**
     * 评价指定资源
     * @param n
     * @return
     */
    public boolean commentById(String n) {
        try {
            String comment = getCommentRandom();
            String url = "http://download.csdn.net";
            String addition = "/index.php/comment/post_comment?jsonpcallback=jsonp1376105098842&sourceid=#%d#&content=#comment#&rating=4&t=1376105166581 ";
            addition = addition.replaceFirst("#%d#", n + "").replaceFirst("#comment#", comment);
            url += addition;
            String body = getPageContent(url);
            if (body.indexOf("-4") != -1) {
                return false;
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private String getCommentRandom() {
        try {
            String temp = commentContent[random.nextInt(commentContent.length)];
            temp = URLEncoder.encode(temp, "utf-8");
            temp = temp.replaceAll("%", "%%");
            return temp;
        } catch (UnsupportedEncodingException ex) {
            //unreach
            return "";
        }
    }

    public List<ResourceInfo> getLists() {
        return lists;
    }

    public void setLists(List<ResourceInfo> lists) {
        this.lists = lists;
    }

    /**
     * 获取待评价资源列表
     * @throws IOException
     */
    public void fetchNeedCommentList() throws IOException {
        lists.clear();
        String myDownPage = getPageContent(CSDNUrl.MY_DOWN_PAGE);
        //System.out.println(myDownPage);
        this.totalPageNumbers = getTotalMyDownPage(myDownPage);
        parseDownResource(myDownPage);
        //查看下一页
        //如果连续3页都没有comment默认认为后面都评价了提高效率
        int flag = 0;
        for (int i = 2; i <= this.totalPageNumbers; i++) {
            int beginSize = this.lists.size();
            String url = "http://download.csdn.net/my/downloads/" + i;
            String pageContent = getPageContent(url);
            parseDownResource(pageContent);
            int endSize = this.lists.size();
            if(beginSize == endSize){
                flag++;
            }else{
                flag = 0;
            }
            if(flag >= 3){
                break;
            }
        }
    }

    /**
     * 进入到下载界面
     * 可能对具体页面有格式要求
     * @param resouceSearchPage For example:
     * <br/>http://download.csdn.net/search?sort=&title=android
     * <br/>http://bbs.csdn.net/topics/390526352
     * @return
     */
    public List<String> resoucePage2RescourceList(String resoucePage,boolean single) throws IOException {
        //存放类似http://download.csdn.net/download/u010723811/6600229的链接
        List<String> listTemp = new ArrayList<String>();
        String resouceSearchPageContent = getPageContent(resoucePage);
        //Pattern pattern = Pattern.compile(">(http://download.csdn.net/detail/.+?)</a></dd>");
        //href="http://download.csdn.net/detail/tiger86521/5300336" 
        Pattern pattern = Pattern.compile("href=\"(http://download.csdn.net/detail/.+?)\" ");
        Matcher matcher = pattern.matcher(resouceSearchPageContent);
        while (matcher.find()) {
            String url = matcher.group(1);
            url = url.replace("detail", "download");
            if(!listTemp.contains(url)){
                listTemp.add(url);
            }
            if(single){
                break;
            }
        }
        return listTemp;
    }

    private List<String> resoucePage2RescourceList(String resoucePage) throws IOException {
        return resoucePage2RescourceList(resoucePage,false);
    }

    /**
     * 下载某个网页上存在的可用资源
     * @param resoucePage
     * @throws IOException
     */
    public void downResoucesByResourcePath(String resoucePage) throws IOException {
        downResouces(resoucePage2RescourceList(resoucePage));
    }
    /**
     * 下载指定资源
     * @param resouceID
     * @return
     * @throws IOException
     */
    public String downResoucesByResourceID(String resouceID) throws IOException {
        String sPath = "http://download.csdn.net/source/" + resouceID;
        Map<String, String> propertys = new HashMap<String, String>();
        propertys.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon;)");
        HttpResponse response = requester.sendGet(sPath,null,propertys);
        String reUrl = "http://download.csdn.net" + response.getPath();
        reUrl = reUrl.replace("detail", "download");
        List list = new ArrayList<String>();
        list.add(reUrl);
        return downResouces(list);
    }

    public String downResouce(String path) throws IOException{
        List<String> list = new ArrayList<String>();
        list.add(path);
        return downResouces(list);
    }
    /**
     * 将download-->http://download.csdn.net/index.php/source/do_download
     * 然后请求下载链接，单个任务则返回链接，多个任务则返回空字符串
     */
    private String downResouces(List resouceList) throws IOException {
        String parseResult = "";
        for (Object object : resouceList) {
            String url = getResouceRealPath(object.toString());
            if (null == url || "".equals(url)) {
                System.out.println("download-->realPath解析错误！！");
                continue;
            } else {
                //开始下载——requestHeader!!
                //POST /index.php/source/do_download/6578103/langyifei/c467adb10d23416c17aa17aa02557969 HTTP/1.1
                //Accept: text/html, application/xhtml+xml, *_/_*
                //Referer: http://download.csdn.net/download/langyifei/6578103
                //Accept-Language: zh-CN
                //User-Agent: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)
                //Content-Type: application/x-www-form-urlencoded
                //Accept-Encoding: gzip, deflate
                //Host: download.csdn.net
                //Content-Length: 20
                //Connection: Keep-Alive
                //Cache-Control: no-cache
                //Cookie: __utma=17226283.109353757.1384908826.1385435026.1385444351.6; __utmz=17226283.1385431969.4.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=show_validate_pop(); uuid_tt_dd=-4391624736800619545_20131120; __message_sys_msg_id=2182; __message_gu_msg_id=0; __message_cnel_msg_id=0; __message_in_school=0; __message_district_code=350000; UN=getseter; __utmb=17226283.17.10.1385444351; abv=mwuwun; __utmc=17226283; UserName=getseter; UserInfo=HSqaKlAITQYIq7y37a6QGJzjJA0u0TscDbKcxwTt38Gkh27quAfQfYiih8sC%2bQXtWMK8M%2f%2fr%2bOWuQreEMOsbl5PJDC8NVr7fdq6oZX6pLTKTL%2bkPtMaHJMfr%2fRa8pjrf; UserNick=getseter; access-token=0697321f-faad-412b-b74e-71be6e55db3e; download_first=1; PHPSESSID=f0224a8ed731f5fe59be23e4bf23f921
                //
                //ds=dx&validate_code=
                //格式结束
                String prepareCookie = "__utma=17226283.109353757.1384908826.1385435026.1385444351.6; __utmz=17226283.1385431969.4.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=show_validate_pop(); uuid_tt_dd=-4391624736800619545_20131120; __message_sys_msg_id=2182; __message_gu_msg_id=0; __message_cnel_msg_id=0; __message_in_school=0; __message_district_code=350000; __utmb=17226283.17.10.1385444351; #myrandom# __utmc=17226283; #mycookie# download_first=1; PHPSESSID=f0224a8ed731f5fe59be23e4bf23f921";
                String partCookie = this.cookies.substring(0, this.cookies.indexOf("domain"));
                String cookie = prepareCookie.replace("#mycookie#", partCookie);
                //abv=mwuwun;
                String randString = getCharacterAndNumber(1, 3) + "=mwu" + getCharacterAndNumber(0, 3) + ";";
                cookie = cookie.replace("#myrandom#", randString);
                //System.out.println(cookie);
                HttpRequester request = new HttpRequester();
                Map<String, String> params = new HashMap<String, String>();
                params.put("ds", "dx");
                params.put("validate_code", "");
                Map<String, String> propertys = new HashMap<String, String>();
                propertys.put("Accept", "text/html, application/xhtml+xml, */*");
                propertys.put("Referer", object.toString());
                propertys.put("Accept-Language", "zh-CN");
                propertys.put("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
                propertys.put("Content-Type", "application/x-www-form-urlencoded");
                propertys.put("Accept-Encoding", "gzip, deflate");
                propertys.put("Cache-Control", "no-cache");
                propertys.put("Cookie", cookie);
                HttpResponse hr = request.sendPost(url, params, propertys);
                String resultContent = hr.getContent();
                if (resultContent.contains("parent.reset_validate_code()")) {
                    String patternString = "window.location.href='(.+?)';";
                    parseResult = findStringPyPattern(resultContent, patternString);
                    System.out.println(object + "-----" + parseResult);
                } else {
                    System.out.println("-_-解析失败:" + object);
                }
            }
        }
        return parseResult;
    }

    private String findStringPyPattern(String content,String pattern){
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher1 = pattern1.matcher(content);
        if (matcher1.find()) {
            return matcher1.group(1);
        } else {
            return "";
        }
    }

    private String getResouceRealPath(String downloadPath) throws IOException {

        String downloadPathContent = getPageContent(downloadPath);
        //搜索下载字符串
        String pattern = "action=\"(http://download.csdn.net/index.php/.+?)\" target";
        return findStringPyPattern(downloadPathContent, pattern);
    }

    /**
     * 随机组成字母或者数字（字母开头）
     * @param isChar 1为字母，0为随机
     * @param length 返回字符串长度
     * @return
     */
    public String getCharacterAndNumber(int isChar, int length) {
        String val = "";
        //字母开头
        val += (char) (97 + random.nextInt(26));
        for (int i = 1; i < length; i++) {
            String charOrNum = "";
            if (isChar == 1) {
                charOrNum = "char";
            } else {
                charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            }
            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                val += (char) (97 + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }

/////////////////////////////////////////////测试//////////////////////////////
    private void decodeComment(String test) {
        try {
            test = test.replaceAll("%%", "%");
            String temp = URLDecoder.decode(test, "utf-8");
            System.out.println(temp);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Brush.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class AutoCommentThread implements Runnable {

        public void run() {
            try {
                Thread.sleep(65 * 1000);
                ResourceInfo info = lists.get(0);
                boolean ret = commentById(info.getUrl());
                if (ret == false) {
                    System.out.println("评价失败！！！");
                }
                lists.remove(info);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void startAutoComment() {
        if (lists.size() == 0) {
            System.out.println("没有需要评价的资源!");
        } else {
            new Thread(new AutoCommentThread()).start();
        }
    }

    /**
     * 输出列表待评价资源列表，用于test
     */
    private void printList(List list) {
        if (list == null) {
            list = lists;
        }
        for (Object info : list) {
            System.out.println(info);
        }
    }

    /**
     * 测试各方法
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Brush b = new Brush();
//        b.login();
//        System.out.println(b.downResoucesByResourceID("6564177"));
//        System.out.println(b.downResoucesByResourceID("2318298"));
//        //获取当前列表
//        b.fetchNeedCommentList();
//        b.printList();
//        b.commentById("438441");
//        List list = b.resoucePage2RescourceList("http://bbs.csdn.net/topics/390526352");
//        b.printList(list);

    }
}
