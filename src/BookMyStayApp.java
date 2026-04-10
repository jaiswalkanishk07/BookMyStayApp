import java.util.*;

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