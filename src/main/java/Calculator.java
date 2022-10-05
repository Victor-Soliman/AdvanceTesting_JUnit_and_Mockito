public class Calculator {

    public double add(double firstNumber, double secondNumber) {
        return firstNumber + secondNumber;
    }

    public double reverseNumber(double number) {
        return number * -1;
    }

    public boolean isOdd(int number) {
        return number % 2 == 1;
    }

    public double divide(int firstNumber, int secondNumber) {
        if (secondNumber == 0) {
            throw new IllegalArgumentException("Cannot divide by 0");
        }
        return (double) firstNumber / secondNumber;
    }
}
