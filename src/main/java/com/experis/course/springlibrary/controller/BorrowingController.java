package com.experis.course.springlibrary.controller;

import com.experis.course.springlibrary.exceptions.BookNotFoundException;
import com.experis.course.springlibrary.exceptions.BorrowingNotFoundException;
import com.experis.course.springlibrary.model.Borrowing;
import com.experis.course.springlibrary.service.BorrowingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

  @Autowired
  BorrowingService borrowingService;

  @GetMapping("/create")
  public String create(@RequestParam Integer bookId, Model model) {
    try {
      model.addAttribute("borrowing", borrowingService.createNewBorrowing(bookId));
      return "borrowings/form";
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @PostMapping("/create")
  public String doCreate(@Valid @ModelAttribute("borrowing") Borrowing formBorrowing,
      BindingResult bindingResult) {
    // valido i dati
    if (bindingResult.hasErrors()) {
      return "borrowings/form";
    }
    // salvo su database
    Borrowing savedBorrowing = borrowingService.saveBorrowing(formBorrowing);
    // redirect al dettaglio del book
    return "redirect:/books/show/" + formBorrowing.getBook().getId();
  }

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    try {
      Borrowing borrowing = borrowingService.getBorrowing(id);
      model.addAttribute("borrowing", borrowing);
      return "borrowings/form";
    } catch (BorrowingNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @PostMapping("/edit/{id}")
  public String doEdit(@PathVariable Integer id,
      @Valid @ModelAttribute("borrowing") Borrowing formBorrowing, BindingResult bindingResult) {
    // valido
    if (bindingResult.hasErrors()) {
      return "borrowings/form";
    }
    // salvo su db
    Borrowing savedBorrowing = borrowingService.saveBorrowing(formBorrowing);
    // redirect
    return "redirect:/books/show/" + formBorrowing.getBook().getId();
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    try {
      // cancello il borrowing
      Borrowing borrowingToDelete = borrowingService.getBorrowing(id);
      borrowingService.deleteBorrowing(borrowingToDelete);
      // faccio la redirect alla pagina di dettaglio del book
      redirectAttributes.addFlashAttribute("message", "Borrowing deleted!");
      return "redirect:/books/show/" + borrowingToDelete.getBook().getId();
    } catch (BorrowingNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
}
