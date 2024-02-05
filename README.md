

# Ticket System API Documentation

This Spring Boot application serves as a comprehensive ticketing system with an approval workflow. Users can raise tickets, and the tickets will go through an approval process involving Manager, Admin, and SuperAdmin. Once the SuperAdmin approves the ticket, it will be marked as closed.

## 1. Create User


This is used to create user who are part of the WorkFlow evey user will have a have roleId  the user may be Coder,Admin,Manager,SuperAdmin.The users who are Coder must have have a mangerId.So When a Coder Creates a Ticket it will be automatically Assigned to the manger asssociated with the  user.

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

This is used to create Role There are only four Roles CODER,MANAGER,ADMIN,SUPERADMIN.For the RoleType an role Id will be generated.The generated RoleId Should be be given in the roleId field when creating a user.

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

This is used to create a Ticket Initially.An user will create a create and it will be automatically assigned to the Manager Associated with the User.

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

This is used to update the Ticket.When a manager approves the ticket it will move to the awating admin approval.So any admin can pick and assign to himself once it is assigned it will be lokcked.if another admin tries to assign to himself it will throw an error.This is because a user  will have only one manager but there will be n number of admins.So when the assigned admin approves it moves awaiting approval from super admin.once a super admin assigns the ticket to himself the ticket will be locked.if any othe super admin tries to assign to himself it will throw an error.Once the assigned super admin approves the ticket will be closed.

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

This feature provides a comprehensive overview of the entire history of the ticket, offering a detailed sequence of actions and events that have transpired throughout its lifecycle.

- **Endpoint:** `/ticketsystem/ticket/read/{ticketId}?pageNumber={}&count={}`
- **Request Method:** GET
- **Produces:** application/json
- **Params:**
  - `pageNumber`
  - `count`
    
## 6. View Ticket 

This is used to view the Ticket by its Id.The ticket details will be displayed, showcasing the most recent action recorded for quick reference.

- **Endpoint:** `/ticketsystem/ticket/{ticketId}
- **Request Method:** GET
- **Produces:** application/json


