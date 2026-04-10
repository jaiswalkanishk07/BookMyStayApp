import java.util.*;
import java.io.*;

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Hotel Booking Management System Main Entry Point");
    }
}


//UC1: Application Entry & Welcome Message
class UseCase1HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("System initialized successfully.");
    }
}


//UC2: Room Initialization & Static Availability
class UseCase2RoomInitialization {

    public static void main(String[] args) {
        System.out.println("Hotel Room Initialization\n");

        // Initializing specific room types with predefined attributes
        SingleRoom single = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        // Static availability (represented using simple variables for now)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Displaying details
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }

    // --- Domain Models ---

    abstract static class Room {
        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
            this.numberOfBeds = numberOfBeds;
            this.squareFeet = squareFeet;
            this.pricePerNight = pricePerNight;
        }

        public void displayRoomDetails() {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sqft");
            System.out.println("Price per night: " + pricePerNight);
        }
    }

    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 250, 1500.0);
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 400, 2500.0);
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 750, 5000.0);
        }
    }
}


//UC3: Centralized Room Inventory Management
class UseCase3InventorySetup {

    public static void main(String[] args) {
        System.out.println("Hotel Room Inventory Status\n");

        // Single Source of Truth for availability
        RoomInventory inventory = new RoomInventory();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Room objects provide the pricing and characteristics
        SingleRoom single = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        // Displaying details by combining Room models and Inventory data
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Single"));

        System.out.println("\nDouble Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Double"));

        System.out.println("\nSuite Room:");
        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Suite"));
    }

    // --- Inventory Management ---

    static class RoomInventory {
        private Map<String, Integer> roomAvailability;

        public RoomInventory() {
            roomAvailability = new HashMap<>();
            initializeInventory();
        }

        private void initializeInventory() {
            // Centralizing setup instead of using scattered variables
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public Map<String, Integer> getRoomAvailability() {
            return roomAvailability;
        }

        public void updateAvailability(String roomType, int count) {
            roomAvailability.put(roomType, count);
        }
    }

    // --- Reusing Domain Models from UC2 ---

    abstract static class Room {
        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
            this.numberOfBeds = numberOfBeds;
            this.squareFeet = squareFeet;
            this.pricePerNight = pricePerNight;
        }

        public void displayRoomDetails() {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sqft");
            System.out.println("Price per night: " + pricePerNight);
        }
    }

    static class SingleRoom extends Room {
        public SingleRoom() { super(1, 250, 1500.0); }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() { super(2, 400, 2500.0); }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() { super(3, 750, 5000.0); }
    }
}


//UC4: Room Search & Availability Check
class UseCase4RoomSearch {

    public static void main(String[] args) {
        System.out.println("Room Search\n");

        // Dependencies: Inventory and Room Definitions
        RoomInventory inventory = new RoomInventory();
        SingleRoom single = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        // Search Service (Read-Only Access)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }

    // --- Search Service (Read-Only) ---

    static class RoomSearchService {
        public void searchAvailableRooms(RoomInventory inventory, Room singleRoom, Room doubleRoom, Room suiteRoom) {
            Map<String, Integer> availability = inventory.getRoomAvailability();

            // Check and display Single Room availability
            if (availability.get("Single") > 0) {
                System.out.println("Single Room:");
                singleRoom.displayRoomDetails();
                System.out.println("Available: " + availability.get("Single") + "\n");
            }

            // Check and display Double Room availability
            if (availability.get("Double") > 0) {
                System.out.println("Double Room:");
                doubleRoom.displayRoomDetails();
                System.out.println("Available: " + availability.get("Double") + "\n");
            }

            // Check and display Suite Room availability
            if (availability.get("Suite") > 0) {
                System.out.println("Suite Room:");
                suiteRoom.displayRoomDetails();
                System.out.println("Available: " + availability.get("Suite") + "\n");
            }
        }
    }

    // --- Inventory & Domain Models (Reused from UC3) ---

    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();

        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public Map<String, Integer> getRoomAvailability() {
            return roomAvailability;
        }
    }

    abstract static class Room {
        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
            this.numberOfBeds = numberOfBeds;
            this.squareFeet = squareFeet;
            this.pricePerNight = pricePerNight;
        }

        public void displayRoomDetails() {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sqft");
            System.out.println("Price per night: " + pricePerNight);
        }
    }

    static class SingleRoom extends Room { public SingleRoom() { super(1, 250, 1500.0); } }
    static class DoubleRoom extends Room { public DoubleRoom() { super(2, 400, 2500.0); } }
    static class SuiteRoom extends Room { public SuiteRoom() { super(3, 750, 5000.0); } }
}


