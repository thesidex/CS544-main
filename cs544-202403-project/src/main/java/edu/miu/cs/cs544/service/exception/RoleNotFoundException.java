package edu.miu.cs.cs544.service.exception;

public class RoleNotFoundException extends Exception {

    public final long requestedRoleId;

    public RoleNotFoundException(long requestedRoleId) {
        this.requestedRoleId = requestedRoleId;
    }
}
