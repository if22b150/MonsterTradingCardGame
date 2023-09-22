package at.technikum.controllers;

import at.technikum.utils.Response;

public abstract class AController {
    public Response index() {
        return new Response(405, "");
    }

    public Response show(int id) {
        return new Response(405, "");
    }

    public Response store() {
        return new Response(405, "");
    }

    public Response update(int id) {
        return new Response(405, "");
    }

    public Response destroy(int id) {
        return new Response(405, "");
    }
}
