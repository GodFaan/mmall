package com.mmall.vo;

import java.math.BigDecimal;

/**
 * @program: mmall
<<<<<<< HEAD
 * @description: 前台展示的产品列表业务，view object
=======
 * @description: 产品列表业务，view object
>>>>>>> fe93ebe90c2c29b99ff8e9f1a3530a900eebd7af
 * @author: GodFan
 * @create: 2019-06-21 11:20
 **/
public class ProductListVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String sutitle;//副标题
    private String mainImage;
    private BigDecimal price;
    private Integer status;
    private String imageHost;//图片服务器的URL的前缀

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSutitle() {
        return sutitle;
    }

    public void setSutitle(String sutitle) {
        this.sutitle = sutitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
