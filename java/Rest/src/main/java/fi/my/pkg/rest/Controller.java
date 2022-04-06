package fi.my.pkg.rest;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.Title;
import fi.my.pkg.service.BookService;
import fi.my.pkg.service.BookServiceImpl;

@RestController
public class Controller {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private final BookService bookService;

	public Controller() throws Exception {
		this.bookService = BookServiceImpl.instance();
	}

	@GetMapping("/")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@GetMapping("/list")
	public List<Book> listAllBooks(){
		return bookService.listBooks();
	}
	
	@GetMapping("/find")
	public Book findWithIsbn(@RequestParam(value = "isbn") String isbn){
		return bookService.findBook(new Isbn(isbn));
	}
	
	@GetMapping("/find")
	public Book findWithTitle(@RequestParam(value = "title") String title){
		return bookService.findBook(new Title(title));
	}
	
	@GetMapping("/find")
	public Book findWithId(@RequestParam(value = "id") String id){
		return bookService.findBook(new Id(id));
	}
	
	@GetMapping("/delete")
	public void deleteWithId(@RequestParam(value = "id") String id){
		Book book = bookService.findBook(new Id(id));
		if (book != null) bookService.delete(book);
	}
}