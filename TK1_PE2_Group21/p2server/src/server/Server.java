package server;

class Server {

    private ServerLogic serverLogic;

    Server() {
    	serverLogic = new ServerLogicImpl();
    }

    ServerLogic getServerLogic() {
        return serverLogic;
    }

}
