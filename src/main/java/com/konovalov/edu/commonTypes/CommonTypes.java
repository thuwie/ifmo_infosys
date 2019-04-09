package com.konovalov.edu.commonTypes;

//TODO(ipoliakov): may be move to database
public class CommonTypes {
    public enum requestStatus{
        WAIT("Wait for manager decision"),
        APPROVED("Approved"),
        DISAPPROVED("Disapproved");

        private final String name;
        public String getName() {
            return name;
        }

        requestStatus(String s) {
            name = s;
        }
    }

    public enum userRole  {
        NULL("NULL"),
        ADMIN("Admin"),
        USER("User"),
        MANAGER("Manager");

        private final String name;
        public String getName() {
            return name;
        }

        userRole (String s) {
            name = s;
        }
    }


    public static class approveStruct {
        public boolean approveStatus;
    }
}
