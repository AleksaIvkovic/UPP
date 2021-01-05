import { Component, OnInit } from '@angular/core';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {
  books = [];

  constructor(
    private bookService: BookService
  ) { }

  ngOnInit(): void {
    this.bookService.getBooks().subscribe(
      (res:any) => {
        this.books = res;
      },
      err => {
        console.log(err);
      }
    )
  }
}
