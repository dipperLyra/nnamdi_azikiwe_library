package com.library.nnamdi_azikiwe.service.book.impl;

import com.library.nnamdi_azikiwe.model.entity.Book;
import com.library.nnamdi_azikiwe.model.entity.BorrowedBook;
import com.library.nnamdi_azikiwe.model.entity.User;
import com.library.nnamdi_azikiwe.model.projection.BookSearch;
import com.library.nnamdi_azikiwe.model.repository.BookRepository;
import com.library.nnamdi_azikiwe.model.repository.BorrowRepository;
import com.library.nnamdi_azikiwe.model.repository.UserRepository;
import com.library.nnamdi_azikiwe.model.requests.BookUpdateRequest;
import com.library.nnamdi_azikiwe.model.requests.BorrowBookRequest;
import com.library.nnamdi_azikiwe.service.book.BookService;
import com.library.nnamdi_azikiwe.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepo;
    @Autowired
    BorrowRepository borrowRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    TimeUtils timeUtils;

    public BookServiceImpl(){}
    /*
    Todo: validate object parameters
     */
    public BookServiceImpl(BookRepository bookRepo){
        this.bookRepo = bookRepo;
    }

    public Book add(Book book) {
        return bookRepo.save(book);
    }

    public BorrowedBook lend(List<BorrowBookRequest> bookRequests) throws Exception {
        List<Book> books = new ArrayList<>();
        User user = new User();

        for(BorrowBookRequest bookRequest : bookRequests) {
            Book book = bookRepo.findByTitleAndCallNumber(bookRequest.getTitle(), bookRequest.getCallNumber())
                    .orElseThrow(() -> new Exception("Book with the supplied details not found"));
            user = userRepo.findByUsername(bookRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + bookRequest.getUsername()));
            books.add(book);
        }

        return new BorrowedBook(books, user, timeUtils.currentDateTime());
    }

    public Book update(BookUpdateRequest bookUpdateRequest) {
        Optional<Book> book = bookRepo.findById(bookUpdateRequest.getId());
        if (book.isPresent()) {
            book.get().setTitle(bookUpdateRequest.getTitle().toLowerCase());
            book.get().setAuthor(bookUpdateRequest.getAuthor());
            book.get().setCallNumber(bookUpdateRequest.getCallNumber());
            return bookRepo.save(book.get());
        }
        return null;
    }

    public List<Book> list() {
        return bookRepo.findAll();
    }

    public List<BookSearch> search(String title) {
        return bookRepo.findByTitleContaining(title);
    }

    public void delete(int id) {
        Optional<Book> book = bookRepo.findById(id);
        book.ifPresent(value -> bookRepo.delete(value));
    }

}
