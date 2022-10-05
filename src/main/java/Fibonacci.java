public class Fibonacci {

    public int getValueOfElement(int element) {

        if (element == 0 || element == 1) {
            return element;
        }
        // it will return the previous 2 elements and sum them
        return getValueOfElement(element - 1) + getValueOfElement(element - 2);
    }
}
