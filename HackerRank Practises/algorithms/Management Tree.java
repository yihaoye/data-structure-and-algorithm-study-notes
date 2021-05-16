import java.util.*;
import java.util.stream.*;

/**
 * The main class for the employment management tree exercise
 */
public class ManagementTree {

    /**
    * The main method that reads input from STDIN.
    * DO NOT MODIFY THIS METHOD.
    */
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line != null) {
                String[] input = line.split("\\s+");
                if (input.length == 3) {
                    int id = Integer.parseInt(input[0]);
                    String name = input[1];
                    int managerId = Integer.parseInt(input[2]);

                    Employee employee = new Employee(id, name, managerId);
                    employees.add(employee);
                }
            }
        }
        displayManagementTree(employees);
    }
    
    /**
    * Displays employees in a logical tree.
    */
    public static void displayManagementTree(List<Employee> employees) {
        Employee root = null;
        int rootIndex = 0;
        for (int i=0; i<employees.size(); i++) {
            if (employees.get(i).managerId == 0) {
                if (root == null) {
                    root = employees.get(i);
                    rootIndex = i;
                } else {
                    System.out.println("more than one root managers found");
                    return;
                }
            }
        }
        if (root == null) {
            System.out.println("no root manager found");
            return;
        }
        employees.remove(rootIndex);
        dfsBuildTree(root, employees);
        dfsPrintTree(root, 0);
    }
    
    public static void dfsBuildTree(Employee employee, List<Employee> employees) {
        if (employees.size() == 0) return;
        List<Integer> removeIndexs = new ArrayList<>();
        for (int i=0; i<employees.size(); i++) {
            if (employee.id == employees.get(i).managerId) {
                employee.children.add(employees.get(i));
                removeIndexs.add(i);
            }
        }
        Collections.reverse(removeIndexs);
        for (int index : removeIndexs) {
            employees.remove(index);
        }
        for (Employee child : employee.children) {
            dfsBuildTree(child, employees);
        }
    }
    
    public static void dfsPrintTree(Employee employee, int level) {
        System.out.println(String.join("", Collections.nCopies(level+1, "->")) + employee.name);
        Collections.sort(employee.children);
        for (Employee child : employee.children) {
            dfsPrintTree(child, level+1);
        }
    }

    /**
    * Employee class
    */
    static class Employee implements Comparable<Employee> {
        public int id;
        public String name;
        public int managerId;
        public Employee parent;
        public List<Employee> children;

        public Employee(int id, String name, int managerId) {
            this.id = id;
            this.name = name;
            this.managerId = managerId;
            this.parent = null;
            this.children = new ArrayList<>();
        }
        
        @Override
        public int compareTo(Employee o) {
            return this.name.compareTo(o.name);
        }
    }
}
