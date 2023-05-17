package org.cynic.jokes.domain.http;

import java.io.Serializable;

public record ErrorHttp(String code, Object... values) implements Serializable {

}
