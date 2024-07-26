package com.example.kindle.payload;

import com.example.kindle.model.Book;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private List<Book> book;
}
