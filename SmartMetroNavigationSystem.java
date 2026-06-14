import java.util.*;

public class SmartMetroNavigationSystem {

    // ---------- Linked List ----------
    static class Request {
        int id;
        String pickup;
        String drop;
        int score;
        Request next;

        Request(int id, String pickup, String drop, int score) {
            this.id = id;
            this.pickup = pickup;
            this.drop = drop;
            this.score = score;
        }
    }

    static class RequestList {
        Request head;

        void add(int id, String pickup, String drop, int score) {
            Request newRequest = new Request(id, pickup, drop, score);

            if (head == null) {
                head = newRequest;
                return;
            }

            Request temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = newRequest;
        }

        void display() {
            Request temp = head;

            while (temp != null) {
                System.out.println("ID: " + temp.id +
                        " | " + temp.pickup +
                        " -> " + temp.drop +
                        " | Score: " + temp.score);
                temp = temp.next;
            }
        }
    }

    // ---------- BST ----------
    static class BSTNode {
        Request request;
        BSTNode left, right;

        BSTNode(Request request) {
            this.request = request;
        }
    }

    static class BST {
        BSTNode root;

        BSTNode insert(BSTNode root, Request request) {

            if (root == null)
                return new BSTNode(request);

            if (request.id < root.request.id)
                root.left = insert(root.left, request);

            else if (request.id > root.request.id)
                root.right = insert(root.right, request);

            return root;
        }

        void insert(Request request) {
            root = insert(root, request);
        }

        Request search(BSTNode root, int id) {

            if (root == null)
                return null;

            if (id == root.request.id)
                return root.request;

            if (id < root.request.id)
                return search(root.left, id);

            return search(root.right, id);
        }
    }

    // ---------- Graph ----------
    static class Graph {

        HashMap<String, HashMap<String, Integer>> map =
                new HashMap<>();

        void addStation(String station) {
            map.putIfAbsent(station, new HashMap<>());
        }

        void addRoute(String s1, String s2, int time) {
            map.get(s1).put(s2, time);
            map.get(s2).put(s1, time);
        }

        // NEW: show all stations
        void showStations() {
            System.out.println("\n_______ ALL STATIONS_______");
            for (String station : map.keySet()) {
                System.out.println("- " + station);
            }
        }

        void shortestPath(String source, String destination) {

            HashMap<String, Integer> distance = new HashMap<>();
            HashMap<String, String> parent = new HashMap<>();

            for (String station : map.keySet()) {
                distance.put(station, Integer.MAX_VALUE);
            }

            distance.put(source, 0);

            PriorityQueue<String> pq =
                    new PriorityQueue<>(Comparator.comparingInt(distance::get));

            pq.add(source);

            while (!pq.isEmpty()) {

                String current = pq.poll();

                for (String neighbour : map.get(current).keySet()) {

                    int newDistance =
                            distance.get(current)
                                    + map.get(current).get(neighbour);

                    if (newDistance < distance.get(neighbour)) {

                        distance.put(neighbour, newDistance);
                        parent.put(neighbour, current);
                        pq.add(neighbour);
                    }
                }
            }

            // ---------- BUILD PATH ----------
            List<String> path = new ArrayList<>();
            String current = destination;

            while (current != null) {
                path.add(current);
                current = parent.get(current);
            }

            Collections.reverse(path);

            // ---------- OUTPUT ----------
            System.out.println("\n===== ROUTE FOUND =====");

            System.out.print("Route: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i != path.size() - 1)
                    System.out.print(" -> ");
            }

            System.out.println();

            System.out.println("Total Stations in Path: " + path.size());

            System.out.println("Total Travel Time: "
                    + distance.get(destination) + " minutes");
        }


        void showMetroMap() {

            System.out.println("\n===== COMPLETE METRO MAP =====");

            for (String station : map.keySet()) {

                System.out.print(station + " -> ");

                for (String neighbour : map.get(station).keySet()) {
                    System.out.print(neighbour + " ");
                }

                System.out.println();
            }
        }

        int calculateFare(String source, String destination) {

            HashMap<String, Integer> distance = new HashMap<>();

            for(String station : map.keySet()){
                distance.put(station, Integer.MAX_VALUE);
            }

            distance.put(source,0);

            PriorityQueue<String> pq =
                    new PriorityQueue<>(Comparator.comparingInt(distance::get));

            pq.add(source);

            while(!pq.isEmpty()){

                String current = pq.poll();

                for(String neighbour : map.get(current).keySet()){

                    int newDistance =
                            distance.get(current)
                                    + map.get(current).get(neighbour);

                    if(newDistance < distance.get(neighbour)){

                        distance.put(neighbour,newDistance);
                        pq.add(neighbour);
                    }
                }
            }

            return distance.get(destination);
        }
    }

    // ---------- Main ----------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Graph metro = new Graph();

        // Stations
        metro.addStation("Shahdara");
        metro.addStation("MAO College");
        metro.addStation("GPO");
        metro.addStation("Kalma Chowk");
        metro.addStation("Gajjumata");
        metro.addStation("Salahuddin Road");
        metro.addStation("Chouburji");
        metro.addStation("Canal View");
        metro.addStation("Thokar Niaz Baig");
        metro.addStation("Hanjarwal");
        metro.addStation("Awan Town");
        metro.addStation("Sabzazar");
        metro.addStation("Data Darbar");
        metro.addStation("Anarkali");
        metro.addStation("Ichhra");
        metro.addStation("Muslim Town");
        metro.addStation("Punjab University");

