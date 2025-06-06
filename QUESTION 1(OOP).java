import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

// Car.java
class Car {
    private String id;
    private String model;
    private boolean isAvailable;

    public Car(String id, String model) {
        this.id = id;
        this.model = model;
        this.isAvailable = true;
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public boolean isAvailable() { return isAvailable; }

    public void rent() {
        if (!isAvailable) {
            throw new IllegalStateException("Car is already rented.");
        }
        isAvailable = false;
    }

    public void returnCar() {
        if (isAvailable) {
            throw new IllegalStateException("Car is already available.");
        }
        isAvailable = true;
    }
}

// Customer.java
class Customer {
    private String id;
    private String name;
    private List<Rental> rentals;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.rentals = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Rental> getRentals() { return rentals; }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public Rental getActiveRentalByCarId(String carId) {
        for (Rental rental : rentals) {
            if (rental.getCar().getId().equals(carId) && rental.getReturnDate() == null) {
                return rental;
            }
        }
        return null;
    }
}

// Rental.java
class Rental {
    private Car car;
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate returnDate;

    public Rental(Car car, Customer customer) {
        this.car = car;
        this.customer = customer;
        this.rentalDate = LocalDate.now();
        this.returnDate = null;
    }

    public void returnCar() {
        if (returnDate != null) {
            throw new IllegalStateException("Car already returned.");
        }
        this.returnDate = LocalDate.now();
        car.returnCar();
    }

    public Car getCar() { return car; }
    public Customer getCustomer() { return customer; }
    public LocalDate getRentalDate() { return rentalDate; }
    public LocalDate getReturnDate() { return returnDate; }
}

// RentalAgency.java
class RentalAgency {
    private List<Car> cars;
    private List<Customer> customers;

    public RentalAgency() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Car findCarById(String id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public Customer findCustomerById(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public boolean rentCar(String carId, String customerId) {
        Car car = findCarById(carId);
        Customer customer = findCustomerById(customerId);
        if (car != null && customer != null && car.isAvailable()) {
            try {
                car.rent();
                Rental rental = new Rental(car, customer);
                customer.addRental(rental);
                return true;
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public boolean returnCar(String carId) {
        Car car = findCarById(carId);
        if (car != null && !car.isAvailable()) {
            for (Customer customer : customers) {
                Rental rental = customer.getActiveRentalByCarId(carId);
                if (rental != null) {
                    try {
                        rental.returnCar();
                        return true;
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return false;
    }
}

// Main.java
public class assignment {
    public static void main(String[] args) {
        RentalAgency agency = new RentalAgency();

        Car car1 = new Car("C1", "Toyota Corolla");
        Car car2 = new Car("C2", "Honda Civic");
        agency.addCar(car1);
        agency.addCar(car2);

        Customer customer1 = new Customer("CU1", "Alice");
        agency.addCustomer(customer1);

        boolean rented = agency.rentCar("C1", "CU1");
        System.out.println("Car C1 rented: " + rented);

        boolean returned = agency.returnCar("C1");
        System.out.println("Car C1 returned: " + returned);
    }
}
