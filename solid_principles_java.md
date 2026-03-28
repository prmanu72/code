# SOLID Principles in Java

Below is the essence of each SOLID principle, with Java examples and the core design intuition behind each one.

## 1. Single Responsibility Principle

A class should have one reason to change.

Essence:
A class should do one coherent job. If a class mixes business logic, persistence, formatting, validation, email sending, and logging, it becomes fragile because unrelated changes affect the same class.

Bad example:

```java
class InvoiceService {
    public double calculateTotal(Invoice invoice) {
        return invoice.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public void saveToDatabase(Invoice invoice) {
        System.out.println("Saving invoice to DB");
    }

    public void sendEmail(Invoice invoice) {
        System.out.println("Sending invoice email");
    }
}
```

Problem:
`InvoiceService` has multiple responsibilities:
- calculation
- persistence
- notification

A change in database logic or email format should not force this class to change if its main job is total calculation.

Better design:

```java
class InvoiceCalculator {
    public double calculateTotal(Invoice invoice) {
        return invoice.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}

class InvoiceRepository {
    public void save(Invoice invoice) {
        System.out.println("Saving invoice to DB");
    }
}

class InvoiceEmailSender {
    public void send(Invoice invoice) {
        System.out.println("Sending invoice email");
    }
}
```

Why this is better:
Each class has one clear responsibility. Changes stay localized.

Typical signal of SRP violation:
- class name ends with `Manager`, `Helper`, `Service` but does too many unrelated things
- too many methods from different domains
- one class changes for unrelated reasons

## 2. Open/Closed Principle

Software entities should be open for extension, but closed for modification.

Essence:
You should be able to add new behavior without constantly editing existing stable code. The usual way is to rely on abstractions and polymorphism instead of `if-else` chains.

Bad example:

```java
class DiscountCalculator {
    public double calculate(String customerType, double amount) {
        if ("REGULAR".equals(customerType)) {
            return amount * 0.95;
        } else if ("PREMIUM".equals(customerType)) {
            return amount * 0.90;
        } else if ("VIP".equals(customerType)) {
            return amount * 0.80;
        }
        return amount;
    }
}
```

Problem:
Every new customer type requires modifying this class. That increases regression risk.

Better design:

```java
interface DiscountStrategy {
    double applyDiscount(double amount);
}

class RegularDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.95;
    }
}

class PremiumDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.90;
    }
}

class VipDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.80;
    }
}

class DiscountCalculator {
    public double calculate(DiscountStrategy strategy, double amount) {
        return strategy.applyDiscount(amount);
    }
}
```

Usage:

```java
DiscountCalculator calculator = new DiscountCalculator();
double finalAmount = calculator.calculate(new PremiumDiscount(), 1000);
System.out.println(finalAmount);
```

Why this is better:
To add `StudentDiscount`, create a new class. Existing logic remains unchanged.

Typical signal of OCP violation:
- repeated `switch` or `if-else` based on type/category
- every new feature requires editing old, tested logic

## 3. Liskov Substitution Principle

Subtypes must be substitutable for their base types without breaking correctness.

Essence:
If `Child` is a subtype of `Parent`, then code using `Parent` should continue to work correctly when given `Child`. A subclass must honor the contract of the parent, not just match its method signatures.

Bad example:

```java
class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}

class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;
    }

    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}
```

Client code:

```java
class AreaTest {
    public static void resizeRectangle(Rectangle rectangle) {
        rectangle.setWidth(5);
        rectangle.setHeight(10);
        System.out.println(rectangle.getArea());
    }

    public static void main(String[] args) {
        resizeRectangle(new Rectangle()); // 50
        resizeRectangle(new Square());    // 100
    }
}
```

Problem:
`Square` changes the expected behavior of `Rectangle`. Client code assumes width and height can vary independently.

Better design:
Do not force square into rectangle inheritance if behavior differs.

```java
interface Shape {
    int getArea();
}

class Rectangle implements Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getArea() {
        return width * height;
    }
}

class Square implements Shape {
    private final int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}
```

Why this is better:
Both are `Shape`, and both honor the same contract: area calculation.

Typical signal of LSP violation:
- subclass throws `UnsupportedOperationException`
- subclass weakens validation rules
- subclass changes expected meaning of methods
- parent abstraction is too broad or wrong

## 4. Interface Segregation Principle

Clients should not be forced to depend on interfaces they do not use.

Essence:
Prefer small, focused interfaces over large “fat” ones. A class should not implement methods that are irrelevant to it.

Bad example:

```java
interface Machine {
    void print();
    void scan();
    void fax();
}

class BasicPrinter implements Machine {
    @Override
    public void print() {
        System.out.println("Printing");
    }

    @Override
    public void scan() {
        throw new UnsupportedOperationException("Scan not supported");
    }

    @Override
    public void fax() {
        throw new UnsupportedOperationException("Fax not supported");
    }
}
```

Problem:
`BasicPrinter` is forced to implement methods it does not support.

Better design:

```java
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

interface Fax {
    void fax();
}

class BasicPrinter implements Printer {
    @Override
    public void print() {
        System.out.println("Printing");
    }
}

class MultiFunctionPrinter implements Printer, Scanner, Fax {
    @Override
    public void print() {
        System.out.println("Printing");
    }

    @Override
    public void scan() {
        System.out.println("Scanning");
    }

    @Override
    public void fax() {
        System.out.println("Faxing");
    }
}
```

Why this is better:
Each class implements only the capabilities it actually supports.

Typical signal of ISP violation:
- interface has many methods but implementations use only some
- implementations throw unsupported exceptions
- clients depend on methods they never call

## 5. Dependency Inversion Principle

High-level modules should not depend on low-level modules. Both should depend on abstractions.

Essence:
Business logic should not be tightly coupled to concrete infrastructure classes. Depend on interfaces so implementations can vary without changing core logic.

Bad example:

```java
class MySqlDatabase {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class UserService {
    private final MySqlDatabase database = new MySqlDatabase();

    public void registerUser(String user) {
        database.save(user);
    }
}
```

Problem:
`UserService` is tightly coupled to `MySqlDatabase`. If you switch to PostgreSQL, file storage, or mock testing, you must change `UserService`.

Better design:

```java
interface Database {
    void save(String data);
}

class MySqlDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class PostgreSqlDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to PostgreSQL: " + data);
    }
}

class UserService {
    private final Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void registerUser(String user) {
        database.save(user);
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Database db = new MySqlDatabase();
        UserService service = new UserService(db);
        service.registerUser("Alice");
    }
}
```

Why this is better:
- easier to change implementations
- easier to test
- business logic is decoupled from storage details

Typical signal of DIP violation:
- `new` of concrete dependencies inside business classes
- business logic knows too much about frameworks, DBs, APIs
- testing is hard because dependencies are tightly bound

## How to remember SOLID quickly

- `S`: one class, one job
- `O`: extend behavior without modifying stable code
- `L`: child classes must truly behave like parents
- `I`: keep interfaces small and focused
- `D`: depend on abstractions, not concrete implementations

## Important practical note

SOLID is not about creating many classes just for the sake of it.  
Its purpose is to reduce coupling, improve changeability, and make code easier to reason about. If applied mechanically, it can overcomplicate simple systems.

A good rule:
Use SOLID where change, variation, and maintenance pressure actually exist.
