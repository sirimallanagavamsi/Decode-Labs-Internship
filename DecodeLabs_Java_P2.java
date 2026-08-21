/**
 * ------------------------------------------------------------------
 * DECODELABS INDUSTRIAL TRAINING KIT - PROJECT 2
 * Project Name : Student Grade Calculator
 * Description  : Takes subject-wise marks, calculates total marks,
 *                average percentage, and assigns grades based on logic ladder.
 * ------------------------------------------------------------------
 */

import java.util.Scanner;

public class DecodeLabs_Java_P2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numSubjects = 0;

        // Step 1: Input number of subjects with validation
        while (numSubjects <= 0) {
            System.out.print("Enter the total number of subjects: ");
            try {
                numSubjects = Integer.parseInt(scanner.nextLine().trim());
                if (numSubjects <= 0) {
                    System.out.println("Invalid input! Number of subjects must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }

        int totalMarks = 0;

        // Step 2: Loop to collect marks for each subject (0 to 100 range validation)
        for (int i = 1; i <= numSubjects; i++) {
            int mark = -1;
            while (true) {
                System.out.print("Enter marks for Subject " + i + " (0 - 100): ");
                try {
                    mark = Integer.parseInt(scanner.nextLine().trim());
                    if (mark < 0 || mark > 100) {
                        System.out.println("Invalid entry! Marks must be between 0 and 100. Try again.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid integer between 0 and 100.");
                }
            }
            totalMarks += mark;
        }

        // Step 3: Calculate average percentage with double precision
        double averagePercentage = (double) totalMarks / numSubjects;

        // Step 4: Grade assignment logic ladder
        char grade;
        if (averagePercentage >= 90) {
            grade = 'A';
        } else if (averagePercentage >= 80) {
            grade = 'B';
        } else if (averagePercentage >= 70) {
            grade = 'C';
        } else if (averagePercentage >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        // Step 5: Final output display
        System.out.println("\n========================================");
        System.out.println("    STUDENT GRADE CARD CALCULATOR       ");
        System.out.println("========================================");
        System.out.printf("Total Marks        : %d / %d\n", totalMarks, numSubjects * 100);
        System.out.printf("Average Percentage : %.2f%%\n", averagePercentage);
        System.out.println("Assigned Grade     : " + grade);
        System.out.println("========================================");

        scanner.close();
    }
} 
 
