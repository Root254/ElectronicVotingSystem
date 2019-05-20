package sample;

import java.util.Date;

class ElectionDetails {
    static String name;
    static Date date;

//    ElectionDetails() {
//    }
//
//    ElectionDetails(String name, Date date) {
//        ElectionDetails.name = name;
//        ElectionDetails.date = date;
//    }
//
//    public String getName() {
//        return ElectionDetails.name;
//    }
//
//    public void setName(String name) {
//        ElectionDetails.name = name;
//    }
//
//    public Date getDate() {
//        return ElectionDetails.date;
//    }
//
//    public void setDate(Date date) {
//        ElectionDetails.date = date;
//    }

    public static void setElectionDetails(String name, Date date) {
        ElectionDetails.name = name;
        ElectionDetails.date = date;
    }
}
