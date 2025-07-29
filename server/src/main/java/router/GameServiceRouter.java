package router;

import Network.Message;
import Network.ServiceRouter;
import com.esotericsoftware.kryonet.Connection;
import services.GameService;
import session.SessionManager;

public class GameServiceRouter implements ServiceRouter {
    @Override
    public Object dispatch(Connection conn, Message msg) throws Exception {
        GameService gameService = SessionManager.getGameService(conn);
        gameService.sendAll(msg);
        return null;
    }
}
