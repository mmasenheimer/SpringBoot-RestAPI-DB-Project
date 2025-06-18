# Dev Log

# 2025-06-10

- Set up Spring Boot project in IntelliJ
- Took notes on maven project structure, and how maven works
- Created a small program (different project) getting practice with dependency injection and beans

# 2025-06-11

- Took notes on Spring components and how they work
- Practice project with environment variables and common config properties

# 2025-06-12

- Made a subproject connecting to in-memory H2 database
- Understanding how to integrate docker compose files into the project

# 2025-06-14

- (Now on the current project)
- Initializing DB schemas
- Introduction to DAOs with jdbcTemplate and java object to db mapping

# 2025-06-16

- Implemented readOne sql query and proper tests for running the correct sql
- Implemented integration tests for querying multiple items from the db
- Implemented readMany to complete C.R.U.D functionality with the database

# 2025-06-18

- Deleted manual sql querying and switched over to using hibernate auto DDL
- SpringData JPA setup