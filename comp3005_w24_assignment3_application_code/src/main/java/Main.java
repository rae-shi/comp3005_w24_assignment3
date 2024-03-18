import java.sql.*;

public class Main {
    private static String url = "jdbc:postgresql://localhost:5432/comp_3005_a3";
    private static String user = "postgres";
    private static String password = "postgres";
    private static Connection connection = null;

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("You have connected to database successfully");
                System.out.println();
//                System.out.println("Output after calling getAllStudents() function:");
//                getAllStudents();
//                addStudent("Rae","Shi","raeshi@cmail.carleton.com", Date.valueOf("2024-03-18"));
//                System.out.println("Students Records Output after calling addStudent() function:");
//                getAllStudents();
//                updateStudentEmail(1,"raeshi@example.com");
//                System.out.println("Students Records Output after calling updateStudentEmail() function:");
//                getAllStudents();
                deleteStudent(2);
                System.out.println("Students Records Output after calling deleteStudent() function:");
                getAllStudents();
            } else {
                System.out.println("You cannot connect to database successfully");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getAllStudents() {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM students");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                String studentRecord = resultSet.getString("student_id") + "\t" +
                        resultSet.getString("first_name") + "\t" +
                        resultSet.getString("last_name") + "\t" +
                        resultSet.getString("email") + "\t" + resultSet.getString("enrollment_date");
                System.out.println(studentRecord);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, enrollment_date);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void updateStudentEmail(Integer student_id, String new_email){
        String sql = "UPDATE students SET email = ? WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,new_email);
            preparedStatement.setInt(2,student_id);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteStudent(Integer student_id){
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, student_id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

