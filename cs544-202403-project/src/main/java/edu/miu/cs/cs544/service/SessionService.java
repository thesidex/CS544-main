package edu.miu.cs.cs544.service;


import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.service.contract.SessionPayload;
import org.springframework.http.ResponseEntity;

public interface SessionService extends BaseReadWriteService<SessionPayload, Session, Long> {
    public ResponseEntity<?> createSessionByEventId(Session session, long eventId);
    public ResponseEntity<?> findSessionsByEventId(long eventId);

    ResponseEntity<?> updateSessionByEventIdAndSessionId(long eventId, long sessionId, SessionPayload sessionPayload);

    ResponseEntity<?> deleteSessionByEventIdAndSessionId(long eventId,long sessionId);

}
