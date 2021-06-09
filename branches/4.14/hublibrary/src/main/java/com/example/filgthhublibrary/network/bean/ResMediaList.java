package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;
import java.util.List;
public class ResMediaList implements Serializable{
    private List<FileListModel> flightGetModelList;
    private int pages;
    private int total;

    public List<FileListModel> getFlightGetModelList() {
        return flightGetModelList;
    }

    public void setFlightGetModelList(List<FileListModel> flightGetModelList) {
        this.flightGetModelList = flightGetModelList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class FileListModel implements Serializable {

        private String name;
        private String path;
        private String category;
        private Double size;
        private Double date;
        private String thumbnail;
        private String metadataPath;
        private String contentType;
        private String sn;
        private String account;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Double getSize() {
            return size;
        }

        public void setSize(Double size) {
            this.size = size;
        }

        public Double getDate() {
            return date;
        }

        public void setDate(Double date) {
            this.date = date;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getMetadataPath() {
            return metadataPath;
        }

        public void setMetadataPath(String metadataPath) {
            this.metadataPath = metadataPath;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

}

}