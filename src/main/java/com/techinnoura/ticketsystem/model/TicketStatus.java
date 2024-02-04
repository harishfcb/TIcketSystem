package com.techinnoura.ticketsystem.model;

public enum TicketStatus {
    CREATED,
    ASSIGNED,
    APPROVED,
    PENDING,
    CLOSED,
    AWAITING_ADMIN_APPROVAL,
    AWAITING_SUPER_ADMIN_APPROVAL,
    ASSIGNED_WITH_ADMIN,
    ASSIGNED_WITH_SUPER_ADMIN;
}
