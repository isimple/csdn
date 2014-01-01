/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yang
 */
public class ResourceInfo {

    private String name;
    private String url;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResourceInfo other = (ResourceInfo) obj;
        if (other.url.equals(this.url)) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.url != null ? this.url.hashCode() : 0);
        return hash;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name + " ---- " + url ;
    }

    public ResourceInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ResourceInfo() {
    }


    public static void main(String[] args){
        ResourceInfo info1 = new ResourceInfo("king","123dsf");
        ResourceInfo info2 = new ResourceInfo("werewr","123");
        List<ResourceInfo> list = new ArrayList<ResourceInfo>();
        list.add(info1);
        if(list.contains(info2)){
            System.out.println("确实包含了！！");
        }
    }

}
