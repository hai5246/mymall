package com.zhc.mymall.search.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

//相当于JPA的entity
@Document(indexName = "shoppingmall", type = "items")//与elasticsearch索引做映射
public class EsItem implements Serializable {
    //定义经常搜索的字段，不用整张表
    @Id //唯一标识
    private Long id;
    @Field(analyzer = "id_max_word", type = FieldType.Text)//粒度大，多一些
    private String title;
    private String image; // 不写就是keyword,不可分词（text）
    private Double price;
    private Long goodsId;
    @Field(type = FieldType.Keyword) //不可做分词，聚合查询时必须是keyword
    private String category;
    private String brand;
    @Field(analyzer = "ik_smart", type = FieldType.Text)//粒度小，少一些
    private String seller;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "EsItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", goodsId=" + goodsId +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", seller='" + seller + '\'' +
                '}';
    }
}
