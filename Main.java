import java.util.Scanner;


public class Main {
    /**
     * An object of the Scanner class that allows you to accept values from the console.
     */
    private Scanner scanner = new Scanner(System.in);
    /**
     * An instance of the Main class that allows you to use its methods.
     */
    private static Main exemplar = new Main();

    /**
     * The main method in which all the functions of the program are called.
     * @param args Array of all elements used in the method.
     */
    public static void main(String[] args) {
        // calling the method that read calculator type
        CalculatorType type = exemplar.readCalculator();
        // checking that calculator type is correct
        if (type == CalculatorType.INCORRECT) {
            // calling the error method
            exemplar.reportFatalError("Wrong calculator type");
        }
        // calling the method that read commands number
        int n = exemplar.readCommandsNumber();
        final int int50 = 50;
        // checking that 1 <= n (Commands number) <= 50
        if (n < 1 || int50 < n) {
            // calling the error method
            exemplar.reportFatalError("Amount of commands is Not a Number");
        }
        // loop for reading commands and working with them
        for (int i = 0; i < n; i++) {
            // reading the operation
            String scOperation = exemplar.scanner.next();
            // determining a type of operation
            OperationType operation = exemplar.parseOperation(scOperation);
            // reading command's arguments
            String x = exemplar.scanner.next();
            String y = exemplar.scanner.next();
            switch (type) {
                // for working with integer type of calculator
                case INTEGER:
                    IntegerCalculator intCal = new IntegerCalculator();
                    switch (operation) {
                        // for working with addition type of operation
                        case ADDITION:
                            System.out.println(intCal.add(x, y));
                            break;
                        // for working with subtraction type of operation
                        case SUBTRACTION:
                            System.out.println(intCal.subtract(x, y));
                            break;
                        // for working with multiplication type of operation
                        case MULTIPLICATION:
                            System.out.println(intCal.multiply(x, y));
                            break;
                        // for working with division type of operation
                        case DIVISION:
                            System.out.println(intCal.divide(x, y));
                            break;
                        // for working with incorrect type of operation
                        default:
                            System.out.println("Wrong operation type");
                    }
                    break;
                // for working with double type of calculator
                case DOUBLE:
                    DoubleCalculator doCal = new DoubleCalculator();
                    switch (operation) {
                        // for working with addition type of operation
                        case ADDITION:
                            System.out.println(doCal.add(x, y));
                            break;
                        // for working with subtraction type of operation
                        case SUBTRACTION:
                            System.out.println(doCal.subtract(x, y));
                            break;
                        // for working with multiplication type of operation
                        case MULTIPLICATION:
                            System.out.println(doCal.multiply(x, y));
                            break;
                        // for working with division type of operation
                        case DIVISION:
                            System.out.println(doCal.divide(x, y));
                            break;
                        // for working with incorrect type of operation
                        default:
                            System.out.println("Wrong operation type");
                    }
                    break;
                // for working with string type of calculator
                default:
                    StringCalculator strCal = new StringCalculator();
                    switch (operation) {
                        // for working with addition type of operation
                        case ADDITION:
                            System.out.println(strCal.add(x, y));
                            break;
                        // for working with subtraction type of operation
                        case SUBTRACTION:
                            System.out.println(strCal.subtract(x, y));
                            break;
                        // for working with multiplication type of operation
                        case MULTIPLICATION:
                            System.out.println(strCal.multiply(x, y));
                            break;
                        // for working with division type of operation
                        case DIVISION:
                            System.out.println(strCal.divide(x, y));
                            break;
                        // for working with incorrect type of operation
                        default:
                            System.out.println("Wrong operation type");
                    }
                    break;
            }
        }
    }

    // reading and determining the calculator type
    private CalculatorType readCalculator() {
        String scCalculatorType = scanner.nextLine();
        switch (scCalculatorType) {
            case "INTEGER" :
                return CalculatorType.INTEGER;
            case "DOUBLE" :
                return CalculatorType.DOUBLE;
            case "STRING" :
                return CalculatorType.STRING;
            default:
                return CalculatorType.INCORRECT;
        }
    }

