package demo;


import demo.MiniApplicationContext;
import demo.MiniApplicationContext;
import org.junit.jupiter.api.Test;
import service.BookService;

import java.awt.print.Book;

import static org.junit.jupiter.api.Assertions.*;

public class IoCTest {
    String applicationcontext="C:\\Users\\JADE\\Desktop\\assignment3\\src\\test\\resources\\applicationContext.xml";
    @Test
    MiniApplicationContext ctx = new MiniApplicationContext("applicationContext.xml");
    BookService bookService = (BookService) ctx.getBean("bookService");
    bookService.save();


}
