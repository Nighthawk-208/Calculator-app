import java.io.*;
import java.util.*;

class Book implements Comparable<Book> {
    int bookId;
    String title;
    String author;
    String category;
    boolean issued;

    Book(int id, String t, String a, String c) {
        bookId = id;
        title = t;
        author = a;
        category = c;
        issued = false;
    }

    void markIssued() { issued = true; }
    void markReturned() { issued = false; }

    public int compareTo(Book b) {
        return title.compareToIgnoreCase(b.title);
    }

    String toCSV() {
        return bookId + "," + title + "," + author + "," + category + "," + issued;
    }

    static Book fromCSV(String s) {
        String[] p = s.split(",", 5);
        if (p.length < 5) return null;
        Book b = new Book(Integer.parseInt(p[0]), p[1], p[2], p[3]);
        b.issued = Boolean.parseBoolean(p[4]);
        return b;
    }
}

class Member {
    int memberId;
    String name;
    List<Integer> issuedBooks;

    Member(int id, String n) {
        memberId = id;
        name = n;
        issuedBooks = new ArrayList<>();
    }

    void addBook(int id) { issuedBooks.add(id); }
    void returnBook(int id) { issuedBooks.remove(Integer.valueOf(id)); }

    String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(memberId).append(",").append(name).append(",");
        for (int i = 0; i < issuedBooks.size(); i++) {
            if (i > 0) sb.append(";");
            sb.append(issuedBooks.get(i));
        }
        return sb.toString();
    }

    static Member fromCSV(String s) {
        String[] p = s.split(",", 3);
        Member m = new Member(Integer.parseInt(p[0]), p[1]);
        if (p.length == 3 && !p[2].isEmpty()) {
            for (String x : p[2].split(";")) {
                m.issuedBooks.add(Integer.parseInt(x));
            }
        }
        return m;
    }
}

class LibraryManagementSystem {

     Map<Integer, Book> books = new HashMap<>();
     Map<Integer, Member> members = new HashMap<>();
     Scanner sc = new Scanner(System.in);

     String booksFile = "books.txt";
     String membersFile = "members.txt";

    public static void main(String[] args) {
        new LibraryManagementSystem().run();
    }

    void run() {
        loadFromFile();
        mainMenu();
        saveToFile();
    }

    void mainMenu() {
        while (true) {
            System.out.println("\n--- City Library Digital Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: addBook(); break;
                case 2: addMember(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: searchBooks(); break;
                case 6: sortBooks(); break;
                case 7: saveToFile(); System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    int nextBookId() {
        return books.keySet().stream().mapToInt(i -> i).max().orElse(100) + 1;
    }

    int nextMemberId() {
        return members.keySet().stream().mapToInt(i -> i).max().orElse(200) + 1;
    }

    void addBook() {
        String t = readLine("Enter Title: ");
        String a = readLine("Enter Author: ");
        String c = readLine("Enter Category: ");

        int id = nextBookId();
        books.put(id, new Book(id, t, a, c));

        System.out.println("Book added. ID: " + id);
    }

    void addMember() {
        String n = readLine("Enter Member Name: ");
        int id = nextMemberId();
        members.put(id, new Member(id, n));
        System.out.println("Member added. ID: " + id);
    }

    void issueBook() {
        int bid = readInt("Enter Book ID: ");
        Book b = books.get(bid);

        if (b == null) { System.out.println("Book not found."); return; }
        if (b.issued) { System.out.println("Book is already issued."); return; }

        int mid = readInt("Enter Member ID: ");
        Member m = members.get(mid);

        if (m == null) { System.out.println("Member not found."); return; }

        b.markIssued();
        m.addBook(bid);

        System.out.println("Book issued.");
    }

    void returnBook() {
        int bid = readInt("Enter Book ID: ");
        Book b = books.get(bid);

        if (b == null) { System.out.println("Book not found."); return; }

        int mid = readInt("Enter Member ID: ");
        Member m = members.get(mid);

        if (m == null) { System.out.println("Member not found."); return; }

        if (!m.issuedBooks.contains(bid)) {
            System.out.println("This member did not issue this book.");
            return;
        }

        b.markReturned();
        m.returnBook(bid);

        System.out.println("Book returned.");
    }

    void searchBooks() {
        String q = readLine("Enter search keyword (title/author/category): ").toLowerCase();
        boolean found = false;

        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(q)
                    || b.author.toLowerCase().contains(q)
                    || b.category.toLowerCase().contains(q)) {
                printBook(b);
                found = true;
            }
        }

        if (!found)
            System.out.println("No books found.");
    }

    void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());

        System.out.println("Sort by: 1.Title  2.Author  3.Category");
        int c = readInt("Enter option: ");

        if (c == 1) Collections.sort(list);
        else if (c == 2) list.sort(Comparator.comparing(x -> x.author, String.CASE_INSENSITIVE_ORDER));
        else if (c == 3) list.sort(Comparator.comparing(x -> x.category, String.CASE_INSENSITIVE_ORDER));

        for (Book b : list) printBook(b);
    }

    void printBook(Book b) {
        System.out.println("ID: " + b.bookId
                + " | Title: " + b.title
                + " | Author: " + b.author
                + " | Category: " + b.category
                + " | Issued: " + b.issued);
    }

    String readLine(String p) {
        System.out.print(p);
        String s = sc.nextLine();
        return s.trim();
    }

    int readInt(String p) {
        while (true) {
            try {
                System.out.print(p);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    void loadFromFile() {
        try {
            File f = new File(booksFile);
            if (!f.exists()) f.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                Book b = Book.fromCSV(line);
                if (b != null) books.put(b.bookId, b);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading books.");
        }

        try {
            File f = new File(membersFile);
            if (!f.exists()) f.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                Member m = Member.fromCSV(line);
                if (m != null) members.put(m.memberId, m);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading members.");
        }
    }

    void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile));
            for (Book b : books.values()) bw.write(b.toCSV() + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving books.");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(membersFile));
            for (Member m : members.values()) bw.write(m.toCSV() + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving members.");
        }
    }
}
