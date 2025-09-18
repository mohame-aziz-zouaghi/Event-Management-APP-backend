package tn.esprit.examen.EventManagement.dto;

import tn.esprit.examen.EventManagement.entities.Reservation;

public class ReservationMapper {
    public static ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) return null;

        return ReservationDTO.builder()
                .id(reservation.getId())
                .userId(reservation.getUser().getId())
                .eventId(reservation.getEvent().getId())
                .reservationDate(reservation.getReservationDate())
                .status(reservation.getStatus().name())
                .ticketNumber(reservation.getTicketNumber())
                .username(reservation.getUser().getUsername())
                .eventtitle(reservation.getEvent().getTitle())
                .build();
    }
}
