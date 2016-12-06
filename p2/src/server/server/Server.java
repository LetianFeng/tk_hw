package server.server;

class Server {

    private ServerLogic serverLogic;

    Server() {
        //serverLogic = new FakeServerLogic();
    	serverLogic = new ServerLogicImpl();
    }

    ServerLogic getServerLogic() {
        return serverLogic;
    }

}
