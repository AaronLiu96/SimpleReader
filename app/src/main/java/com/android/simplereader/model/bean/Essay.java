package com.android.simplereader.model.bean;

import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/30.
 */
public class Essay {

    //注释lalal
    public Res_Body showapi_res_body;

    public Res_Body getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(Res_Body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class Res_Body{
        public PageBean pagebean;

        public PageBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PageBean pagebean) {
            this.pagebean = pagebean;
        }

        public static class PageBean{
            private int allNum;
            private int allPages;
            private List<ContentList> contentlist;

            public int getAllNum() {
                return allNum;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getAllPages() {
                return allPages;
            }

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public List<ContentList> getContentlist() {
                return contentlist;
            }

            public void setContentlist(List<ContentList> contentlist) {
                this.contentlist = contentlist;
            }

            public static class ContentList{
                private String contentImg;
                private String title;
                private String date;
                private String url;
                private String userName;


                public String getContentImg() {
                    return contentImg;
                }

                public void setContentImg(String contentImg) {
                    this.contentImg = contentImg;
                }

                public String getEssayTitle() {
                    return title;
                }

                public void setEssayTitle(String title) {
                    this.title = title;
                }

                public String getEssayDate() {
                    return date;
                }

                public void setEssayDate(String date) {
                    this.date = date;
                }

                public String getEssayUrl() {
                    return url;
                }

                public void setEssayUrl(String url) {
                    this.url = url;
                }

                public String getEssayUserName() {
                    return userName;
                }

                public void setEssayUserName(String userName) {
                    this.userName = userName;
                }


            }
        }
    }

}