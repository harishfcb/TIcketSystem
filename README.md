Certainly! Here's a consolidated version of the Ticket System API Documentation:

---

# Ticket System API Documentation

This Spring Boot application serves as a comprehensive ticketing system with an approval workflow. Users can raise tickets, and the tickets will go through an approval process involving Manager, Admin, and SuperAdmin. Once the SuperAdmin approves the ticket, it will be marked as closed.

## 1. Create User

- **Endpoint:** `/ticketsystem/user/save`
- **Request Method:** POST
- **Consumes:** application/json
- **Produces:** application/json
- **Request Body:**
  ```json
  {
      "userName": "max",
      "emailId": "max@gmail.com",
      "roleId": "f8af17f5-1caa-47a9-bd8b-a196aa36d654"
  }
  ```

## 2. Create Role

- **Endpoint:** `/ticketsystem/role/save`
- **Request Method:** POST
- **Consumes:** application/json
- **Produces:** application/json
- **Request Body:**
  ```json
  {
      "roleType": "CODER"
  }
  ```

## 3. Create Ticket

- **Endpoint:** `/ticketsystem/ticket/create`
- **Request Method:** POST
- **Consumes:** application/json
- **Produces:** application/json
- **Request Body:**
  ```json
  {
      "createdBy": "tim@gmail.com",
      "description": "Payment issue",
      "subject": "Payment on Hold"
  }
  ```

## 4. Update Ticket

- **Endpoint:** `/ticketsystem/ticket/update`
- **Request Method:** POST
- **Consumes:** application/json
- **Produces:** application/json
- **Request Body:**
  ```json
  {
      "ticketId": "cc9cba25-d355-475e-ac5f-e68913da0c6c",
      "ticketStatus": "APPROVED",
      "assignee": "jim@gmail.com"
  }
  ```

## 5. Ticket Event

- **Endpoint:** `/ticket/read/{ticketId}?pageNumber={}&count={}`
- **Request Method:** GET
- **Consumes:** application/json
- **Produces:** application/json
- **Params:**
  - `pageNumber`
  - `count`

