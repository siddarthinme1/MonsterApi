package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import model.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UserController extends Controller {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Result home() {
        return ok("Welcome to PLATE PALETTE");
    }

    public CompletionStage<Result> getAllUsers() {
        CompletableFuture<Result> future = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try {
                Result result = userService.getAllUsers();
                future.complete(result);
            } catch (Exception e) {
                future.complete(internalServerError("An Error occurred: " + e.getMessage()));
            }
        });
        return future;
    }

    public CompletionStage<Result> addUser(Http.Request request) {
        JsonNode json = request.body().asJson();
        User user = Json.fromJson(json, User.class);
        return CompletableFuture.supplyAsync(() -> UserService.addUser(user));
    }

    public Result deleteUser(Long id) {
        return UserService.deleteUser(id);
    }

    public Result updateUser(Http.Request request, Long id) {
        User updatedUser = Json.fromJson(request.body().asJson(), User.class);
        return UserService.updateUser(id, updatedUser);
    }

    public Result getUserById(Long id) {
        return UserService.getUser(id);
    }
}