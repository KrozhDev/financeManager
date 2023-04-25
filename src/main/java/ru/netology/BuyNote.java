package ru.netology;

public class BuyNote {

    private String title = null;
    private String date = null;
    private int sum = 0;

    public String getTitle() {
        return title;
    }

    public Integer getSum() {
        return sum;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString(){
        return "title: " + title + " date: " + date + " sum: " + sum;
    }
}
