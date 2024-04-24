package com.example.queueless;

public class infoOfReservation {
    String Status;
    String Name;
    String Location;
    int numOfPerson;
    String Time;
    int NumberOfReservation;

    byte[] byteArray;

    public infoOfReservation() {}

    public infoOfReservation(String status, String name, String location, int numOfPerson, String time, int numberOfReservation, byte[] byteArray) {
        Status = status;
        Name = name;
        Location = location;
        this.numOfPerson = numOfPerson;
        Time = time;
        NumberOfReservation = numberOfReservation;
        this.byteArray = byteArray;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getNumOfPerson() {
        return numOfPerson;
    }

    public void setNumOfPerson(int numOfPerson) {
        this.numOfPerson = numOfPerson;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getNumberOfReservation() {
        return NumberOfReservation;
    }

    public void setNumberOfReservation(int numberOfReservation) {
        NumberOfReservation = numberOfReservation;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
