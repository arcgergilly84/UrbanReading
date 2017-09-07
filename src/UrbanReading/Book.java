package UrbanReading;

public class Book {

    private String title;
    private String ISBN;
    private String subject;
    private String description;
    private String image;
    private int book_id;
    private int pdf_id;

    public Book(String title, String ISBN, String subject, String description, String image,int book_id, int pdf_id) {
        this.title = title;
        this.ISBN = ISBN;
        this.subject = subject;
        this.description = description;
        this.image = image;
        this.book_id = book_id;
        this.pdf_id = pdf_id;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getBook_id() { return book_id; }

    public int getPdf_id(){ return pdf_id; }





}
