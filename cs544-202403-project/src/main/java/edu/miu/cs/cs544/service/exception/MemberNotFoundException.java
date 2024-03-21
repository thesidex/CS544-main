package edu.miu.cs.cs544.service.exception;

public class MemberNotFoundException extends Exception {
    public final long requestedMemberId;

    public MemberNotFoundException(long requestedMemberId) {
        this.requestedMemberId = requestedMemberId;
    }
}
