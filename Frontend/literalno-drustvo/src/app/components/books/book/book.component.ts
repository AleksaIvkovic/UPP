import { Component, Input, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book.model';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {
  @Input() book: Book;
  genreImage = '';

  constructor(
    private uploadService: UploadService
  ) { }

  ngOnInit(): void {
    switch (this.book.genre) {
      case 'Comedy':
        this.genreImage = 'cinema';
        break;
      case 'Drama':
        this.genreImage = 'theater';
        break;
      case 'Romance':
        this.genreImage = 'hearts';
        break;
      case 'Kids':
        this.genreImage = 'teddy-bear';
        break;
      case 'Horror':
        this.genreImage = 'ghost';
        break;
      case 'History':
        this.genreImage = 'history';
        break;
    }
  }

  downloadBook(fileName) {
    this.uploadService.download(fileName).subscribe(
      (blob: any) => {
        const a = document.createElement('a')
        const objectUrl = URL.createObjectURL(blob)
        a.href = objectUrl
        a.download = fileName;
        a.click();
        URL.revokeObjectURL(objectUrl);
      },
      (error) => {
        console.log('Error: ' + error);;
      });
  }

}