//Use Case 5: Booking Request Queue (FIFO)
class UseCase5BookingRequestQueue {

    public static void main(String[] args) {
        System.out.println("Booking Request Queue");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to the queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued booking requests in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation next = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: " + next.getGuestName() +
                    ", Room Type: " + next.getRoomType());
        }
    }

    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }

    // --- Service: BookingRequestQueue ---

    static class BookingRequestQueue {
        private Queue<Reservation> requestQueue;

        public BookingRequestQueue() {
            this.requestQueue = new LinkedList<>();
        }

        public void addRequest(Reservation reservation) {
            requestQueue.offer(reservation);
        }

        public Reservation getNextRequest() {
            return requestQueue.poll();
        }

        public boolean hasPendingRequests() {
            return !requestQueue.isEmpty();
        }
    }
}


//UC6: Reservation Confirmation & Room Allocation
class UseCase6RoomAllocation {

    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        // Initialize Centralized Inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize Allocation Service
        RoomAllocationService allocationService = new RoomAllocationService();

        // Simulate a Queue of incoming reservations (FIFO)
        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.offer(new Reservation("Abhi", "Single"));
        requestQueue.offer(new Reservation("Subha", "Single"));
        requestQueue.offer(new Reservation("Vanmathi", "Suite"));

        // Process each reservation in the queue
        while (!requestQueue.isEmpty()) {
            allocationService.allocateRoom(requestQueue.poll(), inventory);
        }
    }

    // --- Allocation Service ---

    static class RoomAllocationService {
        private Set<String> allocatedRoomIds;
        private Map<String, Set<String>> assignedRoomsByType;

        public RoomAllocationService() {
            this.allocatedRoomIds = new HashSet<>();
            this.assignedRoomsByType = new HashMap<>();
        }

        public void allocateRoom(Reservation reservation, RoomInventory inventory) {
            String type = reservation.getRoomType();
            int currentAvailable = inventory.getRoomAvailability().getOrDefault(type, 0);

            if (currentAvailable > 0) {
                // Generate Unique ID
                String roomId = generateRoomId(type);

                // Confirm Allocation
                allocatedRoomIds.add(roomId);
                assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);

                // Update Inventory immediately
                inventory.updateAvailability(type, currentAvailable - 1);

                System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                        ", Room ID: " + roomId);
            } else {
                System.out.println("Booking failed for Guest: " + reservation.getGuestName() +
                        ". No " + type + " rooms available.");
            }
        }

        private String generateRoomId(String roomType) {
            int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
            return roomType + "-" + count;
        }
    }

    // --- Supporting Classes (Reused & Simplified) ---

    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();
        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }
        public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
        public void updateAvailability(String type, int count) { roomAvailability.put(type, count); }
    }

    static class Reservation {
        private String guestName;
        private String roomType;
        public Reservation(String name, String type) { this.guestName = name; this.roomType = type; }
        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }
}


//UC7: Add-On Service Selection
class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {
        System.out.println("Add-On Service Selection");

        // Confirmed Reservation ID from previous allocation
        String reservationId = "Single-1";

        // Initialize Service Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Available Services
        Service breakfast = new Service("Breakfast", 500.0);
        Service spa = new Service("Spa", 1000.0);

        // Attach services to the reservation
        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, spa);

        // Calculate and display results
        double totalCost = serviceManager.calculateTotalServiceCost(reservationId);
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }

    // --- Data Model: Service ---

    static class Service {
        private String serviceName;
        private double cost;

        public Service(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() { return serviceName; }
        public double getCost() { return cost; }
    }

    // --- Service Manager ---

    static class AddOnServiceManager {
        /** Maps reservation ID to selected services. */
        private Map<String, List<Service>> servicesByReservation;

        public AddOnServiceManager() {
            this.servicesByReservation = new HashMap<>();
        }

        /** Attaches a service to a reservation. */
        public void addService(String reservationId, Service service) {
            servicesByReservation
                    .computeIfAbsent(reservationId, k -> new ArrayList<>())
                    .add(service);
        }

        /** Calculates total add-on cost for a reservation. */
        public double calculateTotalServiceCost(String reservationId) {
            List<Service> services = servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
            double total = 0;
            for (Service s : services) {
                total += s.getCost();
            }
            return total;
        }
    }
}


//UC8: Booking History & Reporting
class UseCase8BookingHistoryReport {

    public static void main(String[] args) {
        System.out.println("Booking History and Reporting\n");

        // Initialize Data Storage
        BookingHistory history = new BookingHistory();

        // Simulate confirming reservations
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Initialize Reporting Service
        BookingReportService reportService = new BookingReportService();

        // Generate the report
        reportService.generateReport(history);
    }

