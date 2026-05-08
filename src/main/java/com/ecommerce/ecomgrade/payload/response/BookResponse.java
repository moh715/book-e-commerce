package com.ecommerce.ecomgrade.payload.response;

public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private String categoryName;

    public BookResponse() {}

    public BookResponse(Long id, String title, String author,
                        Double price, Integer stock, String imageUrl, String categoryName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}