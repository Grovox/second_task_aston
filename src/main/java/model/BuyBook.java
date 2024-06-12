package model;

public class BuyBook {
    private int buyBookId;
    private float price;
    private int amount;
    private int userId;
    private int bookId;

    public int getBuyBookId() {
        return buyBookId;
    }

    public void setBuyBookId(int buyBookId) {
        this.buyBookId = buyBookId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
