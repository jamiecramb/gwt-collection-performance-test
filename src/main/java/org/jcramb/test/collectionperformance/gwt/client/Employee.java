package org.jcramb.test.collectionperformance.gwt.client;

public class Employee {

    private final String firstName;

    private final String lastName;

    private final String employeeId;

    public Employee(String id) {
        firstName = "Employee FName " + id;
        lastName = "Employee LName " + id;
        employeeId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public int hashCode() {

        // Breakpoint in here to see the Set<E> using the equals method
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        // Breakpoint in here to see the List<E> using the equals method
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Employee other = (Employee) obj;
        if (employeeId == null) {
            if (other.employeeId != null) {
                return false;
            }
        } else if (!employeeId.equals(other.employeeId)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        return true;
    }
}