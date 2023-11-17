package com.experis.course.springlibrary.model;

import com.experis.course.springlibrary.validation.YearPastOrPresent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "Title must not be blank")
  @Size(max = 255, message = "Length must be less than 255")
  private String title;

  @NotBlank(message = "Authors must not be blank")
  @Size(max = 255, message = "Length must be less than 255")
  private String authors;

  @NotBlank(message = "Publisher must not be blank")
  @Size(max = 255, message = "Length must be less than 255")
  private String publisher;

  @NotBlank(message = "ISBN must not be blank")
  @Size(max = 13, message = "Length must be less than 255")
  @Column(length = 13, nullable = false, unique = true)
  private String isbn;

  @NotNull(message = "Year must not be null")
  @YearPastOrPresent
  private Integer year;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @Lob
  private String synopsis;

  @Min(0)
  private Integer numberOfCopies;
  @OneToMany(mappedBy = "book", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Borrowing> borrowings = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Category> categories;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public Integer getNumberOfCopies() {
    return numberOfCopies;
  }

  public void setNumberOfCopies(Integer numberOfCopies) {
    this.numberOfCopies = numberOfCopies;
  }

  public List<Borrowing> getBorrowings() {
    return borrowings;
  }

  public void setBorrowings(List<Borrowing> borrowings) {
    this.borrowings = borrowings;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  // metodo per calcolare un attributo derivato
  public int getAvailableCopies() {
    // togliere dal numero di copie disponibili
    // il numero di prestiti che non sono ancora stati ritornati
    int notReturnedBorrowings = 0;
    for (Borrowing b : borrowings) {
      if (b.getReturnDate() == null) {
        notReturnedBorrowings++;
      }
    }
    return numberOfCopies - notReturnedBorrowings;
  }

}
