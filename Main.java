import java.util.*;
import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SecureRandom random = new SecureRandom();
        String again="y";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:,.<>?";
        boolean hasupper = false, haslower = false, hasdigit = false, hasspecial = false;
        int score = 0;
        StringBuilder allChars;
        
        List<Character> passwordChars;
        int length;
        do	
        {  allChars = new StringBuilder();
          passwordChars = new ArrayList<>();
          hasupper = false;
          haslower = false;
          hasdigit = false;
          hasspecial = false;
          score = 0;

        System.out.print("Enter password length: ");
        length = sc.nextInt();
        sc.nextLine();

        if (length <= 0) {
            System.out.println("Password length must be greater than 0.");
            return;
        }

        int chosenCategories = 0;

        System.out.print("Include uppercase letters (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            allChars.append(upper);
            passwordChars.add(upper.charAt(random.nextInt(upper.length())));
            chosenCategories++;
        }

        System.out.print("Include lowercase letters (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            allChars.append(lower);
            passwordChars.add(lower.charAt(random.nextInt(lower.length())));
            chosenCategories++;
        }

        System.out.print("Include numbers (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            allChars.append(numbers);
            passwordChars.add(numbers.charAt(random.nextInt(numbers.length())));
            chosenCategories++;
        }

        System.out.print("Include special characters? (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            allChars.append(special);
            passwordChars.add(special.charAt(random.nextInt(special.length())));
            chosenCategories++;
        }

        if (allChars.length() == 0) {
            System.out.println("No character types selected. Cannot generate password.");
            continue;
        }

        // Auto-correct length if too short
        if (length < chosenCategories) {
            System.out.println("Length too short. Automatically setting length to " + chosenCategories);
            length = chosenCategories;
        }
        
        // Fill remaining characters randomly
        while (passwordChars.size() < length) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle so mandatory chars aren’t at start
        Collections.shuffle(passwordChars, random);
        
        
        if(length>=6)
        {
        	score++;
        }
        if(length>=9)
        {
        	score++;
        }
        for(char c: passwordChars)
        {
        	if(Character.isUpperCase(c)) hasupper=true;
        	else if(Character.isLowerCase(c)) haslower=true;
        	else if(Character.isDigit(c)) hasdigit=true;
        	else
        		hasspecial=true;
        }
        if(hasupper)
        {
        	score++;
        }
        if(haslower)
        {
        	score++;
        }
        if(hasdigit) score++;
        if(hasspecial) score++;
        
        
        // Build final password
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
        	
            password.append(c);
        }

        System.out.println("Generated Password: " + password);
        System.out.println("score:"+score); 
        
        //Strength generator
        if(score<=3)
        { System.out.println("weak");}
        if(score>3 && score<6)
        { System.out.println("medium");}	
        else if(score>6)
        { System.out.println("strong");}
        
        System.out.print("Do you want to generate another password (y/n)?");
        again=sc.nextLine();
        
        } while(again.equalsIgnoreCase("y"));
        System.out.print("Exiting!");
        }
}
