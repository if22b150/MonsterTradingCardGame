package at.technikum.server.controller;

import at.technikum.models.User;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;

public class BattleController {
    public Response store(User user) {
        return new Response(HttpStatus.OK, EContentType.JSON, HttpStatus.OK.message);
    }
}
