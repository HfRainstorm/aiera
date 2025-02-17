package cn.hfstorm.aiera.ai.rector;

import dev.langchain4j.service.TokenStream;
import dev.langchain4j.spi.services.TokenStreamAdapter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : hmy
 * @date : 2025/2/17
 */
@Component
public class TokenStreamToFluxAdapter implements TokenStreamAdapter {

    @Override
    public boolean canAdaptTokenStreamTo(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            if (parameterizedType.getRawType() == Flux.class) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                return typeArguments.length == 1 && typeArguments[0] == String.class;
            }
        }
        return false;
    }

    @Override
    public Object adapt(TokenStream tokenStream) {
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();
        tokenStream.onNext(sink::tryEmitNext)
                .onComplete(aiMessageResponse -> sink.tryEmitComplete())
                .onError(sink::tryEmitError)
                .start();
        return sink.asFlux();
    }
}