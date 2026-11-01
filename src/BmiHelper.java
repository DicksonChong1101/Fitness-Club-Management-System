public class BmiHelper {

    private static double calculateBMI(double height, double weight) {
        // BMI = weight (kg) / height² (m²)
        double bmi = weight / (height * height);
        return Math.round(bmi * 10.0) / 10.0;
    }

    public static void run(){
        System.out.println("\n=============== BMI CALCULATOR ===============");
        System.out.println("(You can enter 0 at any time to cancel)");

        //from InputHelper (method)
        double height = InputHelper.readPositiveDouble("Enter your height in meters (e.g., 1.75): ");
        if (height == 0) {
            System.out.println("BMI calculation cancelled.");
            return;
        }

        double weight = InputHelper.readPositiveDouble("Enter your weight in kilograms (e.g., 70.5): ");
        if (weight == 0) {
            System.out.println("BMI calculation cancelled.");
            return;
        }

        double bmi = weight / (height * height);
        bmi = Math.round(bmi * 10.0) / 10.0;  // keep one decimal

        System.out.printf("\nYour BMI is: %.1f\n", bmi);

        // Interpret BMI with friendlier messages
        if (bmi < 18.5) {
            System.out.println("You are underweight. Consider a balanced diet to reach a healthy weight.");
        } else if (bmi < 25) {
            System.out.println("You are in the healthy weight range. Great job maintaining a balanced lifestyle!");
        } else if (bmi < 30) {
            System.out.println("You are overweight. Small changes in diet and exercise can make a big difference.");
        } else {
            System.out.println("You are in the obese range. We recommend consulting a health professional for guidance.");
        }

        System.out.println("BMI is a general indicator; it doesn't account for muscle mass or other factors.");
    }

}