    // --- Data Storage: BookingHistory ---

    static class BookingHistory {
        /** List that stores confirmed reservations. */
        private List<Reservation> confirmedReservations;

        public BookingHistory() {
            this.confirmedReservations = new ArrayList<>();
        }

        /** Adds a confirmed reservation to booking history. */
        public void addReservation(Reservation reservation) {
            confirmedReservations.add(reservation);
        }

        /** Returns all confirmed reservations. */
        public List<Reservation> getConfirmedReservations() {
            return confirmedReservations;
        }
    }

    // --- Service: BookingReportService ---

    static class BookingReportService {
        /**
         * Displays a summary report of all confirmed bookings.
         * Separates reporting logic from data storage.
         */
        public void generateReport(BookingHistory history) {
            System.out.println("Booking History Report");
            for (Reservation res : history.getConfirmedReservations()) {
                System.out.println("Guest: " + res.getGuestName() +
                        ", Room Type: " + res.getRoomType());
            }
        }
    }

    // --- Data Model: Reservation (Reused) ---

    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }
}


//UC9: Error Handling & Validation
class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {
        System.out.println("Booking Validation");
        Scanner scanner = new Scanner(System.in);

        // Initialize required components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Centralized validation
            validator.validate(guestName, roomType, inventory);

            // If validation passes, add to queue
            bookingQueue.addRequest(new Reservation(guestName, roomType));
            System.out.println("Request added to queue successfully.");

        } catch (InvalidBookingException e) {
            // Handle domain-specific validation errors
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // --- Custom Exception ---

    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // --- Validator Service ---

    static class ReservationValidator {
        /**
         * Validates booking input.
         * Note: As per requirement, room type check is case-sensitive.
         */
        public void validate(String guestName, String roomType, RoomInventory inventory)
                throws InvalidBookingException {

            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            Map<String, Integer> availability = inventory.getRoomAvailability();

            // Check if the room type exists (Case-Sensitive)
            if (!availability.containsKey(roomType)) {
                throw new InvalidBookingException("Invalid room type selected.");
            }

            // Check if rooms are available
            if (availability.get(roomType) <= 0) {
                throw new InvalidBookingException("No rooms available for the selected type.");
            }
        }
    }

    // --- Reused Components ---

    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();
        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 0); // Setting Suite to 0 to test validation
        }
        public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
    }

    static class Reservation {
        private String guestName;
        private String roomType;
        public Reservation(String name, String type) { this.guestName = name; this.roomType = type; }
    }

    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();
        public void addRequest(Reservation r) { queue.offer(r); }
    }
}


//UC10: Booking Cancellation & Inventory Rollback
class UseCase10BookingCancellation {

    public static void main(String[] args) {
        System.out.println("Booking Cancellation");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        // 1. Register some bookings (Simulating existing confirmed reservations)
        cancellationService.registerBooking("Single-1", "Single");
        cancellationService.registerBooking("Double-1", "Double");

        // 2. Perform a cancellation
        cancellationService.cancelBooking("Single-1", inventory);

        // 3. Show Rollback History
        cancellationService.showRollbackHistory();

        // 4. Verify Inventory Restore
        System.out.println("Updated Single Room Availability: " +
                inventory.getRoomAvailability().get("Single"));
    }

    // --- Cancellation Service ---

    static class CancellationService {
        /** Stack that stores recently released room IDs for rollback tracking. */
        private Stack<String> releasedRoomIds;

        /** Maps reservation ID to room type to know what to restore in inventory. */
        private Map<String, String> reservationRoomTypeMap;

        public CancellationService() {
            this.releasedRoomIds = new Stack<>();
            this.reservationRoomTypeMap = new HashMap<>();
        }

        /** Registers a confirmed booking to allow for later cancellation. */
        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        /** Cancels a confirmed booking and restores inventory safely. */
        public void cancelBooking(String reservationId, RoomInventory inventory) {
            if (reservationRoomTypeMap.containsKey(reservationId)) {
                String roomType = reservationRoomTypeMap.get(reservationId);
                int currentCount = inventory.getRoomAvailability().getOrDefault(roomType, 0);

                // Inventory Restoration
                inventory.updateAvailability(roomType, currentCount + 1);

                // Track for Rollback visualization
                releasedRoomIds.push(reservationId);

                // Remove from active reservations
                reservationRoomTypeMap.remove(reservationId);

                System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType + "\n");
            } else {
                System.out.println("Error: Invalid Reservation ID. Cancellation failed.");
            }
        }

