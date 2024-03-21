package edu.miu.cs.cs544.service;


import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.dto.ErrorResponseDTO;
import edu.miu.cs.cs544.repository.EventRepository;
import edu.miu.cs.cs544.repository.SessionRepository;
import edu.miu.cs.cs544.service.contract.SessionPayload;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SessionServiceImpl extends BaseReadWriteServiceImpl<SessionPayload, Session, Long> implements SessionService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> createSessionByEventId(Session session, long eventId) {
        var optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            //Persist the session
            sessionRepository.save(session);

            //Add id to session through event
            var event = optionalEvent.get();
            event.addSchedule(session);
            eventRepository.save(event);

            return new ResponseEntity<>(session, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ErrorResponseDTO(404, "Event not found"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> findSessionsByEventId(long eventId) {
        var optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            return new ResponseEntity<>(optionalEvent.get().getSchedule(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponseDTO(404, "Event not found"), HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<?> updateSessionByEventIdAndSessionId(long eventId, long sessionId, SessionPayload sessionPayload) {
        var optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            var event = optionalEvent.get();
            Optional<Session> sessionOptional = event.getSchedule().stream().filter(session -> session.getId() == sessionId).findFirst();

            if (sessionOptional.isEmpty())
                return new ResponseEntity<>(new ErrorResponseDTO(404, "Session Not found"), HttpStatus.NOT_FOUND);

            Session session = sessionOptional.get();

            modelMapper.map(sessionPayload, session);
            sessionRepository.save(session);

            return new ResponseEntity<>(session, HttpStatus.OK);

        }
        return new ResponseEntity<>(new ErrorResponseDTO(404, "Event Not found"), HttpStatus.NOT_FOUND);


    }

    @Override
    public ResponseEntity<?> deleteSessionByEventIdAndSessionId(long eventId, long sessionId) {
        var optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            var event = optionalEvent.get();
            Optional<Session> sessionOptional = event.getSchedule().stream().filter(session -> session.getId() == sessionId).findFirst();

            if (sessionOptional.isEmpty())
                return new ResponseEntity<>(new ErrorResponseDTO(404, "Session Not found"), HttpStatus.NOT_FOUND);

            Session session = sessionOptional.get();

            event.getSchedule().remove(session);
            eventRepository.save(event);

            sessionRepository.delete(session);

            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(new ErrorResponseDTO(404, "Event Not found"), HttpStatus.NOT_FOUND);

    }

}