    // reading the commands number
    private int readCommandsNumber() {
        String scN = scanner.nextLine();
        // checking for integer value
        try {
            return Integer.parseInt(scN);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // printing the type of fatal error and finish the program
    private void reportFatalError(String r) {
        System.out.println(r);
        System.exit(0);
    }

    // determining the operation type
    private OperationType parseOperation(String operation) {
        switch (operation) {
            case "+":
                return OperationType.ADDITION;
            case "-":
                return OperationType.SUBTRACTION;
            case "*":
                return OperationType.MULTIPLICATION;
            case "/":
                return OperationType.DIVISION;
            default:
                return OperationType.INCORRECT;
        }
    }
}
// enumerator of calculator type
enum CalculatorType {
    /**
     * Integer type of calculator.
     */
    INTEGER,
    /**
     * Double type of calculator.
     */
    DOUBLE,
    /**
     * String type of calculator.
     */
    STRING,
    /**
     * Incorrect type of calculator.
     */
    INCORRECT
}
// enumerator of operation type
enum OperationType {
    /**
     * Addition type of operation.
     */
    ADDITION,
    /**
     * Subtraction type of operation.
     */
    SUBTRACTION,
    /**
     * Multiplication type of operation.
     */
    MULTIPLICATION,
    /**
     * Division type of operation.
     */
    DIVISION,
    /**
     * Incorrect type of operation.
     */
    INCORRECT
}
// abstract class keeping the methods which we will use
abstract class Calculator {
    public abstract String add(String a, String b);
    public abstract String subtract(String a, String b);
    public abstract String multiply(String a, String b);
    public abstract String divide(String a, String b);
}
// inheritance class for integer calculator
class IntegerCalculator extends Calculator {
    // the method for addition type of an operation
    public String add(String a, String b) {
        // checking for integer values
        try {
            int x = Integer.parseInt(a);
            int y = Integer.parseInt(b);
            return Integer.toString(x + y);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    // the method for subtraction type of an operation
    public String subtract(String a, String b) {
        // checking for integer values
        try {
            int x = Integer.parseInt(a);
            int y = Integer.parseInt(b);
            return Integer.toString(x - y);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    // the method for multiplication type of an operation
    public String multiply(String a, String b) {
        // checking for integer values
        try {
            int x = Integer.parseInt(a);
            int y = Integer.parseInt(b);
            return Integer.toString(x * y);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    // the method for division type of an operation
    public String divide(String a, String b) {
        // checking for integer values and dividing by zero
        try {
            int x = Integer.parseInt(a);
            int y = Integer.parseInt(b);
            if (y == 0) {
                return "Division by zero";
            }
            return Integer.toString(x / y);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
}
// inheritance class for double calculator
class DoubleCalculator extends Calculator {
    // the method for addition type of an operation
    public String add(String a, String b) {
        // checking for double values
        try {
            double x = Double.parseDouble(a);
            double y = Double.parseDouble(b);
            return Double.toString(x + y);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    // the method for subtraction type of an operation
    public String subtract(String a, String b) {
        // checking for double values
        try {
            double x = Double.parseDouble(a);
            double y = Double.parseDouble(b);
            return Double.toString(x - y);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    // the method for multiplication type of an operation
    public String multiply(String a, String b) {
        // checking for double values
        try {
            double x = Double.parseDouble(a);
            double y = Double.parseDouble(b);
            return Double.toString(x * y);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    // the method for division type of an operation
    public String divide(String a, String b) {
        // checking for double values and dividing by zero
        try {
            double x = Double.parseDouble(a);
            double y = Double.parseDouble(b);
            if (y == 0) {
                return "Division by zero";
            }
            return Double.toString(x / y);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
}
// inheritance class for string calculator
class StringCalculator extends Calculator {
    // the method for addition type of an operation
    public String add(String a, String b) {
        return a + b;
    }
    // the method for subtraction type of an operation
    public String subtract(String a, String b) {
        return "Unsupported operation for strings";
    }
    // the method for multiplication type of an operation
    public String multiply(String a, String b) {
        try {
            int y = Integer.parseInt(b);
            if (y <= 0) {
                throw new NumberFormatException();
            }
            String aMultiplied = "";
            for (int i = 0; i < y; i++) {
                aMultiplied += a;
            }
            return aMultiplied;
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }

    }
    // the method for division type of an operation
    public String divide(String a, String b) {
        return "Unsupported operation for strings";
    }
}
