import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class MainMenu {

  public static final String ADD_BOOK_TEXT = "Contribute a book";
  public static final String CHECKOUT_TEXT = "Check out a book";
  public static final String RETURN_BOOK_TEXT = "Return a book";
  public static final String QUIT_TEXT = "Quit";

  public enum MenuOption {
    a(ADD_BOOK_TEXT),
    c(CHECKOUT_TEXT),
    r(RETURN_BOOK_TEXT),
    q(QUIT_TEXT);

    private String optionText;

    MenuOption(String optionText) {
      this.optionText = optionText;
    }

    public String toString() {
      return this.name() + ". " + this.optionText;
    }
  }

  @Override
  public String toString() {
    String output = "";
    for (MenuOption option : MenuOption.values()) {
      output += option.toString() + "\n";
    }
    return output;
  }

  public static List<Book> getBooks() {
    List<Book> books = new ArrayList<Book>();
    books.add(new Book("Attack on Titan"));
    books.add(new Book("Beauty and Beasts"));
    return books;
  }

  public void addBook(String title) {
    Book book = new Book(title);
  }


  public void promptUntilQuit() {

    MenuOption input = null;
    Scanner scanner = new Scanner(System.in);
    List<Book> books = MainMenu.getBooks();
    List<Book> checkOutBook = new ArrayList<>();
    do {
      System.out.println(this.toString());
      System.out.print("> ");
      try {
        input = MenuOption.valueOf(scanner.next());
      } catch (IllegalArgumentException error) {
        System.out.println("Please make a valid selection!");
      }

      if (input == MenuOption.a) {
        System.out.println("Please enter the title of the book");
        Scanner newBook = new Scanner(System.in);
        String title = newBook.nextLine();
        books.add(new Book(title));
        System.out.println("Thank you. You have successfully contributed a new book: " + title);
      } else if (input == MenuOption.c) {
        String allBooks = "";
        for (int i = 0; i < books.size(); i++) {
          allBooks += i + ". " + books.get(i) + "\n";
        }
        Book selectedBook = null;
        do {
          System.out.println("Please select from below books by their index: \n" + allBooks);
          Scanner checkOut = new Scanner(System.in);
          String checkStatus =checkOut.nextLine();
          if (checkStatus.isBlank()) {
            break;
          }
          try {
            int checkIndex = Integer.parseInt(checkStatus);
            selectedBook = books.get(checkIndex);
            books.remove(selectedBook);
            System.out.println("You checked out: " + selectedBook);
            checkOutBook.add(selectedBook);
          } catch (NumberFormatException exception) {
            System.out.println(
                "Invalid input!");
          }
          catch (InputMismatchException exception){
            System.out.println("Please enter a valid index!");
          }
          catch (IndexOutOfBoundsException exception){
            System.out.println("Please enter a valid index!");
          }
         }while (selectedBook == null);
        System.out.println("Back to main menu \n");
      } else if (input == MenuOption.r) {
        String currentHolds = "";
        for (int i = 0; i < checkOutBook.size(); i++) {
          currentHolds += i + ". " + checkOutBook.get(i) + "\n";
        }
        Book selectedBook = null;
        do {
          System.out.println("Please select from below books by their index: \n" + currentHolds);
          Scanner returnBook = new Scanner(System.in);
          String returnStatus =returnBook.nextLine();
          if (returnStatus.isBlank()) {
            break;
          }
          try {
            int returnIndex = Integer.parseInt(returnStatus);
            selectedBook = checkOutBook.get(returnIndex);
            checkOutBook.remove(selectedBook);
            System.out.println("You returned: " + selectedBook);
            books.add(selectedBook);
          } catch (NumberFormatException exception) {
            System.out.println(
                "Invalid input!");
          }
          catch (InputMismatchException exception){
            System.out.println("Please enter a valid index!");
          }
          catch (IndexOutOfBoundsException exception){
            System.out.println("Please enter a valid index!");
          }
        } while (selectedBook == null);
        System.out.println("Back to main menu \n");
      }
    }
    while (input != MenuOption.q);
    {
      System.out.println("Thanks! Come to the library again.");
      scanner.close();
    }
  }
}
