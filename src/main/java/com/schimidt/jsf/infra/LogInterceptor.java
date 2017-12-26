package com.schimidt.jsf.infra;

import com.google.common.base.Stopwatch;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

import static javax.interceptor.Interceptor.Priority.APPLICATION;

@Interceptor
@Log
@Priority(APPLICATION)
class LogInterceptor implements Serializable {
    private static final long serialVersionUID = -5812562295395995811L;

    @AroundInvoke
    Object logResponseTimeForMethod(InvocationContext invocationContext) throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object result = invocationContext.proceed();
        stopwatch.stop();

        String methodWithClassName = invocationContext.getMethod().toGenericString();
        long millis = stopwatch.elapsed().toMillis();

        System.out.printf("========= > Tempo decorrido do método %s : %d milisegundos.\n", methodWithClassName, millis);

        return result;
    }
}
