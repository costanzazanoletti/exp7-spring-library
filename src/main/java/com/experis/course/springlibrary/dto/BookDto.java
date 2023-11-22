package com.experis.course.springlibrary.dto;

import com.experis.course.springlibrary.model.Category;
import com.experis.course.springlibrary.validation.YearPastOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class BookDto {


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
  private String isbn;

  @NotNull(message = "Year must not be null")
  @YearPastOrPresent
  private Integer year;

  private String synopsis;

  @Min(0)
  private Integer numberOfCopies;


  private MultipartFile coverFile;


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

  public MultipartFile getCoverFile() {
    return coverFile;
  }

  public void setCoverFile(MultipartFile coverFile) {
    this.coverFile = coverFile;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }
}
