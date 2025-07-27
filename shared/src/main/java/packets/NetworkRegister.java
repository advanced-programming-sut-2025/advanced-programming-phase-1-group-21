package packets;

import com.esotericsoftware.kryo.Kryo;
import models.result.Error;
import models.result.Result;
import models.result.errorTypes.*;
import models.user.Gender;
import models.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NetworkRegister {

    public static void register(Kryo kryo) {
        kryo.register(MessageType.class);
        kryo.register(Message.class);
        kryo.register(HashMap.class);
        kryo.register(ConcurrentMap.class);
        kryo.register(ConcurrentHashMap.class);

        kryo.register(Error.class);
        kryo.register(ServerError.class);
        kryo.register(UserError.class);
        kryo.register(AuthError.class);
        kryo.register(GameError.class);
        kryo.register(MenuError.class);

        kryo.register(Integer.class);
        kryo.register(Gender.class);
        kryo.register(User.class);

        kryo.register(List.class);
        kryo.register(ArrayList.class);

        kryo.register(String.class);
        kryo.register(Void.class);
        kryo.register(Result.class);
    }
}
