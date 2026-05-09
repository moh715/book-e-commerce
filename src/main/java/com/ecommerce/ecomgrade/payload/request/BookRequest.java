// BookRequest.java
package com.ecommerce.ecomgrade.payload.request;
import jakarta.validation.constraints.*;

public class BookRequest {
    @NotBlank(message="Title is required")
    private String title;

    @NotBlank(message="Author is required")
    private String author;

    @NotNull(message="Price is required")
    @Positive(message="Price must be positive")
    private Double price;

    @NotNull(message="Stock is required")
    @Positive(message="Stock must be positive")
    private Integer stock;

    private String imageUrl;
    private Long categoryId;

    public BookRequest(){}
    public String getTitle(){return title;} public void setTitle(String t){this.title=t;}
    public String getAuthor(){return author;} public void setAuthor(String a){this.author=a;}
    public Double getPrice(){return price;} public void setPrice(Double p){this.price=p;}
    public Integer getStock(){return stock;} public void setStock(Integer s){this.stock=s;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String u){this.imageUrl=u;}
    public Long getCategoryId(){return categoryId;} public void setCategoryId(Long c){this.categoryId=c;}
}