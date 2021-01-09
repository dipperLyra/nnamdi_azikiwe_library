package com.library.nnamdi_azikiwe.service.book;

import com.library.nnamdi_azikiwe.model.entity.Book;
import com.library.nnamdi_azikiwe.model.entity.BorrowedBook;
import com.library.nnamdi_azikiwe.model.projection.BookSearch;
import com.library.nnamdi_azikiwe.model.requests.BookUpdateRequest;
import com.library.nnamdi_azikiwe.model.requests.BorrowBookRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Book add(Book book);
    BorrowedBook lend(List<BorrowBookRequest> bookRequests) throws Exception;
    Book update(BookUpdateRequest bookUpdateRequest);
    List<Book> list();
    List<BookSearch> search(String title);
    void delete(int id);
}
