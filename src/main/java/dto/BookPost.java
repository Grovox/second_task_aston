package dto;

import java.util.List;

public class BookPost {
    private String title;
    private float price;
    private int amount;
    private List<AuthorId> authorsId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
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

    public List<AuthorId> getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(List<AuthorId> authorsId) {
        this.authorsId = authorsId;
    }
}
