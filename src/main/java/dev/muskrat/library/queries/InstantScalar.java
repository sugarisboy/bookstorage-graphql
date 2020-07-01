package dev.muskrat.library.queries;

import graphql.schema.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InstantScalar extends GraphQLScalarType {

    public InstantScalar() {
        super("Instant", "Instant",
            new Coercing<Instant, String>() {
                @Override
                public String serialize(Object data) throws CoercingSerializeException {
                    if (data instanceof Instant)
                        return ((Instant) data).toString();
                    return null;
                }

                @Override
                public Instant parseValue(Object input) throws CoercingParseValueException {
                    return null;
                }

                @Override
                public Instant parseLiteral(Object input) throws CoercingParseLiteralException {
                    return null;
                }
            }
        );
    }
}
