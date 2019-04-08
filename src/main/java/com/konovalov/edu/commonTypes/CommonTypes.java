package com.konovalov.edu.commonTypes;

//TODO(ipoliakov): may be move to database
public class CommonTypes {
    public enum requestStatus{
        WAIT("Wait for manager decision"),
        APPROVED("Approved"),
        DISAPPROVED("Disapproved");

        private final String name;

        requestStatus(String s) {
            name = s;
        }
    }
}
