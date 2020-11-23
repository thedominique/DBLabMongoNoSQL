package booksdbclient.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.regex.Pattern;

public class DocBooksDB implements BooksDbInterface{
	private static final String CASE_INSENSITIVE = null;
	private MongoCollection<Document> collection;
	MongoDatabase dabase;

	@Override
	public boolean connect(String database) throws IOException, SQLException, ClassNotFoundException {
		
		MongoClient mongoClient = MongoClients.create();
		this.dabase = mongoClient.getDatabase("test");
		this.collection = dabase.getCollection("books");
		
		return true;
	}

	@Override
	public void disconnect() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Book> searchBooksByTitle(String s) throws IOException, SQLException {
		Pattern pattern = Pattern .compile(s,Pattern.CASE_INSENSITIVE);
		Bson bsonFilter = Filters.regex("title", pattern);
		return query(bsonFilter);
	}

	@Override
	public List<Book> searchBooksByAuthor(String s) throws IOException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> searchBooksByISBN(String s) throws IOException, SQLException {
		Pattern pattern = Pattern .compile(s,Pattern.CASE_INSENSITIVE);
		Bson bsonFilter = Filters.regex("ISBN", pattern);
		
		return query(bsonFilter);
	}

	@Override
	public List<Book> searchBooksByGenre(String s) throws IOException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> searchBooksByRating(String s) throws IOException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private  List<Book> query(Bson bsonFilter){
		FindIterable<Document> findIt = collection.find(bsonFilter);
		ArrayList<Book> list = new ArrayList<Book>();
		for(MongoCursor<Document> cursor = findIt.iterator(); cursor.hasNext();) {
			Document docu = cursor.next(), arrayOfAuthorsDoc = (Document) docu.get("authors");
			
			
		
			System.out.println(arrayOfAuthorsDoc.toString()+ "JESUS");
			/*for(MongoCursor<Document> cur = findIt.iterator(); cursor.hasNext();) {
				Docuemt aDoc = (Document) arrayOfAuthorsDoc.get("author");
				
			}
			ArrayList<Author> authors = new ArrayList<Author>();*/
			
			Book book = new Book(docu.getString("ISBN"), docu.getString("title"), docu.getString("publisher"), docu.getString("genre"), docu.getDouble("rating").intValue(), new ArrayList<Author>());
			System.out.println(book.getIsbn());
			list.add(book);
		}			
		return list;
	}

	@Override
	public boolean checkExistingAuthor(Author author) throws IOException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addNewAuthor(Author author) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertBokk(Book book) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBook(String newValue, String isbn, String column_name) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAuthor(String isbn, Author a) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
