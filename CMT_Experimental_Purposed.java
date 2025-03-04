
import java.util.Scanner;
/**
 *
 * @author Grazielle
 */
public class CMT_Experimental_Purposed {

     public static void main(String[] args) { 
       
       //I used Red's way but added the string "<<Invalid input. Please follow HH:MM format.>>" after timepattern because otherwise it doesn't work--netbeans says the length doesn't match.
        try (Scanner scanner = new Scanner(System.in)) {
            String timePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
            
            String timeInInput, timeOutInput;
            while (true) {
                try {
                    timeInInput = getValidatedInput(scanner, "Enter Time-In (HH:MM): ", timePattern, "<<Invalid input. Please follow HH:MM format.>>"); // this one!
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            while (true) {
                try {
                    timeOutInput = getValidatedInput(scanner, "Enter Time-Out (HH:MM): ", timePattern, "<<Invalid input. Please follow HH:MM format.>>");
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            
            // I also used red's way here! personally speaking, sure it looks easy on the eyes but we will have issues with past-midnight time-outs.
            int timeInHours = Integer.parseInt(timeInInput.split(":")[0]);
            int timeInMinutes = Integer.parseInt(timeInInput.split(":")[1]);
            int timeOutHours = Integer.parseInt(timeOutInput.split(":")[0]);
            int timeOutMinutes = Integer.parseInt(timeOutInput.split(":")[1]);
            
            // Trial for restrict overtime code. No OT will be counted if time-in is after 8AM.
            int requiredTimeInHours = 8;
            int requiredTimeOutHours = 17; // 5PM
            
            double totalHoursWorked;
            double timeInTotal = timeInHours + (timeInMinutes / 60.0);
            double timeOutTotal = timeOutHours + (timeOutMinutes / 60.0);
         
           // Check if time-out is on the next day
           if (timeOutHours < timeInHours || (timeOutHours == timeInHours && timeOutMinutes < timeInMinutes)) {
               timeOutTotal += 24; // Adding 24 hours to correctly calculate the total work time
           }
            
            if (timeInHours > requiredTimeInHours || (timeInHours == requiredTimeInHours && timeInMinutes > 0)) {
                System.out.println("<<Overtime hours will not be counted due to late time-in.>>");
                timeOutTotal = Math.min(timeOutTotal, requiredTimeOutHours); // Restricting time-out to 5PM
            }
            
            double lunchBreakDuration = getValidatedDouble(scanner, "Enter Lunch Break Duration (hours): ");
            totalHoursWorked = (timeOutTotal - timeInTotal) - lunchBreakDuration; // Subtracting the lunch break duration from total hours worked.

            double hourlyRate = getValidatedDouble(scanner, "Enter Hourly Rate (no commas): ");
            double basicSalary = getValidatedDouble(scanner, "Enter Basic Salary (no commas): ");
            double sssContribution = getValidatedDouble(scanner, "Enter SSS Contribution (no commas): ");
            double philHealthPercentage = getValidatedDouble(scanner, "Enter PhilHealth Percentage (e.g., 0.05 for 5%): ");

            double hourlyRateSalary = (totalHoursWorked * hourlyRate) - hourlyRate; // "- hourlyRate" is for the lunch break included in the totalHoursWorked.

            double hourlyRateSalary = totalHoursWorked * hourlyRate; // Calculating salary based on total hours worked.
            double PhilHealthDaily = PhilHealth / 20;
            double pagIbigContribution = getValidatedDouble(scanner, "Enter Pag-Ibig Contribution (no commas): ");
            double PagIbig = pagIbigContribution;
            double SSSDaily = sssContribution / 20;
            double PagIbigDaily = PagIbig / 20;
            double MonthlyGovernmentDeductions = PhilHealth + sssContribution + PagIbig;
            double DailyGovernmentDeductions = PhilHealthDaily + SSSDaily + PagIbigDaily;
            
            // as for the outputs here well...preference i suppose. i just like my layout and seeing the numbers presented this way.
            String createnewline = System.getProperty("line.separator");
            
            System.out.println(createnewline + "[Daily Payslip]");
            System.out.printf("Total Hours Worked: %.2f hours\n", totalHoursWorked);
            System.out.printf("Salary Based on Hours Worked: %.2f Pesos\n", hourlyRateSalary);
            System.out.println(createnewline + "Government Contributions:");
            System.out.println("PhilHealth:");
            System.out.printf("Monthly: %.2f Pesos\n", PhilHealth);
            System.out.printf("Daily: %.2f Pesos\n", PhilHealthDaily);
            System.out.println(createnewline + "SSS:");
            System.out.printf("Monthly: %.2f Pesos\n", sssContribution);
            System.out.printf("Daily: %.2f Pesos\n", SSSDaily);
            System.out.println(createnewline + "Pag-Ibig:");
            System.out.printf("Monthly: %.2f Pesos\n", PagIbig);
            System.out.printf("Daily: %.2f Pesos\n", PagIbigDaily);
            System.out.println(createnewline + createnewline + "Total Government Deductions:");
            System.out.printf("Monthly: %.2f Pesos\n", MonthlyGovernmentDeductions);
            System.out.printf("Daily: %.2f Pesos\n", DailyGovernmentDeductions);
            System.out.printf(createnewline + "Daily Net Salary: %.2f Pesos\n", (hourlyRateSalary - DailyGovernmentDeductions));
        }      
    }
    
    // for this validating part i retained your way
    private static String getValidatedInput(Scanner Answer, String prompt, String pattern, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = Answer.nextLine();
            if (input.matches(pattern)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    private static double getValidatedDouble(Scanner Answer, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = Answer.nextLine();
            if (input.matches("\\d+(\\.\\d+)?")) {
                return Double.parseDouble(input);
            }
            System.out.println("<<Invalid input. Please enter a valid number without commas.>>");
        }   
    }
}