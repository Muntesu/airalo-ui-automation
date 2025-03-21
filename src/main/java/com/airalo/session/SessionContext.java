package com.airalo.session;

import com.airalo.dto.AbstractContextDto;
import java.util.HashMap;
import java.util.Map;

public class SessionContext {

    private static final ThreadLocal<Map<Class<? extends AbstractContextDto>, AbstractContextDto>> sessions =
        ThreadLocal.withInitial(HashMap::new);

    public static void save(AbstractContextDto context) {
        sessions.get().put(context.getClass(), context);
    }

    public static <T extends AbstractContextDto> T get(Class<T> type) {
        return type.cast(sessions.get().get(type));
    }

    public static void closeSession() {
        sessions.remove();
    }
}

