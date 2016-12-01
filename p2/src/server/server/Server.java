package server.server;

class Server {

    private ServerLogic serverLogic;

    Server() {
        serverLogic = new FakeServerLogic();
    }

    ServerLogic getServerLogic() {
        return serverLogic;
    }

}