        /** Displays recently cancelled reservations (LIFO order). */
        public void showRollbackHistory() {
            System.out.println("Rollback History (Most Recent First):");
            if (releasedRoomIds.isEmpty()) {
                System.out.println("No recent cancellations.");
            } else {
                // Using a copy or iterator to show stack contents
                for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
                    System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
                }
            }
            System.out.println();
        }
    }

    // --- Reused Inventory Component ---

    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();
        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
        }
        public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
        public void updateAvailability(String type, int count) { roomAvailability.put(type, count); }
    }
}


//UC11: Concurrent Booking Simulation
class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");

        // Shared Resources
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Adding initial requests to the queue
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));

        // Create booking processor tasks
        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        // Start concurrent processing
        t1.start();
        t2.start();

        try {
            // Wait for threads to finish processing for a short time
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\nRemaining Inventory:");
        inventory.displayInventory();
    }

    // --- Processor: ConcurrentBookingProcessor ---

    static class ConcurrentBookingProcessor implements Runnable {
        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(BookingRequestQueue bq, RoomInventory inv, RoomAllocationService ras) {
            this.bookingQueue = bq;
            this.inventory = inv;
            this.allocationService = ras;
        }

        @Override
        public void run() {
            while (true) {
                Reservation reservation = null;

                // Synchronize on the queue to ensure only one thread retrieves a request
                synchronized (bookingQueue) {
                    if (bookingQueue.hasPendingRequests()) {
                        reservation = bookingQueue.getNextRequest();
                    } else {
                        break; // No more requests, exit thread
                    }
                }

                if (reservation != null) {
                    // Synchronize on inventory to ensure atomic allocation (Thread-Safe)
                    synchronized (inventory) {
                        allocationService.allocateRoom(reservation, inventory);
                    }
                }
            }
        }
    }

    // --- Services & Models (Thread-Safe versions) ---

    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();
        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }
        public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
        public void updateAvailability(String type, int count) { roomAvailability.put(type, count); }
        public void displayInventory() {
            roomAvailability.forEach((k, v) -> System.out.println(k + ": " + v));
        }
    }

    static class RoomAllocationService {
        private Map<String, Integer> counters = new HashMap<>();
        public void allocateRoom(Reservation res, RoomInventory inv) {
            String type = res.getRoomType();
            int available = inv.getRoomAvailability().get(type);
            if (available > 0) {
                int id = counters.getOrDefault(type, 0) + 1;
                counters.put(type, id);
                inv.updateAvailability(type, available - 1);
                System.out.println("Booking confirmed for Guest: " + res.getGuestName() +
                        ", Room ID: " + type + "-" + id);
            }
        }
    }

    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();
        public void addRequest(Reservation r) { queue.offer(r); }
        public Reservation getNextRequest() { return queue.poll(); }
        public boolean hasPendingRequests() { return !queue.isEmpty(); }
    }

    static class Reservation {
        private String guestName;
        private String roomType;
        public Reservation(String n, String t) { this.guestName = n; this.roomType = t; }
        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }
}


//UC12: Data Persistence & System Recovery
class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {
        System.out.println("System Recovery");

        String filePath = "inventory.txt";
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        // Attempt to load previous state
        persistenceService.loadInventory(inventory, filePath);

        System.out.println("\nCurrent Inventory:");
        inventory.displayInventory();

        // Save current state (simulating an update or system shutdown)
        persistenceService.saveInventory(inventory, filePath);
        System.out.println("Inventory saved successfully.");
    }

    // --- Persistence Service ---

    static class FilePersistenceService {
        /** Saves room inventory state to a file in format: roomType=availableCount */
        public void saveInventory(RoomInventory inventory, String filePath) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                Map<String, Integer> availability = inventory.getRoomAvailability();
                for (Map.Entry<String, Integer> entry : availability.entrySet()) {
                    writer.println(entry.getKey() + "=" + entry.getValue());
                }
            } catch (IOException e) {
                System.out.println("Error saving inventory: " + e.getMessage());
            }
        }

        /** Loads room inventory state from a file. restores system on startup. */
        public void loadInventory(RoomInventory inventory, String filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        inventory.updateAvailability(parts[0], Integer.parseInt(parts[1]));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error loading inventory: " + e.getMessage());
            }
        }
    }

    // --- Reused Inventory Component ---

    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();

        public RoomInventory() {
            // Default values if no file is found
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
        public void updateAvailability(String type, int count) { roomAvailability.put(type, count); }
        public void displayInventory() {
            roomAvailability.forEach((k, v) -> System.out.println(k + ": " + v));
        }
    }
}