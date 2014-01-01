/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 *
 * @author yang
 */
public interface CSDNUrl {

    /**
     * 使用url中的title进行资源搜索
     */
    String SEARCH_BY_TITLE = "http://download.csdn.net/search?sort=&title=android&body=&user_name=&tag=&categoryid=&source_money=0-0&created_at=&per_page=1";
    /**
     * <我的下载>首页
     */
    String MY_DOWN_PAGE = "http://download.csdn.net/my/downloads";

}