        // Routes
        metro.addRoute("Shahdara", "MAO College", 5);
        metro.addRoute("MAO College", "GPO", 4);
        metro.addRoute("GPO", "Kalma Chowk", 6);
        metro.addRoute("Kalma Chowk", "Gajjumata", 5);
        metro.addRoute("Shahdara", "Data Darbar", 4);

        metro.addRoute("Data Darbar", "Anarkali", 3);

        metro.addRoute("Anarkali", "MAO College", 2);

        metro.addRoute("MAO College", "Chouburji", 3);

        metro.addRoute("Chouburji", "Ichhra", 4);

        metro.addRoute("Ichhra", "Muslim Town", 3);

        metro.addRoute("Muslim Town", "Kalma Chowk", 2);

        metro.addRoute("Kalma Chowk", "Canal View", 4);

        metro.addRoute("Canal View", "Punjab University", 3);

        metro.addRoute("Punjab University", "Awan Town", 4);

        metro.addRoute("Awan Town", "Sabzazar", 5);

        metro.addRoute("Sabzazar", "Hanjarwal", 4);

        metro.addRoute("Hanjarwal", "Thokar Niaz Baig", 3);

        metro.addRoute("Thokar Niaz Baig", "Gajjumata", 6);

        metro.addRoute("Salahuddin Road", "Shahdara", 3);

        // Requests
        RequestList requests = new RequestList();


        // BST
        BST bst = new BST();

        Request temp = requests.head;
        while (temp != null) {
            bst.insert(temp);
            temp = temp.next;
        }

        // Heap
        PriorityQueue<Request> heap =
                new PriorityQueue<>((a, b) -> b.score - a.score);

        temp = requests.head;
        while (temp != null) {
            heap.add(temp);
            temp = temp.next;
        }

        // MENU
        while (true) {

            System.out.println("\n===== SMART METRO NAVIGATION SYSTEM =====");
            System.out.println("1. Find Shortest Route");
            System.out.println("2. Show All Stations");
            System.out.println("3. View Requests");
            System.out.println("4. Search Request By ID");
            System.out.println("5. Ranked Requests");
            System.out.println("6. Add New Request");
            System.out.println("7. Show Complete Metro Map");
            System.out.println("8. Fare Calculator");
            System.out.println("9. Exit");
            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                System.out.print("Source Station: ");
                String source = sc.nextLine();

                System.out.print("Destination Station: ");
                String destination = sc.nextLine();

                metro.shortestPath(source, destination);
            }

            else if (choice == 2) {

                metro.showStations();
            }

            else if (choice == 3) {

                requests.display();
            }

            else if (choice == 4) {

                System.out.print("Enter Request ID: ");
                int id = sc.nextInt();

                Request found = bst.search(bst.root, id);

                if (found != null) {
                    System.out.println("Request Found");
                    System.out.println(found.pickup + " -> " + found.drop);
                    System.out.println("Score: " + found.score);
                } else {
                    System.out.println("Request Not Found");
                }
            }

            else if (choice == 5) {

                PriorityQueue<Request> tempHeap =
                        new PriorityQueue<>((a, b) -> b.score - a.score);

                tempHeap.addAll(heap);

                while (!tempHeap.isEmpty()) {

                    Request r = tempHeap.poll();

                    System.out.println(
                            "ID: " + r.id +
                                    " | " + r.pickup +
                                    " -> " + r.drop +
                                    " | Score: " + r.score);
                }
            }
            else if (choice == 6) {

                System.out.print("Enter Request ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                // Check if ID already exists
                if (bst.search(bst.root, id) != null) {
                    System.out.println("Request ID already exists!");
                    continue;
                }

                System.out.print("Enter Pickup Station: ");
                String pickup = sc.nextLine();

                System.out.print("Enter Drop Station: ");
                String drop = sc.nextLine();

                System.out.print("Enter Priority Score: ");
                int score = sc.nextInt();

                // Add to Linked List
                requests.add(id, pickup, drop, score);

                // Find newly added request
                Request newRequest = requests.head;
                while (newRequest.next != null) {
                    newRequest = newRequest.next;
                }

                // Insert into BST
                bst.insert(newRequest);

                // Insert into Heap
                heap.add(newRequest);

                System.out.println("\nRequest Added Successfully!");System.out.println("\n===== NEW REQUEST =====");
                System.out.println("ID: " + id);
                System.out.println("Pickup: " + pickup);
                System.out.println("Drop: " + drop);
                System.out.println("Priority Score: " + score);

            }


                else if(choice == 7){

                    metro.showMetroMap();

            }else if(choice == 8) {

                System.out.print("Enter Source Station: ");
                String source = sc.nextLine();

                System.out.print("Enter Destination Station: ");
                String destination = sc.nextLine();

                if(!metro.map.containsKey(source)
                        || !metro.map.containsKey(destination)){

                    System.out.println("Invalid Station Name!");
                    continue;
                }

                int time = metro.calculateFare(source,destination);

                int fare = 20 + (time * 2);

                System.out.println("\n===== FARE DETAILS =====");
                System.out.println("Travel Time: " + time + " minutes");
                System.out.println("Estimated Fare: Rs. " + fare);
            } else if (choice==9) {
                System.out.println("THANK YOU for Visiting Lahore Mass Transit Authority");
                System.out.println("Exiting..........");

            } else {
                System.out.println("Invalid Choice");
            }
        }
    }
}