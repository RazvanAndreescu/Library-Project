Entity
-AuthorEntity
-BookEntity

Utils 
-HibernateUtils
-LoggerUtils

Repositorys
-AuthorRepository  (addAuthor, private getAuthorsList, findAuthorBy name)
-BookRepository 
==Used for implementing the methos to get something from database

Services
-AuthorService (addAuthor, deleteAuthorById)
-BookService (addBook)
==Here are methods called from MenuViewHandler. These methods takes inputs from MenuViewHandler and use methods from repositorys to get what it needs.


Flow -deleteAuthor- ask for id or firstName+lastName, get author by Id or by firstName+lastName, check if he has books, delete him.

Flow -deleteBook - (ask for id or title)-done, (get book by Id or by title) -done , check if the the author has only that book and if it is true, then ask user if he want to delete him from the database