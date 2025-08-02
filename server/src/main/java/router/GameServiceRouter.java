package router;

import models.network.Message;
import models.network.MessageType;
import network.ServiceRouter;
import com.esotericsoftware.kryonet.Connection;
import services.GameService;
import session.SessionManager;
import util.NetworkUtil;

public class GameServiceRouter implements ServiceRouter {
    @Override
    public Object dispatch(Connection conn, Message msg) throws Exception {
        GameService gameService = SessionManager.getGameService(conn);
        System.out.println("[GameServiceRouter] " + msg);
        gameService.sendAll(msg);
        return null;
    }
}
