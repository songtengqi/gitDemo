package httpDemo;



import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8080/DRRS?wsdl");
        QName qName = new QName("http://httpDemo/","DRRSImplService");
        Service service = Service.create(url,qName);
        DRRS port = service.getPort(DRRS.class);
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        System.out.println("Welcome to Distributed Room Reservation System");
        while (flag) {

            System.out.println("please enter your ID please: ");
            String ID = scanner.next();
            if (ID.contains("A")||ID.contains("a")) {
                System.out.println("Welcome to the AdminClient");

                while (true) {
                    System.out.println("How can I help you(enter c for Create Room, d for Deleter Room: ");
                    String coop = scanner.next();
                    if (coop.equalsIgnoreCase("C")) {
                        System.out.println("Please enter the campus: ");
                        String campus = scanner.next();
                        System.out.println("Please enter the room number: ");
                        Integer room = scanner.nextInt();
                        System.out.println("Please enter the date: ");
                        String date = scanner.next();
                        ArrayList<String> time = new ArrayList<>();
                        while (true){
                            System.out.println("please enter the time plot: ");
                            String s = scanner.next();
                            time.add(s);
                            System.out.println("continue to add?(Y/N): ");
                            String s1 = scanner.next();
                            if (s1.equalsIgnoreCase("Y")){
                                continue;
                            }
                            break;
                        }
                        String s = port.createRoom(room,date,campus,time);
                        System.out.println(s);


                    }
                    if (coop.equalsIgnoreCase("d")) {
                        //nt roomnum, String date, String campus,ArrayList<String> timeSlots
                        System.out.println("Please enter the campus: ");
                        String campus = scanner.next();
                        System.out.println("Please enter the Date");
                        String date = scanner.next();
                        System.out.println("Please enter the room number");
                        Integer roomnum = scanner.nextInt();
                        ArrayList<String> time = new ArrayList<>();
                        while (true){
                            System.out.println("please enter the time plot: ");
                            String s = scanner.next();
                            time.add(s);
                            System.out.println("continue to add?(Y/N): ");
                            String s1 = scanner.next();
                            if (s1.equalsIgnoreCase("Y")){
                                continue;
                            }
                            break;
                        }
                        System.out.println(port.deleteRoom(roomnum,date,campus,time));

                    }
                    System.out.println("Would you like more cooperation as Admin?(Y/N): ");
                    String con = scanner.next();
                    if (con.equalsIgnoreCase("Y")) {
                        continue;
                    }else {
                        break;
                    }

                }
            }else {
                System.out.println("Welcome to StudentClient");
                while (true) {
                    System.out.println("How can I help you?(b for book room, g for get available time, c for cancel booked room" +
                            ", r for change reservation)");
                    String answer = scanner.next();
                    if (answer.equalsIgnoreCase("g")) {
                        System.out.println("Enter the date you want to search:(DD-MM-YYYY)");
                        String date = scanner.next();
                        System.out.println(port.getAvailableTimeSlot(date));
                    } else if (answer.equalsIgnoreCase("b")) {
                        System.out.println("Please enter the campus ( DVL for Dorval-Campus, KKL for Kirkland-Campus and\n" +
                                "WST for Westmount-Campus): ");
                        String campus = scanner.next();
                        System.out.println("Please enter the date(DD-MM-YYYY)");
                        String date = scanner.next();
                        System.out.println("PLease enter the room number: ");
                        Integer room = scanner.nextInt();
                        System.out.println("PLease enter the timeslot: ");
                        String time = scanner.next();
                        System.out.println(port.bookRoom(campus, room, date, time, ID));
                    } else if (answer.equalsIgnoreCase("c")) {
                        System.out.println("please enter the Booking ID:");
                        String bookingId = scanner.next();
                        System.out.println(port.cancelBooking(bookingId));
                    }else if (answer.equalsIgnoreCase("r")){
                        System.out.println("please enter the old BookingID");
                        String BookingID = scanner.next();

                        System.out.println("please enter the new campus: ");
                        String campus = scanner.next();

                        System.out.println("please enter the new time slot: ");
                        String timeslot = scanner.next();

                        System.out.println("please enter thr new room number: ");
                        Integer room = scanner.nextInt();

                        System.out.println(port.changeReservation(BookingID,campus,room,timeslot));
                    }
                    else {
                        System.out.println("not a valid input, try again");
                    }

                    System.out.println("Would you like more cooperation as Student?(Y/N): ");
                    String con = scanner.next();
                    if (con.equalsIgnoreCase("Y")) {
                        continue;
                    }else {
                        break;
                    }
                }
            }

            System.out.println("continue to use System?(Y/N): ");
            String sys = scanner.next();
            if (sys.equalsIgnoreCase("Y")) {

            }else {
                flag=false;
            }

            // System.out.println("Remote method invoked");
        }//while loop


    }
}
