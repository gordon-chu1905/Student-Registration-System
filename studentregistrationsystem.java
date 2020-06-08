import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

import oracle.jdbc.driver.*;
import oracle.sql.*;
import java.util.Scanner;
import java.util.Date;

// Group member: Kevin THOSATRIA 18087058D
//               Nicholas Matthew KURNIADI 18079267D

public class hw2
{
    public static void main(String args[]) throws SQLException, IOException, Exception
    {
        String username, password;
        username = "\"18079267d\"";	     // Your Oracle Account ID
        password = "upgbintk"; 	     // Your password of Oracle Account


        // Connection
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        OracleConnection conn =
                (OracleConnection)DriverManager.getConnection(
                        "jdbc:oracle:thin:@studora.comp.polyu.edu.hk:1521:dbms", username, password);


        //int program = 1;

//        Statement stmt;
//        ResultSet rset;

        while (true) {
            // take User Input ( 1 = Admin , 2 = Student)

            Scanner userinput = new Scanner(System.in);

            System.out.println("Enter 1 for Admin, 2 for Student, 0 to terminate: ");
            int user = userinput.nextInt();

            if (user == 1) {

                // nic part
                System.out.println("Welcome... System Administrator, please choose what do you want to do:");
                int select = 0;
        
                while(select != 7){

                    System.out.println("-----------------------------------------------\n" 
                                        + "1.) List all courses and student \n"
                                        + "2.) Department's student \n"
                                        + "3.) Add a new course/ student \n"
                                        + "4.) Modify course/ student \n" 
                                        + "5.) Delete course/ student \n"
                                        + "6.) Modify grade \n"
                                        + "7.) exit");           
                    
                    Scanner obj = new Scanner(System.in); 
                    select = obj.nextInt();
                

                    
                    switch(select){
                        case 1: 
                            Statement stmt = conn.createStatement();
                            ResultSet rset = stmt.executeQuery("SELECT COURSE_ID, STUDENT_ID FROM ENROLLMENT");
                            while (rset.next()){
                                System.out.println(rset.getString(1)
                                + " " + rset.getString(2));
                            }
                            
                            break;
                        
                        case 2: 
                            Scanner scan2 = new Scanner(System.in); 
                            Statement stmt_1 = conn.createStatement();
                            
                            System.out.println("Please input the department name");
                            String dept = scan2.nextLine();
                            System.out.println(dept);
                            ResultSet rset2 = stmt_1.executeQuery("SELECT STUDENT_ID FROM STUDENTS WHERE DEPARTMENT = '" + dept + "'");
                            while(rset2.next()){
                                System.out.println(rset2.getString(1));
                            }
                            
                            break;

                        case 3: 
                            System.out.println("Which data do you want to add: \n"
                                                + "1.) Student\n"
                                                + "2.) Courses");
                            
                            Scanner menu3 = new Scanner(System.in);
                            int menu = menu3.nextInt();
                            // 1 for student,  2 for course
                            String id, name, deptn, addr, birth, gender;
                            if(menu == 1){
                                menu3.nextLine();
                                System.out.println("-- Input the student ID --");
                                id = menu3.nextLine();
                                System.out.println("-- Input the student name --");
                                name = menu3.nextLine();
                                System.out.println("-- Input the department --");
                                deptn = menu3.nextLine();
                                System.out.println("-- Input the address --");
                                addr = menu3.nextLine();
                                System.out.println("-- Input the birthdate (YYYY/MM/DD) --");
                                birth = menu3.nextLine();
                                System.out.println("-- Input the gender --");
                                gender = menu3.nextLine();

                                String sql = "INSERT INTO STUDENTS " + "(STUDENT_ID, STUDENT_NAME, DEPARTMENT, ADDRESS, BIRTHDATE, GENDER) " 
                                            + " VALUES(?, ?, ?, ?, ?, ?)";
                                java.util.Date date = new SimpleDateFormat("yyyy/MM/dd").parse(birth);
                                long ms = date.getTime();
                                java.sql.Date sqldate = new java.sql.Date(ms); 
                                PreparedStatement mystmt = conn.prepareStatement(sql);
                                mystmt.setString(1, id);
                                mystmt.setString(2, name);
                                mystmt.setString(3, deptn);
                                mystmt.setString(4, addr);
                                mystmt.setDate(5, sqldate);
                                mystmt.setString(6, gender);
                                
                                mystmt.executeUpdate();

                                System.out.println("You have entered the value below to the database");
                                
                                Statement stmt1 = conn.createStatement();
                                ResultSet conf = stmt1.executeQuery("SELECT * FROM STUDENTS WHERE STUDENT_ID = '" + id + "'");
                                while(conf.next()){
                                    System.out.println(conf.getString(1)
                                        + " " + conf.getString(2)
                                        + " " + conf.getString(3)
                                        + " " + conf.getString(4)
                                        + " " + conf.getString(5)
                                        + " " + conf.getString(6)
                                        );
                                }
                            }
                            else if(menu == 2){
                                menu3.nextLine();
                                System.out.println("-- Input the course id -- ");
                                id = menu3.nextLine();
                                System.out.println("-- Input the course title --");
                                deptn = menu3.nextLine();
                                System.out.println("-- Input the staff name --");
                                name = menu3.nextLine();
                                System.out.println("-- Input which section -- ");
                                addr = menu3.nextLine();

                                String sql1 = "INSERT INTO COURSES" + "(COURSE_ID, COURSE_TITLE, STAFF_NAME, SECTION)" 
                                            + "VALUES(?, ?, ?, ?)";

                                PreparedStatement mystmt1 = conn.prepareStatement(sql1);
                                mystmt1.setString(1, id);
                                mystmt1.setString(2, deptn);
                                mystmt1.setString(3, name);
                                mystmt1.setString(4, addr);

                                mystmt1.executeUpdate();

                                System.out.println("You have entered the value below to the database");
                                
                                Statement stmt1 = conn.createStatement();
                                ResultSet conf = stmt1.executeQuery("SELECT * FROM COURSES WHERE COURSE_ID = '" + id + "'");
                                while(conf.next()){
                                    System.out.println(conf.getString(1)
                                        + " " + conf.getString(2)
                                        + " " + conf.getString(3)
                                        + " " + conf.getString(4)
                                    );
                                }
                            }
                            
                            else{
                                System.out.println("-- You have entered the wrong menu --");
                                break;
                            }

                            System.out.println("== Database has been updated ==");
                            break;
                        
                        case 4: 
                            System.out.println("-- Which data do you want to modify -- \n"
                                            +   "1.) Student \n"
                                            +   "2.) Courses ");

                            Scanner menu4 = new Scanner(System.in);
                            int sel4 = menu4.nextInt();
                            String orid, id4, name4, deptn4, addr4, birth4, gender4;
                            if(sel4 == 1){
                        
                                menu4.nextLine();
                                System.out.println("-- Input the student id you want to update --");
                                orid = menu4.nextLine();
                                // I assume it is not possible to change id since it is a primary key, and changing it 
                                // may lead to some integrity constraints
                                System.out.println("-- Input the student name --");
                                name4 = menu4.nextLine();
                                System.out.println("-- Input the department --");
                                deptn4 = menu4.nextLine();
                                System.out.println("-- Input the address --");
                                addr4 = menu4.nextLine();
                                System.out.println("-- Input the birthdate (YYYY/MM/DD) --");
                                birth4 = menu4.nextLine();
                                System.out.println("-- Input the gender --");
                                gender4 = menu4.nextLine();
                                
                                String sql4S = "UPDATE STUDENTS SET STUDENT_NAME = ?, DEPARTMENT = ?, ADDRESS = ?"
                                + ",BIRTHDATE = ?, GENDER = ? WHERE STUDENT_ID = ?";
                                PreparedStatement updateS = conn.prepareStatement(sql4S);
                                java.util.Date date4 = new SimpleDateFormat("yyyy/MM/dd").parse(birth4);
                                long ms4 = date4.getTime();
                                java.sql.Date sqldate4 = new java.sql.Date(ms4); 

                                updateS.setString(1, name4);
                                updateS.setString(2, deptn4);
                                updateS.setString(3, addr4);
                                updateS.setDate(4, sqldate4);
                                updateS.setString(5, gender4);
                                updateS.setString(6, orid);

                                updateS.executeUpdate();

                                Statement stmt4 = conn.createStatement();
                                ResultSet conf4 = stmt4.executeQuery("SELECT * FROM STUDENTS WHERE STUDENT_ID = '" + orid + "'");
                                while(conf4.next()){
                                    System.out.println(conf4.getString(1)
                                        + " " + conf4.getString(2)
                                        + " " + conf4.getString(3)
                                        + " " + conf4.getString(4)
                                        + " " + conf4.getString(5)
                                        + " " + conf4.getString(6)
                                        );
                                }
                                
                            }
                            else if(sel4 == 2){                        
                                menu4.nextLine();
                                System.out.println("-- Input the course id you want to update -- ");
                                orid = menu4.nextLine();
                                System.out.println("-- Input the course title --");
                                deptn4 = menu4.nextLine();
                                System.out.println("-- Input the staff name --");
                                name4 = menu4.nextLine();
                                System.out.println("-- Input which section -- ");
                                addr4 = menu4.nextLine();

                                String sql4D = "UPDATE COURSES SET COURSE_TITLE = ?, STAFF_NAME = ?, SECTION = ? WHERE COURSE_ID = ?";
                                PreparedStatement updateD = conn.prepareStatement(sql4D);
                                
                                updateD.setString(1, deptn4);
                                updateD.setString(2, name4);
                                updateD.setString(3, addr4);
                                updateD.setString(4, orid);

                                updateD.executeUpdate();

                                Statement stmt1 = conn.createStatement();
                                ResultSet conf = stmt1.executeQuery("SELECT * FROM COURSES WHERE COURSE_ID = '" + orid + "'");
                                while(conf.next()){
                                    System.out.println(conf.getString(1)
                                        + " " + conf.getString(2)
                                        + " " + conf.getString(3)
                                        + " " + conf.getString(4)
                                    );
                                }


                            }
                            else{
                                System.out.println("You have entered the wrong menu");
                                break;
                            }
                            System.out.println("== Database has been updated ==");
                            break;
                        case 5: 
                            System.out.println("-- Which data you want to delete --\n"
                                            +   "1.) Student\n"
                                            +   "2.) Courses");
                            Scanner menu5 = new Scanner(System.in);
                            int select5 = menu5.nextInt();

                            if(select5 == 1){
                                System.out.println("-- Student ID to delete --");
                                menu5.nextLine();
                                String sid5 = menu5.nextLine();
                                String sqls5 = "DELETE FROM STUDENTS WHERE STUDENT_ID = ?"; 
                                String sqlse5 = "DELETE FROM ENROLLMENT WHERE STUDENT_ID = ?";

                                PreparedStatement ps5 = conn.prepareStatement(sqls5);
                                ps5.setString(1, sid5);
                                ps5.executeUpdate();

                                ps5 = conn.prepareStatement(sqlse5);
                                ps5.setString(1, sid5);
                                ps5.executeUpdate();
                                
                                System.out.println("Student id " + sid5 + " and its corresponding tables has been deleted" );
                            }
                            else if(select5 == 2){
                                System.out.println("-- Course ID to delete -- ");
                                menu5.nextLine();

                                String cid5 = menu5.nextLine();
                                String sqlc5 = "DELETE FROM COURSES WHERE COURSE_ID = ?";
                                String sqlce5 = "DELETE FROM ENROLLMENT WHERE COURSE_ID = ?";

                                PreparedStatement ps5_1 = conn.prepareStatement(sqlc5);
                                ps5_1.setString(1, cid5);
                                ps5_1.executeUpdate();

                                ps5_1 = conn.prepareStatement(sqlce5);
                                ps5_1.setString(1, cid5);
                                ps5_1.executeUpdate();
                                System.out.println("Course id " + cid5 + " and its corresponding tables has been deleted");
                            }
                            break;
                        case 6: 
                            Scanner menu6 = new Scanner(System.in);
                            System.out.println("-- Student id --");
                            String sid6 = menu6.nextLine();
                            System.out.println("-- Course id --");
                            String cid6 =  menu6.nextLine();
                            System.out.println("-- New Grade --");
                            int grade = menu6.nextInt();
                            
                            String s6 = "UPDATE ENROLLMENT SET GRADE = ? WHERE STUDENT_ID = ? AND COURSE_ID = ?";
                            PreparedStatement ps6 = conn.prepareStatement(s6);
                            ps6.setInt(1, grade);
                            ps6.setString(2, sid6);
                            ps6.setString(3, cid6);

                            ps6.executeUpdate();
                            System.out.println("You have updated below");

                            String s6n = "SELECT * FROM ENROLLMENT WHERE STUDENT_ID = '" + sid6 + "' AND COURSE_ID = '" + cid6 + "'";
                            Statement stmt6 = conn.createStatement();
                            ResultSet res6 = stmt6.executeQuery(s6n);
                            while(res6.next()){
                                System.out.println(res6.getString(1)
                                                + " " + res6.getString(2)
                                                + " " + res6.getDate(3)
                                                + " " + res6.getInt(4) 
                                                );
                            }
                            break;

                        default: 
                            System.out.println("You have chosen a wrong menu, Redirecting..");
                    }
                    
                }

                    }


            else if (user == 2) {

                int studentmenu = 0;

                while (studentmenu != 99) {
                    System.out.println("STUDENT MENU");
                    System.out.println("99 = Exit Student Menu.");
                    System.out.println("1 = List all courses in the system.");
                    System.out.println("2 = List all the courses that you have registered.");
                    System.out.println("3 = Register a course in the system");
                    System.out.println("4 = Modify your personal information");
                    System.out.println("Input: ");

                    Scanner studentmenuinput = new Scanner(System.in);

                    studentmenu = studentmenuinput.nextInt();

                    switch (studentmenu) {

                        case 1:
                            Statement student_stmt1 = conn.createStatement();
                            ResultSet student_rset1 = student_stmt1.executeQuery("SELECT COURSE_ID FROM COURSES");
                            while (student_rset1.next()) {
                                    System.out.println(student_rset1.getString(1));
                            }

                            break;

                        case 2:
                            System.out.println("Student Menu 2 chosen. Input your Student ID: ");
                            Scanner studentmenu2input = new Scanner(System.in);
                            String inputstudentid = studentmenu2input.nextLine();


                            Statement student_stmt2 = conn.createStatement();
                            ResultSet student_rset2 = student_stmt2.executeQuery("SELECT ENROLLMENT.COURSE_ID FROM ENROLLMENT WHERE ENROLLMENT.STUDENT_ID = '" + inputstudentid + "'");

                            while (student_rset2.next()) {
                                System.out.println(student_rset2.getString(1));
                            }

                            break;

                        case 3:
                        Scanner studentmenu3input = new Scanner(System.in);
                        String studentid3, courseid3, regdate3a;
                        int grade3;

                        System.out.println("Student Menu 3 chosen. Input Student ID, Course ID, Registration Date[yyyy/mm/dd] and Grade (Separate each input with new line): ");

                        studentid3 = studentmenu3input.nextLine();
                        courseid3 = studentmenu3input.nextLine();
                        regdate3a = studentmenu3input.nextLine();
                        grade3 = studentmenu3input.nextInt();


                        // change regdate3a to java.sql.Date object as regdate3sql
                        java.util.Date regdate3b = new SimpleDateFormat("yyyy/MM/dd").parse(regdate3a);
                        long regdate3c = regdate3b.getTime();
                        java.sql.Date regdate3sql = new java.sql.Date(regdate3c);


                        PreparedStatement student_stmt3 = conn.prepareStatement("INSERT INTO ENROLLMENT VALUES(?, ?, ?, ?)");
                        student_stmt3.setString(1, studentid3);
                        student_stmt3.setString(2, courseid3);
                        student_stmt3.setDate(3, regdate3sql);
                        student_stmt3.setInt(4, grade3);
                        student_stmt3.executeUpdate();

                        System.out.println("Done, here is the result");
                        Statement stmt_stud3 = conn.createStatement();
                        ResultSet rset_stud3 = stmt_stud3.executeQuery("SELECT * FROM ENROLLMENT WHERE STUDENT_ID = '" + studentid3 + "'");
                        while(rset_stud3.next()){
                            System.out.println(rset_stud3.getString(1)
                                            + " " + rset_stud3.getString(2)
                                            + " " + rset_stud3.getString(3)
                                            + " " + rset_stud3.getString(4));      
                        }
                        break;



                        case 4:
                                // I assume a student can modify all of his/her personal information, except for STUDENT_ID as it may lead to some integrity constraints.
                                Scanner studentmenu4input = new Scanner(System.in);
                                String studentid4, studentname4, dept4, address4, birthdate4a, sex4;
    
                                System.out.println("Student Menu 4 chosen. Input your Student ID to modify personal information: ");
                                studentid4 = studentmenu4input.nextLine();
    
                                System.out.println("Input updated Name, Department, Address, BirthDate[YYYY/MM/DD] and Gender: ");
                                studentname4 = studentmenu4input.nextLine();
                                dept4 = studentmenu4input.nextLine();
                                address4 = studentmenu4input.nextLine();
                                birthdate4a = studentmenu4input.nextLine();
                                sex4 = studentmenu4input.nextLine();
    
                                // convert birthdate4a to java.sql.Date object as birthdate4sql
                                java.util.Date birthdate4javaobj = new SimpleDateFormat("yyyy/MM/dd").parse(birthdate4a);
                                long birthdate4gettime = birthdate4javaobj.getTime();
                                java.sql.Date birthdate4sql = new java.sql.Date(birthdate4gettime);
    
    
                                PreparedStatement student_stmt4 = conn.prepareStatement("UPDATE STUDENTS SET STUDENT_NAME = ?, DEPARTMENT = ?, ADDRESS = ?, BIRTHDATE = ?, GENDER = ? WHERE STUDENT_ID = ?");
    
                                student_stmt4.setString(1, studentname4);
                                student_stmt4.setString(2, dept4);
                                student_stmt4.setString(3, address4);
                                student_stmt4.setDate(4, birthdate4sql);
                                student_stmt4.setString(5, sex4);
                                student_stmt4.setString(6, studentid4);
    
                                student_stmt4.executeUpdate();
                                System.out.println("Done");
    
                                System.out.println("DEBUGGING/CHECKING PURPOSES: ");
                                // select statement to check changes
    
    
                                Statement student_stmt4_debug = conn.createStatement();
                                ResultSet student_rset4_debug = student_stmt4_debug.executeQuery("SELECT * FROM STUDENTS WHERE STUDENT_ID = '" + studentid4 + "'");
    
                                while(student_rset4_debug.next()) {
                                    System.out.println(student_rset4_debug.getString(1)
                                            + " " + student_rset4_debug.getString(2)
                                            + " " + student_rset4_debug.getString(3)
                                            + " " + student_rset4_debug.getString(4)
                                            + " " + student_rset4_debug.getString(5)
                                            + " " + student_rset4_debug.getString(6));
                                }
    
    
                                break;
                    }



                }
            }




            else if (user == 0){
                break; // breaks from while(true) loop

            }

        }





        conn.close();
    }
}

