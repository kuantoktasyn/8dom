
interface Beverage {
    String getDescription();
    double cost();
}

class Espresso implements Beverage {
    public String getDescription() { return "Эспрессо"; }
    public double cost() { return 800; }
}

class Tea implements Beverage {
    public String getDescription() { return "Шай"; }
    public double cost() { return 500; }
}

class Latte implements Beverage {
    public String getDescription() { return "Латте"; }
    public double cost() { return 900; }
}

class Mocha implements Beverage {
    public String getDescription() { return "Мокко"; }
    public double cost() { return 950; }
}

abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;
    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
    public String getDescription() { return beverage.getDescription(); }
    public double cost() { return beverage.cost(); }
}

class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) { super(beverage); }
    public String getDescription() { return beverage.getDescription() + ", сүт"; }
    public double cost() { return beverage.cost() + 150; }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) { super(beverage); }
    public String getDescription() { return beverage.getDescription() + ", қант"; }
    public double cost() { return beverage.cost() + 50; }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) { super(beverage); }
    public String getDescription() { return beverage.getDescription() + ", қаймақ"; }
    public double cost() { return beverage.cost() + 200; }
}


interface IPaymentProcessor {
    void processPayment(double amount);
}

class PayPalPaymentProcessor implements IPaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("PayPal арқылы төлем: " + amount + " теңге");
    }
}

class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Stripe жүйесі арқылы транзакция: " + totalAmount + " теңге");
    }
}

class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripeService;
    public StripePaymentAdapter(StripePaymentService stripeService) {
        this.stripeService = stripeService;
    }
    public void processPayment(double amount) {
        stripeService.makeTransaction(amount);
    }
}

class KaspiPaymentService {
    public void sendPayment(double sum) {
        System.out.println("Kaspi.kz арқылы төлем жіберілді: " + sum + " теңге");
    }
}

class KaspiPaymentAdapter implements IPaymentProcessor {
    private KaspiPaymentService kaspiService;
    public KaspiPaymentAdapter(KaspiPaymentService kaspiService) {
        this.kaspiService = kaspiService;
    }
    public void processPayment(double amount) {
        kaspiService.sendPayment(amount);
    }
}


public class Main {
    public static void main(String[] args) {

        System.out.println("ДЕКОРАТОР ПАТТЕРН");

        Beverage order1 = new Espresso();
        order1 = new Milk(order1);
        order1 = new Sugar(order1);
        System.out.println(order1.getDescription() + " -> " + order1.cost() + " теңге");

        Beverage order2 = new Latte();
        order2 = new WhippedCream(order2);
        order2 = new Sugar(order2);
        System.out.println(order2.getDescription() + " -> " + order2.cost() + " теңге");

        System.out.println("\nАДАПТЕР ПАТТЕРН");

        IPaymentProcessor paypal = new PayPalPaymentProcessor();
        paypal.processPayment(5000);

        IPaymentProcessor stripe = new StripePaymentAdapter(new StripePaymentService());
        stripe.processPayment(7500);

        IPaymentProcessor kaspi = new KaspiPaymentAdapter(new KaspiPaymentService());
        kaspi.processPayment(10000);
    }
}
