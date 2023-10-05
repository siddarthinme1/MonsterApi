package services;


import com.fasterxml.jackson.databind.JsonNode;
import model.User;
import play.db.Database;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.List;

import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;


public class UserService {
    private final Database database;
    private static JPAApi jpaApi;

    @Inject
    public UserService(Database database, JPAApi jpaApi) {
        this.database = database;
        this.jpaApi = jpaApi;

    }
    public Result getAllUsers() {
        return jpaApi.withTransaction(entityManager -> {
            try {
                Query query = entityManager.createNativeQuery("select * from users", User.class);
                List<User> userList = query.getResultList();
                JsonNode json = Json.toJson(userList);
                return ok(json);
            } catch (Exception e) {
                return internalServerError("An Error occurred: " + e.getMessage());
            }
        });
    }

    public static Result addUser(User user) {
        return jpaApi.withTransaction(entityManager -> {
            try {
                entityManager.persist(user);
                return ok("User Added Successfully");
            } catch (Exception e) {
                return internalServerError("An Error occurred while adding user: " + e.getMessage());
            }
        });
    }

    public static Result deleteUser(Long userId) {
        return jpaApi.withTransaction(entityManager -> {
            try {
                User user = entityManager.find(User.class, userId);
                if (user != null) {
                    entityManager.remove(user);
                    return ok("User Deleted Successfully");
                } else {
                    return internalServerError("User not found with ID: " + userId);
                }
            } catch (Exception e) {
                return internalServerError("An Error occurred while deleting user: " + e.getMessage());
            }
        });
    }

    public static Result updateUser(Long userId, User updatedUser) {
        return jpaApi.withTransaction(entityManager -> {
            try {
                User existingUser = entityManager.find(User.class, userId);
                if (existingUser != null) {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setUserName(updatedUser.getUserName());
                    existingUser.setPassword(updatedUser.getPassword());
                    entityManager.merge(existingUser);
                    return ok("User Updated Successfully");
                } else {
                    return internalServerError("User not found with ID: " + userId);
                }
            } catch (Exception e) {
                return internalServerError("An Error occurred while updating user: " + e.getMessage());
            }
        });
    }

    public static Result getUser(Long userId) {
        return jpaApi.withTransaction(entityManager -> {
            try {
                User user = entityManager.find(User.class, userId);
                if (user != null) {
                    JsonNode json = Json.toJson(user);
                    return ok(json);
                } else {
                    return internalServerError("User not found with ID: " + userId);
                }
            } catch (Exception e) {
                return internalServerError("An Error occurred while deleting user: " + e.getMessage());
            }
        });
    }
}