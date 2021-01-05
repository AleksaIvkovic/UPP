export class Book {
    constructor(
        public title: string,
        public writer: string,
        public genre: string,
        public synopsis: string,
        public numberOfPages: number,
        public placeOfPublication: string,
        public yearOfPublication: number,
        public publisher: string,
        public plagiarism: boolean,
    ) {}
}