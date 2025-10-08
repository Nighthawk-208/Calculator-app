package Java.Assignments;
import java.util.Scanner;

class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a+b;
    }

    int add(int a, int b, int c) {
        return a+b+c;
    }

    int minus(int a, int b) {
        return a - b;
    }

    double multi(double a, double b) {
        return a*b;
    }

    int divide(int a, int b) {
        if(b==0){
            throw new ArithmeticException("Error: Division by zero is not allowed.");
        }
        return a/b;
    }
}

class UserUI{

    Calculator calculator;
    Scanner scanner;

    UserUI(){
        calculator=new Calculator();
        scanner=new Scanner(System.in);
    }
     void mainMenu(){
         System.out.println("Welcome to Calculator");
         System.out.println("1. Addition");
         System.out.println("2. Subtraction");
         System.out.println("3. Multiplication");
         System.out.println("4. Division");
         System.out.println("5. Exit");
         System.out.println("Enter your choice");
         int choice=scanner.nextInt();
         switch(choice){
             case 1:
                 performAddition();
                 break;
             case 2:
                 performSubtraction();
                 break;
             case 3:
                 performMultiplication();
                 break;
             case 4:
                 performDivision();
                 break;
             case 5:
                 System.out.println("Exiting the program");
                 System.exit(0);
             default:
                 System.out.println("Invalid choice.enter num btw 1-5");
         }
     }
     void performAddition(){
         System.out.println("Addition options:");
         System.out.println("1) Two integers:");
         System.out.println("2) Two Doubles:");
         System.out.println("3) Three integers:");
         System.out.println("Enter your choice");
         int choice=scanner.nextInt();

         switch(choice){
             case 1:
                 System.out.println("Enter the first integer:");
                 int twoa=scanner.nextInt();
                 System.out.println("Enter the second integer:");
                 int twob=scanner.nextInt();
                 int  twosum=calculator.add(twoa,twob);
                 System.out.println("Result: "+twosum);
                 break;
             case 2:
                 System.out.println("Enter the first double:");
                 double doublea=scanner.nextInt();
                 System.out.println("Enter the second double:");
                 double doubleb=scanner.nextInt();
                 double doublesum=calculator.add(doublea,doubleb);
                 System.out.println("Result: "+doublesum);
                 break;
             case 3:
                 System.out.println("Enter the first Integer:");
                 int threea=scanner.nextInt();
                 System.out.println("Enter the second Integer:");
                 int threeb=scanner.nextInt();
                 System.out.println("Enter the third Integer:");
                 int threec=scanner.nextInt();
                 int threesum=calculator.add(threea,threeb,threec);
                 System.out.println("Result: "+threesum);
                 break;
         }
     }
     void performSubtraction(){
        System.out.println("Enter the first integer:");
        int suba=scanner.nextInt();
        System.out.println("Enter the second integer:");
        int subb=scanner.nextInt();
        int subresult=calculator.minus(suba,subb);
         System.out.println("Result: "+subresult);
     }
     void performMultiplication(){
        System.out.println("Enter the first Double:");
        double multia=scanner.nextInt();
        System.out.println("Enter the second double:");
        double multib=scanner.nextInt();
        double multiresult=calculator.multi(multia,multib);
        System.out.println("Result: "+multiresult);
     }
     void performDivision(){
        System.out.println("Enter the first Integer:");
        int diva=scanner.nextInt();
        System.out.println("Enter the second Integer:");
        int divb=scanner.nextInt();
        int divresult=calculator.divide(diva,divb);
        System.out.println("Result: "+divresult);
     }

    public static void main(String[] args) {
        UserUI obj=new UserUI();
        obj.mainMenu();
    }
}
