package com.konai.search.vo;

import com.konai.common.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

public class Message {
	public String value;
	public String[] tokens;
	public int numOfToken;

	public Message(String value) {
	    if(StringUtils.isEmpty(value)) {
	        throw new NullPointerException();
        }
		this.value = value.replaceAll(" ", "");
		this.tokens = value.split(" ");
		this.numOfToken = tokens.length;
	}
	
	public String getOriginMessage() {
		String origin = "";
		for (int i = 0; i < tokens.length; i++) {
			origin = origin + (i > 0 ? " " : "") + tokens[i];
		}
		return origin;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return getOriginMessage().equals(message.getOriginMessage());
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(value, numOfToken);
		result = 31 * result + Arrays.hashCode(tokens);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Message{");
		sb.append("value='").append(value).append('\'');
		sb.append(", tokens=").append(Arrays.toString(tokens));
		sb.append(", numOfToken=").append(numOfToken);
		sb.append('}');
		return sb.toString();
	}
}
